'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:UsersCtrl
 * @description
 * # UsersCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('UsersController', function ( $scope, $rootScope, store, $state, Rest, Auth ) {

    function init(){
        $rootScope.$watch('isLoggedIn', function(isLoggedIn){


            $scope.addFriend = function( index ){
                Rest.Friend.addFriend( {currentUserId : store.get("currentUserId")}, { id : $scope.items[index].id } ).$promise.then(function(){
                    $state.reload();
                });
            };

            if( isLoggedIn === true ){
                var currentUserId = store.get('currentUserId');

                Rest.Group.find().$promise.then( function( groups ){
                    if( groups ){
                        $scope.groups = groups.sortPromise();
                    }
                    $scope.groups = [];
                });

                Rest.User.find().$promise.then( function( users ){
                    $scope.users = users;
                    $rootScope.users = users;
                });
            }
        });
    }

    init();
})
.controller('UserDetailsController', function( $scope, Storage, $stateParams, Rest, $filter ){
    var  userId = $stateParams.userId;
    $scope.user = $scope.users[ userId ];
})

