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
            if( isLoggedIn === true ){
                var currentUserId = store.get('currentUserId');

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
.controller('UserDetailsController', function( $scope, Storage, $stateParams, Rest ){
    var  userId = $stateParams.userId;
    Rest.Group.find( { gv : userId } ).$promise.then( function( usergroups ){
        $scope.usergroups = usergroups;
    });
    Rest.Friend.find( {currentUserId : userId} ).$promise.then( function( friends ){
        $scope.userfriends = friends;
    });
})

