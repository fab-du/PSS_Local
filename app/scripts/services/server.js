'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.server
 * @description
 * # server
 * Service in the cryptClientApp.
 */

angular.module('cryptClientApp')
.run(function($httpBackend) {
    
    $httpBackend.whenGET("/api/users").respond(  function(){
        var users= [
            {
                id : 2,
                email : "tux@tux.com",
                friends : [ 3, 4 ]
            },
            {
                id : 3,
                email : "linux@linux.com",
                friends : [ 2 ]
            },
            {
                id : 4,
                email : "fabrice@fabrice.com"
            },
            {
                id : 5,
                email : "levinas@levinas.com"
            }
        ];

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

    $httpBackend.whenGET( /(\?|\&)([^=]+)\=([^&]+)/ ).respond(  function(method, url, params  ){

        console.log( method );
        console.log( url );
        console.log( params );

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
    
});

