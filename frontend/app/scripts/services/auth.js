'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.Auth
 * @description
 * # Auth
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $q, AUTH_EVENTS, Storage,  $rootScope ) {
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
api.login = function( user ){
    var q = $q.defer();
    $http.post('/session/login', user).success(function( response, status , headers ){
        var authHeaders = headers();

        $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
        Storage.putAll( authHeaders );
        Storage.set("currentUser", user.email );
        q.resolve( status  );
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( err );
    });

    return q.promise;
}



api.isLoggedIn =  function() {
    var currentUser = Storage.get("currentUser");
    var ret = currentUser !== null;
    console.log( "from isloggedin" );
    console.log( ret )
    return  currentUser !== null ;
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
