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



        if( Auth.isLoggedIn() ){
            var currentUserId = Auth.getCurrentUser().currentUserId;

            Rest.Group.find( ).$promise.then( function( groups ){
                if( groups ){
                    $scope.groups = groups.sortPromise();
                    console.log($scope.groups)
                }
                $scope.groups = [];
            });

            $scope.onSelect = function(){
                console.log('selected');
            };

            Rest.User.find().$promise.then( function( users ){
                $scope.users = users.sortPromise(); 
            });


            Rest.Friend.find( { currentUserId : currentUserId  } ).$promise.then( function( friends ){
                $scope.friends = friends.sortPromise();
            });
        }

    }

    init();
})
.controller('UserGroupController', function( $scope, Storage, Rest ){
        Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
            $scope.my_groups = my_groups;
        });

});

