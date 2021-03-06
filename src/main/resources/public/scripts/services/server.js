'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.server
 * @description
 * # server
 * Service in the cryptClientApp.
 */

/*
 *angular.module('cryptClientApp')
 *.run(function($httpBackend) {
 *        var groups= [
 *            {
 *                id : 2,
 *                gv : 5,
 *                name : "levinas_group",
 *                users : [ 5,1 ]
 *            },
 *            {
 *                id : 3,
 *                gv : 4,
 *                name : "fabrice_group",
 *                users : [ 4,1 ]
 *            },
 *            {
 *                id : 4,
 *                gv : 2,
 *                name : "tux_group",
 *                users : [ 2 ]
 *            },
 *            {
 *                id : 5,
 *                gv : 3,
 *                name : "linux_group",
 *                users : [ 3 ]
 *            }
 *        ];
 *        var friends= [
 *            {
 *                id : 2,
 *                email : "tux@tux.com",
 *                firstname : "tux",
 *                secondname : "tux",
 *                password : "pass",
 *                friends : [ 3, 4 ]
 *            },
 *            {
 *                id : 3,
 *                email : "linux@linux.com",
 *                firstname :  "linux",
 *                secondname : "linux",
 *                password : "pass",
 *                friends : [ 2 ]
 *            },
 *            {
 *                id : 4,
 *                password : "pass",
 *                firstname :  "fabrice",
 *                secondname : "fabrice",
 *                email : "fabrice@fabrice.com"
 *            }];
 *
 *        var my_groups= [
 *            {
 *                id : 2,
 *                gv : 5,
 *                name : "levinas_group",
 *                users : [ 5, 1 ]
 *            },
 *            {
 *                id : 3,
 *                gv : 4,
 *                name : "fabrice_group",
 *                users : [ 4 ]
 *            }
 *            
 *        ];
 *
 *        var users= [
 *            {
 *                id : 2,
 *                email : "tux@tux.com",
 *                firstname : "tux",
 *                secondname : "tux",
 *                password : "pass",
 *                friends : [ 3, 4 ]
 *            },
 *            {
 *                id : 3,
 *                email : "linux@linux.com",
 *                firstname :  "linux",
 *                secondname : "linux",
 *                password : "pass",
 *                friends : [ 2 ]
 *            },
 *            {
 *                id : 4,
 *                password : "pass",
 *                firstname :  "fabrice",
 *                secondname : "fabrice",
 *                email : "fabrice@fabrice.com"
 *            },
 *            {
 *                id : 5,
 *                password : "pass",
 *                firstname :  "levinas",
 *                secondname : "levinas",
 *                email : "levinas@levinas.com"
 *            }
 *        ];
 *
 *        function userExists( cred ){//{{{
 *            var ret = false;
 *
 *            angular.forEach( users, function( v  ){
 *                if( v.email === cred.email && v.password === cred.password  ){
 *                    ret = true;
 *                }
 *            });
 *
 *            return ret;
 *        }//}}}
 *    
 *    $httpBackend.whenGET("/api/users").respond(  function(){ return [200, users, {}]; });
 *
 *    $httpBackend.whenGET( "/api/groups" ).respond(  function(method, url, params  ){//{{{
 *
 *        console.log( params );
 *
 *        console.log( url );
 *
 *        return [200, groups, {}];
 *    });//}}}
 *
 *    $httpBackend.whenPOST('/session/login').respond( function( method, url, data ){//{{{
 *            var cred = { };
 *            cred = angular.fromJson( data );
 *            var ret = userExists( cred );
 *            if ( ret ){
 *                return [ 200, { email : "tux@tux.com", currentUserId : "2"  }, {} ];
 *            }
 *            else{
 *                return [401, {}, {}];
 *            }
 *    });//}}}
 *
 *    $httpBackend.whenPOST('/session/register').respond( function( ){});
 *
 *    $httpBackend.whenPOST('/session/logout').respond( function(){});
 *
 *
 *    $httpBackend.whenGET(/api\//).respond(function( method, url , data ){
 *        console.log(url);
 *        var _urls = url.split('/');
 *        var indexFriends = _urls.indexOf('friends');
 *
 *        if ( indexFriends !== -1 && indexFriends === 3 ){
 *            return [ 200, friends, {} ];
 *        }
 *
 *        var indexGroups = _urls.indexOf('groups');
 *        if ( indexGroups !== -1 && indexGroups === 2 ){
 *            return [ 200, my_groups, {} ];
 *        }
 *
 *    });
 *
 *    $httpBackend.whenPOST(/api\//).respond(function( method, url , data ){
 *        console.log(url);
 *        var _urls = url.split('/');
 *        var index = _urls.indexOf('documents');
 *
 *        if ( index !== -1 ){
 *            return [ 200, data , {} ];
 *        }
 *
 *        var indexGroups = _urls.indexOf('groups');
 *        if ( indexGroups !== -1 && indexGroups === 2 ){
 *            console.log(data)
 *            my_groups.push( data );
 *            return [ 200, { message : "group created" }, {} ];
 *        }
 *
 *    });
 *
 *    $httpBackend.whenGET(/views\//).passThrough();
 *    $httpBackend.whenGET(/images\//).passThrough();
 *    
 *});
 *
 */
