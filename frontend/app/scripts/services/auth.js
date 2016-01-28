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

        Storage.set("currentUserEmail", response.email);
        Storage.set("currentUserId", response.currentUserId);
        //Storage.putAll( authHeaders );
        $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
        q.resolve( status  );
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( err );
    });

    return q.promise;
}



api.isLoggedIn =  function() {
    var currentUserEmail = Storage.get("currentUserEmail");
    var currentUserId = Storage.get("currentUserId");
    var ret = ((currentUserEmail !== null) &&( currentUserId !== null )) ;
    return  ret;
}

api.getCurrentUser = function(){
    var currentUserEmail = Storage.get("currentUserEmail");
    var currentUserId = Storage.get("currentUserId");
    var ret = ((currentUserEmail !== null) &&( currentUserId !== null )) ;

    if( ret === true ){
        return { currentUserEmail : currentUserEmail, currentUserId: currentUserId };
    }
    else{
        return null;
    }
};


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

