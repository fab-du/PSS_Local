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
            Rest.Group.find().$promise.then( function( groups ){
                Storage.set( "groups", groups );
                $scope.groups = groups;
            });
        
            Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
                Storage.set( "my_groups", my_groups );
                $scope.my_groups = my_groups;
            });

    }


    init();
});

