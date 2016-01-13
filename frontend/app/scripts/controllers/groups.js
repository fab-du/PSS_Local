'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:GroupsCtrl
 * @description
 * # GroupsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('GroupsController', function ( Rest, Storage, $scope  ) {

    function init(){
        if ( !Storage.get("groups") ){
            Rest.Group.find().$promise.then( function( groups ){
                Storage.set( "groups", groups );
                $scope.groups = groups;
            });
        }
        else{
           $scope.groups = Storage.get("groups");
        }


        if ( !Storage.get("my_groups") ){
            Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
                Storage.set( "my_groups", my_groups );
                $scope.my_groups = my_groups;
            });
        }
        else{
           $scope.my_groups = Storage.get("my_groups");
        }

    }


    init();
});

