'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('UsersController', function ( $scope, $rootScope, $state, Rest, Storage ) {

    function init(){

        Rest.User.find().$promise.then( function( users ){
            $scope.users = users; 
        });


        /*
         *if( !Storage.get("users") ){
         *}
         *else{
         *    $scope.users = Storage.get("users"); 
         *}
         */

    }

    init();
})
.controller('UserGroupController', function( $scope, Storage, Rest ){
        Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
            $scope.my_groups = my_groups;
        });

});

