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
    restrict : 'E',
    templateUrl : '/views/documents/widget.document.list.html',
    transclude : false,
    scope : {
        data : '=',
        crudOnSelect : '=', // display delete/add button on select.
        onSelect : '='
    },
    link : function( scope, el, attrs ){


        scope.gridOptions = {
            enableSelectAll: true,
            enableSelection: true,
            enableFiltering: true,
            showHeader : false,
            showGridFooter:true
        };

        var data = [];
        scope.details = [];
        scope.selected = false;

        var selectHandler = function( row ){
                                if( row.isSelected ){
                                    scope.details.push( angular.toJson (row.entity) )
                                    scope.selected = true;
                                }
                                else{
                                    scope.details.pop( row.entity );
                                }

                                if ( scope.details.length === 0 ){
                                    scope.selected = false;
                                }
                            };

        scope.$watch('data', function( ){
            console.log( attrs.data );
            scope.gridOptions.data = scope.data;
        });

        scope.$watch('onSelect', function(){
            if( angular.isFunction( scope.onSelect ) ){
                selectHandler = scope.onSelect;
            }
        });

        scope.$watch('crudOnSelect', function(){
            if( scope.crudOnSelect === "true" ){
                console.log( "lkajsdf" )
            }
        });


      scope.myStyle = '.grid { border: 1px solid blue }';


        scope.gridOptions.onRegisterApi = function( gridApi ) {
            scope.gridApi = gridApi;
            gridApi.selection.on.rowSelectionChanged( scope, selectHandler );
        };


    }

};
})
.directive('accessLevel', function ( Auth ) {
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

})

.directive('matchFilter', function( $filter ){
    return{
        
        restrict : 'E',
        scope : {
            data : '='
        },
        template :  ''
                    + '<div id="selectBox" class="md-alert" ng-hide="closed" on-close="closed=true" ><div><input type="text" style="width:200px;" name="" id="" value="{{substring}}" ng-model="substring" /></div>'
                    + '<div id="elements" ><div class="selectClass" ng-click="matchFilterSelect(it)" ng-repeat="it in data track by $email | filter:substring"> {{ it }} <hr style="background=lightblue"/>  </div></div></div>',

        controller : function( $filter, $scope, $rootScope ){
            $scope.closed = false;
            $scope.substring= "";

            $scope.matchFilterSelect  = function( el ){
                $rootScope.selectedElement = el;
                $scope.closed = true;
            };
        },
        link : function(scope, el , attrs ){
            var _selectBox = angular.element('#elements .selectClass');
                _selectBox.on('click', function(ev){
                    $scope.$emit( 'element:selected');

                })

                scope.$watch('substring', function( _new, _old ){
            })

        }
    }
});

