'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:GroupsCtrl
 * @description
 * # GroupsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('GroupsController', function ( Rest, Storage, $scope, Auth  ) {

    function init(){

            if( !Auth.isLoggedIn() ){
               return;
            } 

            Rest.Group.find().$promise.then( function( groups ){
                $scope.groups = groups;
            });

            Rest.Group.mygroups( { suffix : Auth.getCurrentUser().currentUserId } ).$promise.then( function( mygroups ){
                $scope.mygroups = mygroups;
            });
    }


    init();
})
.controller('GroupDetailsController', function( Rest, $filter, Storage, $scope, $stateParams,  Auth){
    var groups = $scope.groups; 

    $scope.group = $filter('getById')( groups, "id",  $stateParams.groupId );


})

