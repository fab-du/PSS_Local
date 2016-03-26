'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:FriendsCtrl
 * @description
 * # FriendsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('FriendController', function ( Rest, $q,  $scope, store ) {

    Rest.Friend.find( { currentUserId : store.get("currentUserId") } ).$promise.then( function( friends ){
        $scope.friends = friends;
    });

})
.controller('FriendDetailController', function ( Rest, $q, $scope, $stateParams ) {
    Rest.Friend.findOne( { friendId : $stateParams.friendId } ).$promise.then( function( friends ){
        $scope.friend = friend;
    });

})
