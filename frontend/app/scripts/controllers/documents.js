'use strict';
/**
 * @ngdoc function
 * @name cryptClientApp.controller:DocumentsCtrl
 * @description
 * # DocumentsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('DocumentsController', function ( $scope, Rest, $rootScope, $q, store ) {

var init = function(){
    Rest.Group.documents( { groupId : store.get("currentUserGroupId") }).$promise.then( function( documents ){
        $scope.documents = documents;
    });
    $rootScope.groupId = store.get("currentUserGroupId");
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
