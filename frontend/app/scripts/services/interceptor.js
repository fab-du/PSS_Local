'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.interceptor
 * @description
 * # interceptor
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Interceptor', function($q, $location, Storage, $rootScope, AUTH_EVENTS ){
return {

/*
* Automatically attach Authorization header
*
*/

request : function( config ){
/*
 *console.log( config.url )
 */

if( config.headers["X-XSRF-TOKEN"] === undefined ){
    config.headers["X-XSRF-TOKEN"] = Storage.get('X-XSRF-TOKEN') || '';
}

if( config.headers.Authorization === undefined ){
    config.headers.Authorization = "SRP";
}

if( config.headers["WWW-Authenticate"] === undefined ){
    config.headers["WWW-Authenticate"] = "SRP";
}

if( config.headers["hash-algorithm"] === undefined ){
    config.headers["hash-algorithm"] = "SHA256";
}

if( config.headers.realm === undefined ){
    config.headers.realm = "realm";
}

if( config.headers.expires_in === undefined ){
    config.headers.expires_in  = 3600;
}

if( config.headers.token_type === undefined ){
    config.headers.token_type  = "bearer";
}

if( config.headers.access_token === undefined ){
    config.headers.access_token  = "xsrh";
}

if( config.headers.client_public_key === undefined ) {
    config.headers.client_public_key  = "pubkey";
}

if( config.headers.server_public_key === undefined ) {
    config.headers.server_public_key  = "ddodi";
}


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
/*
 *    console.log( rejection );
 *
 *    return $q.reject( rejection );
 */

    /*
     *if (canRecover(rejection)) {
     *    return responseOrNewPromise;
     *}
     *return $q.reject(rejection);
     */
}

};


})
.config( function( $httpProvider ){
    $httpProvider.interceptors.push('Interceptor');
});
