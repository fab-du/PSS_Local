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

    function setScope(){
        $scope.groups = Storage.get("groups") || 
                           Rest.Group.find().$promise.then( function( groups ){
                             Storage.set( "groups", groups );
                             $scope.groups = groups;
                           })
    }

    setScope();

});
