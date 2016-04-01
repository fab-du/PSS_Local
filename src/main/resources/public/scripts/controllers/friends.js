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
    
    $scope.revoke = function( index ){
        Rest.Friend.revoke( {}, { friendId : $scope.items[index].id, currentUserId : store.get("currentUserId") } ).$promise.then( function( ){
            if (index !== -1) {
                $scope.items.splice(index, 1);
                $state.reload();
            }
        });
    };

})
.controller('FriendDetailController', function ( Rest, $q, $scope, $stateParams ) {
        $scope.user = $scope.friends[ $stateParams.friendId ] ;
})
