'use strict';

/**
 * @ngdoc directive
 * @name cryptClientApp.directive:directive
 * @description
 * # directive
 */
angular.module('cryptClientApp')
.directive('tab', function( $document ){
return{
    template : '<style ui-grid-style>{{ ui-style }}</style><div ui-grid=gridOptions class="grid" ui-grid-selection ></div>',
    restrict : 'E',
    transclude : true,
    scope : {
        data : '='
    },
    link : function( scope, el ){
        scope.gridOptions = {
            enableSelectAll: true,
            enableSelection: true,
            enableFiltering: true,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            data : scope.data,
            showGridFooter:true
        };

      scope.myStyle = '.grid { border: 1px solid blue }';


        scope.gridOptions.onRegisterApi = function( gridApi ) {
            scope.gridApi = gridApi;


            gridApi.selection.on.rowSelectionChanged( scope, function(row){
                //console.log( row );
            });
        };


    }

};
})
.directive('accessLevel', function ( Auth ) {
return {
    restrict: 'A',
    link: function($scope, element, attrs) {
        var prevDisp = element.css('display')
            , userRole
            , accessLevel;

        $scope.user = Auth.user;
        $scope.$watch('user', function(user) {
            if(user.role)
                userRole = user.role;
            updateCSS();
        }, true);

        attrs.$observe('accessLevel', function(al) {
            if(al) accessLevel = $scope.$eval(al);
            updateCSS();
        });

        function updateCSS() {
            if(userRole && accessLevel) {
                if(!Auth.authorize(accessLevel, userRole)){
                    element.css('display', 'none');
                }
                else{
                    element.css('display', prevDisp);
                }
            }
        }
    }
};
});
