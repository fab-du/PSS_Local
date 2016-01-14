'use strict';

/**
 * @ngdoc directive
 * @name cryptClientApp.directive:directive
 * @description
 * # directive
 */
angular.module('cryptClientApp')
.directive('tab', function( ){
return{
    template : '<style ui-grid-style>{{ ui-style }}</style><div ui-grid=gridOptions class="grid" ui-grid-selection ></div>',
    restrict : 'E',
    transclude : false,
    scope : {
        data : '='
    },
    link : function( scope ){
        scope.gridOptions = {
            enableSelectAll: true,
            enableSelection: true,
            enableFiltering: true,
            selectionRowHeaderWidth: 35,
            rowHeight: 35,
            showGridFooter:true
        };


        var data = [];
        var _obj = [];

        scope.$watch('data', function(){
            scope.gridOptions.data = scope.data;
            console.log( scope.data );
            console.log( data );
        });

      scope.myStyle = '.grid { border: 1px solid blue }';


        scope.gridOptions.onRegisterApi = function( gridApi ) {
            scope.gridApi = gridApi;


            gridApi.selection.on.rowSelectionChanged( scope, function(row){
            
            });
        };


    }

};
})
.directive('accessLevel', function ( Auth ) {
    //TODO
return {
    restrict: 'A',
    link: function($scope, element, attrs) {

        function updateCSS() {
            if(isLoggedIn) {
                    element.css('display', 'none');
                }
                else{
                    element.css('display', prevDisp);
                }
            }
        }
    
};
})
.directive('uploader', function( ){
    return{
        restrict : 'E',
        transclude : true,
        templateUrl : '/views/documents/widget.uploader.html',
        scope : {
            cryptfiles : '=',
            uploader : '=',
        },
        controller : function( $scope, $compile, Upload ){
            $scope.files = [];
            $scope.filesString = [];

            $scope.$watch( "files", function( _new, _old ){
                if ( _new !== _old  && _new !== null ){
                    console.log( _new[0] )
                    $scope.filesString.push( _new[0] );
                }
            });

            $scope.removeFile = function( index ){
               var ret = [];
               var values = $scope.filesString; 
               angular.forEach( values , function( v, k ){
                    if ( k !== index ){
                        ret.push( v );
                    }
               })
               
               $scope.filesString = ret; 
            };

            $scope.upload = function(){
/*
 *                angular.forEach(files, function(file) {
 *                    file.upload = Upload.upload({
 *                        url: '/api/addDocuments',
 *                        data: {file: file}
 *                    });
 *
 *                    file.upload.then(function (response) {
 *                        $timeout(function () {
 *                            file.result = response.data;
 *                        });
 *                    }, function (response) {
 *                        if (response.status > 0)
 *                            $scope.errorMsg = response.status + ': ' + response.data;
 *                    }, function (evt) {
 *                        file.progress = Math.min(100, parseInt(100.0 * 
 *                                                evt.loaded / evt.total));
 *                    });
 *                });
 */

            };

        },

        link : function(  scope, element, attributs){

        },
    };

});

