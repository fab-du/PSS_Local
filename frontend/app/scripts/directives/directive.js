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
        template : '<div> <form enctype="multipart/form-data"> <label class="fileupload" > <input id="uploader" type="file" onchange="angular.element(this).scope().filesChanged(this)"  multiple/><span> Upload </span></label> </form> </div>'+
                    '<li ng-repeat="file in files">{{ file.name }}</li>',
        scope : {
            cryptfiles : '=',
            uploader : '=',
        },
        controller : function( $scope, $compile ){

            $scope.uploadFiles = function(files, errFiles) {
                $scope.files = files;
                $scope.errFiles = errFiles;
                angular.forEach(files, function(file) {
                    file.upload = Upload.upload({
                        url: '/api/addDocuments',
                        data: {file: file}
                    });

                    file.upload.then(function (response) {
                        $timeout(function () {
                            file.result = response.data;
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    }, function (evt) {
                        file.progress = Math.min(100, parseInt(100.0 * 
                                                evt.loaded / evt.total));
                    });
                });
            };


        },

        link : function(  scope, element, attributs){
            angular.element( element ).find( "#uploader" ).css( { position : "fixed", top : "-1000px" } );
            angular.element( element ).find( ".fileupload" ).css( { display : "inline-block", background : "grey",  radius : "5px", padding: "2px 4px", margin : "4px" } );

        },
    };

});

