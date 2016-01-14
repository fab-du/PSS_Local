'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:SessionCtrl
 * @description
 * # SessionCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('LoginController', function ( $scope, $rootScope, AUTH_EVENTS, Auth, Storage  ) {

    function init(){
        $scope.credentials = {
            email : '',
            password : ''
        };

        $scope.login = function( credentials ){
            Auth.login( credentials ); 
        };

    }


    init();
})

.controller('RegisterController', function ( $scope, $rootScope, AUTH_EVENTS, Auth , Storage ) {

    function init(){
        $scope.credentials = {
            email      : '',
            firstname  : '',
            secondname : '',
            password   : '',
            _password  : '',
            company    : '',
        };

        $scope.register = function( credentials ){

        };
    }

    init();
})

.controller('LogoutController', function ( $scope, $rootScope, AUTH_EVENTS, Auth , Storage ) {

    function init(){
        $scope.logout = function(){
            Storage.remove();
        };
    }

    init();

});

