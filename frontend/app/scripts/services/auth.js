'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.Auth
 * @description
 * # Auth
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $filter, $q, AUTH_EVENTS, Storage,  $rootScope ) {
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
    $http.post('/session/login', user).success(function( response, status ){
        //var authHeaders = headers();

        Storage.set("currentUserEmail", response.email);
        Storage.set("currentUserId", response.currentUserId);

        //TODO this muss comme from server
        //85 hard coded just for dev purpose
        Storage.set("currentUserGroupId", "85" );

        //Storage.putAll( authHeaders );
        $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
        q.resolve( status  );
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( err );
    });

    return q.promise;
};



api.isLoggedIn =  function() {
    var currentUserEmail = Storage.get("currentUserEmail");
    var currentUserId = Storage.get("currentUserId");
    var ret = ((currentUserEmail !== null) &&( currentUserId !== null )) ;
    return  ret;
};

api.getCurrentUser = function(){
    var currentUserEmail = Storage.get("currentUserEmail");
    var currentUserId = Storage.get("currentUserId");
    var currentUserGroupId = Storage.get("currentUserGroupId");
    var ret = ((currentUserEmail !== null) &&
               ( currentUserId !== null )  &&
               ( currentUserGroupId !==null));

    if( ret === true ){
        return { currentUserEmail : currentUserEmail, 
                 currentUserId: currentUserId,
                 currentUserGroupId : currentUserGroupId};
    }
    else{
        return null;
    }
};

api.isGroupMember = function( groupUsers ){
    var isLoggedIn = api.isLoggedIn();

    if( !isLoggedIn || groupUsers === null ){
        return false;
    }

    var ret = $filter('getById')( groupUsers, 'id',  api.getCurrentUser().currentUserId );
    return ( ret !== null );
}

api.isGv = function( group ){
    var isLoggedIn = api.isLoggedIn();

    if( !isLoggedIn || group === null ){
        return false;
    }
    return group['gvid'] === api.getCurrentUser().currentUserId;
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
    });

    if( count === 6 ){
        return register( user, success, error );
    }
    else{
        $rootScope.$broadcast( AUTH_EVENTS.loginFailed );
    }
};

return api;
});

