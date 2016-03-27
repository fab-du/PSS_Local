'use strict';
/**
 * @ngdoc function
 * @name cryptClientApp.controller:DocumentsCtrl
 * @description
 * # DocumentsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('DocumentsController', function ( $scope, Rest, $rootScope, $stateParams, $q, store ) {

var init = function(){
    Rest.Document.find( {  group : $stateParams.groupId }).$promise.then( function( documents ){
        console.log( documents )
        $scope.documents = documents;
    });
};
init();
})
.controller('DocumentsDetailController', function ( $scope, Rest, $q, $stateParams ) {
var init = function(){
    Rest.Document.findOne( { documentId : $stateParams.documentId } ).$promise.then( function( document ){
        $scope.document = document;
    });
};
init();
});
