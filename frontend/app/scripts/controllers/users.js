'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('UsersController', function ( $scope, $rootScope, $state, Rest, Auth ) {

    function init(){
        $rootScope.$watch('isLoggedIn', function(isLoggedIn){
            if( isLoggedIn === true ){
                var currentUserId = Auth.getCurrentUser().currentUserId;

                Rest.Group.find().$promise.then( function( groups ){
                    if( groups ){
                        $scope.groups = groups.sortPromise();
                    }
                    $scope.groups = [];
                });

                $scope.onSelect = function(){
                    console.log('selected');
                };

                Rest.User.find().$promise.then( function( users ){
                    $scope.users = users;
                });


                Rest.Friend.find( { currentUserId : currentUserId  } ).$promise.then( function( friends ){
                    $scope.friends = friends;
                });
            }
        });
    }

    init();
})
.controller('UserGroupController', function( $scope, Storage, Rest ){
        Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
            $scope.my_groups = my_groups;
        });

});

