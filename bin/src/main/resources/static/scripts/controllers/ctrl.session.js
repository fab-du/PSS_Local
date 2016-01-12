'use strict';
angular.module('cryptoClientApp')

.controller('CtrlSession', function ( $rootScope, $scope, $location, $window, Auth  ) {

    $scope.login = function( credentials ) {
        Auth.login( credentials,
            function(res) {
                $location.path('/');
            },
            function(err) {
                console.log( err )
                $rootScope.error = "Failed to login";
            });
    };

    $scope.register = function( credentials ){
        Auth.register(credentials, function(res){
            $location.path('/');
        },
        function(err){
          console.log(err);
        });
    }

    $scope.logout = function(){
        Auth.logout();
    };
    
});

