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

        if( !Storage.get("users") ){
            Rest.User.find().$promise.then( function( users ){
                $scope.users = users; 
                Storage.set("users", users);
            });
            
        }
        else{
            $scope.users = Storage.get("users"); 
        }

    }

    init();
})
.controller('UserGroupController', function( $scope, Storage, Rest ){

    console.log( "come here" );
    if ( !Storage.get("my_groups") ){
        Rest.Group.find( { gv : Storage.get('currentUser').id } ).$promise.then( function( my_groups ){
            Storage.set( "my_groups", my_groups );
            $scope.my_groups = my_groups;
        });
    }
    else{
        $scope.my_groups = Storage.get("my_groups");
    }

});

