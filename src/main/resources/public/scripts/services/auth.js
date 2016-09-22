'use strict';

angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $location, $window, $filter, $q, AUTH_EVENTS,  store,  $rootScope ) {
var api = {};

function register( user, success, error ){
    $http.post('/session/register', user).success(function(res) {
	console.log( res );
        $state.go('login');
    }).error(error);
}

/****
 * Follow SRP Working-flow. ie :
 * Challenge -> Authenticate -> Authorize
 **/
api.login = function( user ){
    var q = $q.defer();
    $http.post('/session/login', user).success(function( response, status, headers ){
        
        store.set("currentUserEmail", response.email);
        store.set("currentUserId", response.currentUserId);
        store.set("token", headers("authorization"));
        store.set("currentUserId", response.currentUserId );
        store.set("evidence", response.evidence );
        store.set("clientpubkey", response.currentUserPublicKey );
        store.set("currentUserGroupId", response.currentUserGroupId );

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
    var currentUserEmail = store.get("currentUserEmail");
    var currentUserId = store.get("currentUserId");
    var ret = ((currentUserEmail !== null) &&( currentUserId !== null )) ;
    return  ret;
};

api.getCurrentUser = function(){
    var currentUserEmail   = store.get("currentUserEmail");
    var currentUserId      = store.get("currentUserId");
    /*
     *var currentUserGroupId = store.get("currentUserGroupId");
     */
    var ret = ((currentUserEmail    !== null) && ( currentUserId      !== null));

    if( ret === true ){
        return { currentUserEmail : currentUserEmail, 
                 currentUserId: currentUserId };
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
};

api.isGv = function( group ){
    var isLoggedIn = api.isLoggedIn();

    if( !isLoggedIn || group === null ){
        return false;
    }
    return group.gvid === api.getCurrentUser().currentUserId;
};

api.logout =  function(){
    store.remove("currentUserId");
    store.remove("currentUserEmail");
    store.remove("token");
    store.remove("evidence");
    store.remove("clientpubkey");
    store.remove("currentUserGroupId");

    $http.post('/session/logout', {} ).success( function( res ){
	console.log( res );
        $http.config.headers.Authorization = null;
        $http.config.headers.authorization = null;
        $location.path("/");
        $window.location.reload();
    })
    .error( function( err ){
        $http.config.headers.Authorization = null;
        $http.config.headers.authorization = null;
        $window.location.reload();
        console.log( err );
    });
};


api.register = function( user, success, error ){
    return register( user, success, error );
};

return api;
});

