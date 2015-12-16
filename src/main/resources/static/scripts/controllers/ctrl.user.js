'use strict';

/**
 * @ngdoc function
 * @name cryptoClientApp.controller:CtrlUserCtrl
 * @description
 * # CtrlUserCtrl
 * Controller of the cryptoClientApp
 */
angular.module('cryptoClientApp')
  .controller('CtrlUser', function ( $scope, $rootScope ,$resource, Rest ) {

      var grid_opts = {

      };

      var User = Rest.User( );

      User.get().$promise.
      then( function(response){
        $scope.users = response;
      });



  });
