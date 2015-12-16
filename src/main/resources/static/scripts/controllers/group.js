'use strict';

/**
 * @ngdoc function
 * @name cryptoClientApp.controller:CtrlUserCtrl
 * @description
 * # CtrlUserCtrl
 * Controller of the cryptoClientApp
 */
angular.module('cryptoClientApp')
  .controller('CtrlGroup', function ( $scope, $rootScope, $resource, Rest, $location, $state ) {

      var currentUserId = 8;
      var currentUserEmail = "dufilsfabrice@gmail.com";

      

      var Group = Rest.Group();


      $scope.member = null;
      $scope.newgroup = null;
      $scope.selected = {};


      Group.get().$promise.then( function(response){ 
          $scope.data = response;
      });


      $scope.addUserToGroup = function( member ){
          console.log(member);
      }

      $scope.create = function( newgroup ){
          newgroup.currentUserId = currentUserId;
          newgroup.currentUserEmail = currentUserEmail;
          Group.save( newgroup).$promise
          .then( function( response ){
              console.log(response);
              $location.path("/api/groups");
          });
      };

      $scope.operation = 
      {
          remove :  function remove( ){
              console.log('remove')
          },

          getOne : function( id, callback ){
            Group.getOne({ groupId : id }).$promise
            .then( function(response){
                callback(response, $state );
            });
          }

      }


  })
  .directive('tab', function(){

    var controller = ['$scope', '$state',  function($scope, $state ){

        $scope.gridOptions = {
        enableGridMenu : true,
        enableSorting : true,
        enableFiltering : true,
        showGridFooter: true,
        enableFullRowSelection : true,
        };


        $scope.gridOptions.onRegisterApi = function( gridApi ) {
            $scope.gridApi = gridApi;

            gridApi.selection.on.rowSelectionChanged($scope, function(row){
                if( row.isSelected === true ){
                    console.log( row.entity.id )
                    $scope.operation.getOne( row.entity.id, function( response , $state ){
                        $scope.selected = response; 
                        $state.go('groups.id');
                    });
                }
                else{

                    $scope.rowSelected = false;
                    $state.go('^');
                }

            });

        };

    }];

    return {
        restrict : 'AE',
        scope : {
            data : '=',
            operation : '=',
            selected : '='
        },
        template : 
            '<div ui-grid="gridOptions" ui-grid-selection class="grid" >' + 
            '</div>',
        link : function( scope, elem, attrs ){
            scope.$watch('data', function( data ){
                scope.gridOptions.data = data;
            });

            scope.$watch( 'selected', function(selected){
                console.log(selected)
                scope.selected = selected;
            });

        },
        controller : controller
                
    };
  });
