 'use strict';

 angular.module('cryptoClientApp')
 .controller('ApiCtrl', function($scope, Auth, Rest, $http, $state){

     $scope.logout = function(){
         Auth.logout();
         $state.go('index');
     };

     $scope.getUser = function(){
         $http.get('/api/users')
         .success( function( resp , status , headers ){
             console.log( status );
             console.log( headers());
             console.log( resp );

         })
         .error( function( error ){
             console.log( error );
         })
     }

 });
