'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.server
 * @description
 * # server
 * Service in the cryptClientApp.
 */

angular.module('cryptClientApp')
.run(function($httpBackend, HEADERS ) {

        var users= [
            {
                id : 2,
                email : "tux@tux.com",
                password : "pass",
                friends : [ 3, 4 ]
            },
            {
                id : 3,
                email : "linux@linux.com",
                email : "tux@tux.com",
                friends : [ 2 ]
            },
            {
                id : 4,
                email : "tux@tux.com",
                email : "fabrice@fabrice.com"
            },
            {
                id : 5,
                email : "tux@tux.com",
                email : "levinas@levinas.com"
            }
        ];

        function userExists( cred ){
            var ret = false;

            angular.forEach( users, function( v , k ){
                if( v.email == cred.email && v.password == cred.password  ){
                    ret = true;
                }
            });

            return ret;
        }
    
    $httpBackend.whenGET("/api/users").respond(  function(){

        return [200, users, {}];
    });

    $httpBackend.whenGET( "/api/groups" ).respond(  function(method, url, params  ){

        console.log( params );

        console.log( url );

        var groups= [
            {
                id : 2,
                gv : 5,
                name : "levinas_group",
                users : [ 5,1 ]
            },
            {
                id : 3,
                gv : 4,
                name : "fabrice_group",
                users : [ 4,1 ]
            },
            {
                id : 4,
                gv : 2,
                name : "tux_group",
                users : [ 2 ]
            },
            {
                id : 5,
                gv : 3,
                name : "linux_group",
                users : [ 3 ]
            }
        ];

        return [200, groups, {}];
    });

    $httpBackend.whenPOST('/session/login').respond( function( method, url, data ){
            var cred = { };
            cred = angular.fromJson( data )
            var ret = userExists( cred );
            if ( ret ){
                return [ 200, {},  { 'realm' : 'realm', 'Authorization' : 'Bearer Tokennnnnnn', 'www-authentication' : 'SRP', 'hash-algorithm' : 'SHA256', 'Client-Public-Key' : 'public-key', 'Server-Public-Key' : 'server-key', 
               "AUTH-TOKEN" : 'authenticationtokenklajsdfkja',  'alg': 'RS512', 'typ' : 'JWT', 'Content-Security-Header': 'script-src self', 'X-XSRF-TOKEN' : 'XSRF-TOKEN', 'expires-in' : '3600' }  ]
            }
            else{
                return [401, {}, {}];
            }
    });

    $httpBackend.whenPOST('/session/register').respond( function( method, url, data ){
        console.log(data)
        if( angular.isUndefined( data ) || data === null ){
            return [400, { message : 'registration failed' }, {}];
        }
        return [200, {}, {}];
    });

    $httpBackend.whenPOST('/api/documents/addDocuments').respond( function( method, url, params ){

    });


    $httpBackend.whenPOST('/session/logout').respond( function( method, url, params ){

    });

    $httpBackend.whenGET( /(\?|\&)([^=]+)\=([^&]+)/ ).respond(  function(method, url, params  ){

        var my_groups= [
            {
                id : 2,
                gv : 5,
                name : "levinas_group",
                users : [ 5, 1 ]
            },
            {
                id : 3,
                gv : 4,
                name : "fabrice_group",
                users : [ 4 ]
            }
            
        ];

        return [200, my_groups, {}];
    });



    $httpBackend.whenGET(/views\//).passThrough();
    $httpBackend.whenGET(/images\//).passThrough();
    
});

