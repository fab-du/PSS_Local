'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.Auth
 * @description
 * # Auth
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $q, AUTH_EVENTS, Storage ) {
var api = {};

var currentSession = Storage.getAll() || {};


function storeSession( header ) {
    Storage.putAll( header );
}

function removeSession( ){
    Storage.remove();
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
function login( user ){
    var q = $q.defer();
    $http.post('/session/login/challenge', user).success(function( response, status , headers ){
        /*
         *Storage.set( "prikey", response.prikey );
         *var sessionHeader = headers();
         *storeSession( sessionHeader );
         *$state.go('api');
         */
        Storage.set( "currentUser", response );
        $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
        q.resolve( { response : response , status : status, headers : headers } );
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( err );
    });

    return q.promise;
}



function isLoggedIn() {
    return (Storage.getAll() === undefined);
}

api.logout =  function() {
    removeSession();
};



api.currentSession = currentSession;

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

});
