'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:DocumentsCtrl
 * @description
 * # DocumentsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('DocumentsController', function ( $scope ) {

function init( ){

$scope.filesString = [];
$scope.files = [];

$scope.startUpload = function(){

};

$scope.clean_documents_list = function(){

};

$scope.$watch( "files", function( newFiles, oldFiles ){
    if( newFiles !== oldFiles ){
        $scope.filesString.push( newFiels );
    }

});


}

init();

});
