'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.interceptor
 * @description
 * # interceptor
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Interceptor', function($q, $location, store, $rootScope, AUTH_EVENTS, ERRORS_EVENTS, SUCCESS_EVENTS ){
return {

request : function( config ){
    if ( store.get("token")){
        config.headers["Authorization"] = store.get("token");
    }

    if ( store.get("clientpubkey") ){
        config.headers["clientpubkey"] = store.get("clientpubkey");
    }

    return config;
},

response : function( response ){

    $rootScope.$broadcast({
        201: SUCCESS_EVENTS.created,
        204: SUCCESS_EVENTS.nothingToDelete,
        202: SUCCESS_EVENTS.deleted

    }[response.status], response);
    
    return response;
},

/*
* Handle authentication errors in response object
*/
responseError: function( response ) {
      $rootScope.$broadcast({
        401: AUTH_EVENTS.notAuthenticated,
        403: AUTH_EVENTS.notAuthorized,
        419: AUTH_EVENTS.sessionTimeout,
        440: AUTH_EVENTS.sessionTimeout,
        404: AUTH_EVENTS.notFound

      }[response.status], response);

      return $q.reject(response);
},

/*
* Handle authentication errors in request object
*/
requestError: function(rejection) {
    return $q.reject( rejection );
}

};


})
.config( function( $httpProvider ){
    $httpProvider.interceptors.push('Interceptor');
});
