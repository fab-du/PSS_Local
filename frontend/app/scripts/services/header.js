'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.header
 * @description
 * # header
 * Constant in the cryptClientApp.
 */
angular.module('cryptClientApp')
  .constant('HEADERS', {
      contentSecurityPolicy : { name : 'content-security-header', value : '' },
      authorization : { name : 'authorization', value : '' },
      wwwAuthentication :  { name : 'www-authentication', value : '' }, 
      realm : { name : 'realm', value : ''},
      hashAlgorithm : { name : 'hash-algorithm', value : '' },
      xXsrfToken : { name : 'x-xsrf-token', value : ''}, 
      expiresIn : { name : 'expires-in', value : '' },
      clientPublicKey : { name : 'client-public-key', value : '' },
      serverPublicKey : { name : 'server-public-key', value : '' },
      typ : { name : 'typ', value : '' },
      alg : { name : 'alg', value : '' }
  
  });
