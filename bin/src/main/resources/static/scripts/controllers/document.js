'use strict';

/**
 * @ngdoc function
 * @name cryptoClientApp.controller:CtrlDocument
 * @description
 * # CtrlDocument
 * Controller of the cryptoClientApp
 */
angular.module('cryptoClientApp')
.controller('CtrlDocument', function ( $http, $scope, $rootScope ,$resource, Rest, uiUploader ) {


$scope.uploader = uiUploader; 
$scope.files = [];


$scope.startUpload = function() {

    for( var i = 0; i < $scope.files.length ; i++ ){
        var fd = new FormData();
        fd.append('file', $scope.files[i]);

        $http.post("/api/documents/addDocument",fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).success(function(){
            alert("success");
        });
    }

};


$scope.clean_document_list = function(){
    $scope.files = [];
    $scope.$apply();
}

})

.directive('uploader', function( $rootScope ){
    return{
        restrict : 'A',
        scope : {
            cryptfiles : '=',
            uploader : '=',
        },
        link : function(  scope, element, attributs ){
            element.on('change', function(event){
                    scope.$apply( function(scope){
                        scope.cryptfiles.push( event.target.files[0] )
                    });
            });

        },
    };

});
