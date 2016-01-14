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
    transclude : true,
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
            data : scope.data,
            showGridFooter:true
        };

      scope.myStyle = '.grid { border: 1px solid blue }';


        scope.gridOptions.onRegisterApi = function( gridApi ) {
            scope.gridApi = gridApi;


            gridApi.selection.on.rowSelectionChanged( scope, function(row){
                console.log( row );
            });
        };


    }

};
})
.directive('accessLevel', function ( Auth ) {
return {
    restrict: 'A',
    link: function($scope, element, attrs) {

        var prevDisp = element.css('display');
        var userRole;
        var accessLevel;

        $scope.user = Auth.user;
        $scope.$watch('user', function(user) {
            if(user.role){
                userRole = user.role;
            }
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

