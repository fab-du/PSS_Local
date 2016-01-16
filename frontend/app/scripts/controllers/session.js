'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:SessionCtrl
 * @description
 * # SessionCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('SessionController', function ( $scope, $rootScope, $state, AUTH_EVENTS, Auth, Storage  ) {

    function init(){
        $scope.credentials = {
            email : '',
            password : ''
        };


        $scope.register = function( credentials ){
        };

        $scope.logout = function(){
            console.log( "come hree" )
               Storage.remove();
               $rootScope.isLoggedIn = false;
               $state.go('login');
        };

    }


    init();
})
.controller('RegisterController', function( $scope, $rootScope, Storage, Auth, AUTH_EVENTS ){

        $scope.register = function( credentials ){
            console.log( credentials )
            Auth.register( credentials ); 
        };

})
.controller('LoginController', function( $scope, $rootScope, Storage, Auth, AUTH_EVENTS ){

        $scope.login = function( credentials ){
            Auth.login( credentials );
        };

})

