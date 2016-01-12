'use strict';

/**
 * @ngdoc service
 * @name cryptoClientApp.Auth
 * @description
 * # Auth
 * Factory in the cryptoClientApp.
 */
angular.module('cryptoClientApp')
  .factory('Auth', function ($http, $state, sessionStorage ) {
    var api = {};
    var currentSession = sessionStorage.getAll() || {};


    function storeSession( header ) {
        sessionStorage.putAll( header );
    }

    function removeSession( ){
        sessionStorage.remove();
    }



    function register( user, success, error ){
        $http.post('/session/register', user).success(function(res) {
            $state.go('login');
        }).error(error);
    }


    /*
     * Follow SRP Working-flow. ie :
     * Challenge -> Authenticate -> Authorize
     **/
    function login( user, success, error ){
        $http.post('/session/login/challenge', user).success(function( response, status , headers ){
            sessionStorage.set( "prikey", response.prikey );
            var sessionHeader = headers();
            storeSession( sessionHeader );
            $state.go('api');
        }).error(error);
    }



      function isLoggedIn() {
          return (sessionStorage.getAll() === null);
      }

      api.logout =  function() {
        console.log( sessionStorage.getAll() );
        removeSession();
        console.log( sessionStorage.getAll() );
      };



    api.currentSession = currentSession;

      api.register = function( user, success, error ){
        var count = 0;

        angular.forEach( user , function(value, key){
            if( key === 'email' )  count++;
            if( key === 'password') count++;
            if( key === 'passphrase') count++;
            if( key === 'firstname') count++;
            if( key === 'secondname') count++;
            if( key === 'company') count++;
        })    

        if( count === 6 ){
            return register( user, success, error );
        }
        else{
            return false;
        }
      };

      api.login = function( user, success,error ){
        login( user, success , error);
      };

      api.isLoggedIn = function(user){
          return isLoggedIn(user);
      };

      return api;

  })
  
