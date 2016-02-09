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

if( config.headers.client_public_key === undefined ) {
    config.headers.client_public_key  = Storage.get('client_public_key')|| "";
}

if( config.headers.server_public_key === undefined ) {
    config.headers.server_public_key  = Storage.get('server_public_key') || "" ;
}

    return config;
},

response : function( res ){

    if( (  res.headers( 'X-XSRF-TOKEN'))  ) {
        Storage.set( res.headers( 'X-XSRF-TOKEN'));
    }

    if(  res.headers( 'client_public_key') ) {
        Storage.set( res.headers( 'client_public_key'));
    }

    if( res.headers( 'client_private_key') ) {
        Storage.set( res.headers( 'client_private_key'));
    }

    if(  res.headers( 'server_public_key')  ) {
        Storage.set( res.headers( 'server_public_key'));
    }

    if(  res.headers( 'token_type') ) {
        Storage.set( res.headers( 'token_type') );
    }

    if( res.headers( 'access_token') ) {
        Storage.set( res.headers( 'access_token'));
    }

    if( res.headers( 'WWW-Authenticate')) {
        Storage.set( res.headers( 'WWW-Authenticate'));
    }

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
