'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.interceptor
 * @description
 * # interceptor
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.provider('Interceptor', function(Storage ){
return {

/*
* Automatically attach Authorization header
*
*/

request : function( config ){
    if( config.headers["X-XSRF-TOKEN"] === undefined ) config.header["X-XSRF-TOKEN"] = $cookieStore.get('X-XSRF-TOKEN');
    if( config.headers["Authorization"] === undefined ) config.header["Authorization"] = "SRP";
    if( config.headers["WWW-Authenticate"] === undefined ) config.header["WWW-Authenticate"] = "SRP";
    if( config.headers["hash-algorithm"] === undefined ) config.header["hash-algorithm"] = "SHA256";
    if( config.headers["realm"] === undefined )  config.header["realm"] = "realm";
    if( config.headers["expires_in"] === undefined ) config.header["expires_in"]  = 3600;
    if( config.headers["token_type"] === undefined ) config.header["token_type"]  = "bearer";
    if( config.headers["access_token"] === undefined )  config.header["access_token"]  = "xsrh";
    if( config.headers["client-public-key"] === undefined )  config.header["client-public-key"]  = "pubkey";
    if( config.headers["server-public-key"] === undefined )  config.header["server-public-key"]  = "ddodi";

    return config;
},

/*
* Save authorization header, if those was send back,
*/
response : function( res ){
    console.log( res.header );
    return res;
},

/*
* Handle authentication errors in response object
*/
responseError: function(response) {
    console.log("error not auth");
    if(response.status === 401 || response.status === 403) {
        window.document.url ='#/login';
    }
    return $q.reject(response);
},

/*
* Handle authentication errors in request object
*/
requestError: function(rejection) {
    console.log(rejection);
    if (canRecover(rejection)) {
        return responseOrNewPromise;
    }
    return $q.reject(rejection);
}

};


});
