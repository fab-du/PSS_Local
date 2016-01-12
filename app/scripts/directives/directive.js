'use strict';

/**
 * @ngdoc directive
 * @name cryptClientApp.directive:directive
 * @description
 * # directive
 */
angular.module('cryptClientApp')
.directive('directive', function () {
    return {
        template: '<div></div>',
        restrict: 'E',
        link: function postLink(scope, element, attrs) {
        element.text('this is the directive directive');
        }
};
})

.directive('tab', function(){
return{
    template : '<div class="grid" ng-transclude></div>',
    restrict : 'E',
    controller : function( $scope ){
        console.log( $scope.groups )
        $scope.gridOptions = {
            enableSelectAll: true,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            showGridFooter:true
        };

        $scope.gridOptions.multiSelect = false;
        $scope.gridOptions.modifierKeysToMultiSelect = false;
        $scope.gridOptions.noUnselect = true;

        $scope.gridOptions.onRegisterApi = function( gridApi ) {
            $scope.gridApi = gridApi;
        };


    }

};
})
