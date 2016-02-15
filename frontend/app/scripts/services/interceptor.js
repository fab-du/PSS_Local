'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.interceptor
 * @description
 * # interceptor
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Interceptor', function($q, $location, Storage, store, $rootScope, AUTH_EVENTS ){
return {

/*
* Automatically attach Authorization header
*
*/

request : function( config ){
    return config;
},

response : function( res ){
    return res;
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

        404: AUTH_EVENTS.notFound,

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
