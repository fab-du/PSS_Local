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
        $scope.logout = function(){
               Storage.remove();
               $rootScope.isLoggedIn = false;
               $rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);
               $state.go('login');
        };

    }


    init();
})
.controller('RegisterController', function( $scope, Auth){
        $scope.register = function( credentials ){
            Auth.register( credentials );
        };

})
.controller('LoginController', function( $scope, Auth ){
        $scope.login = function( credentials ){
            Auth.login( credentials );
        };
});

