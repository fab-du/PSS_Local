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
    
    $httpBackend.whenGET("/users").respond(  function(){
        var users= [
            {
                id : 2,
                email : "tux@tux.com"
            },
            {
                id : 3,
                email : "linux@linux.com"
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

    $httpBackend.whenGET("/api/groups/").respond(  function(){
        var groups= [
            {
                id : 2,
                gv : 5,
                name : "levinas_group",
                users : [ 5 ]
            },
            {
                id : 3,
                gv : 4,
                name : "fabrice_group",
                users : [ 4 ]
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

    $httpBackend.whenGET(/views\//).passThrough();
    
});

