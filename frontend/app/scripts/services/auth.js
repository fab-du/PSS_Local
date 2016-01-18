'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.Auth
 * @description
 * # Auth
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $q, AUTH_EVENTS, Storage, HEADERS,  $rootScope ) {
var api = {};


function register( user, success, error ){
    $http.post('/session/register', user).success(function(res) {
        $state.go('login');
    }).error(error);
}


/*
    * Follow SRP Working-flow. ie :
    * Challenge -> Authenticate -> Authorize
    **/

   function findHeader( header, headers ){
       var ret = false;
       var q = $q.defer();
       var value;
       

      angular.forEach( headers , function( _v, _k ){
          console.log( _k )
          if ( !angular.isUndefined( _k )  &&  _k !== null && header === _k  ){
                  value = _v; 
                  ret = true;
          }
         else{}

      });

      if ( ret ){
          q.resolve( { name :  header, value :  value } )
      }
      else{
          q.reject( { err : 'header error \t' + header } );
      }
      return q.promise;
   }

api.login = function( user ){
    var q = $q.defer();
    $http.post('/session/login', user).success(function( response, status , headers ){
        var error = false;
        var errorMessage;
        var authHeaders = headers();

        Storage.set("currentUser", user.email );

        angular.forEach( HEADERS , function( _v, _k ){
               findHeader( _v.name, authHeaders).then( 
                function( header ){
                    Storage.set( header.name , header.value  );
               },function(err){
                    error = true;
                    errorMessage = err;
                    console.log( err )
               });
        });

        if ( !error ){
            $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
            q.resolve( status  );
        }
        else{
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
            q.reject( err );
        }
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( errorMessage );
    });

    return q.promise;
}



api.isLoggedIn =  function() {
    var currentUser = Storage.get("currentUser");
    var ret = currentUser !== null;
    return  ret;
}


api.logout =  function(){
    Storage.remove();
};


api.register = function( user, success, error ){
    var count = 0;

    angular.forEach( user , function(value, key){

        if( key === 'email' ){
            count++;
        }  

        if( key === 'password') {
          count++;
        }

        if( key === 'passphrase'){
           count++;
        } 

        if( key === 'firstname'){
           count++;
        } 
        if( key === 'secondname') {
          count++;
        }
        if( key === 'company') {
          count++;
        }
    })    

    if( count === 6 ){
        return register( user, success, error );
    }
    else{
        $rootScope.$broadcast( AUTH_EVENTS.loginFailed );
    }
};


return api;

});
