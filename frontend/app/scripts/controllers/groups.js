'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:GroupsCtrl
 * @description
 * # GroupsCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')
.controller('GroupsController', function ( Rest, Storage, $scope, Auth  ) {

    function init(){

            if( !Auth.isLoggedIn() ){
               return;
            } 

            Rest.Group.find().$promise.then( function( groups ){
                $scope.groups = groups;
            });

            Rest.Group.mygroups( { suffix : Auth.getCurrentUser().currentUserId } ).$promise.then( function( mygroups ){
                $scope.mygroups = mygroups;
            });
    }


    init();
})
.controller('GroupDetailsController', function( Rest, $filter, Storage, $scope, $stateParams,   Auth){
    Rest.Group.findOne({ groupId : $stateParams.groupId }).$promise.then( function( group ){
        console.log( group );
        var _group = group;
        _group['createdAt'] = $filter('date')( group['createdAt'] );

        Rest.Group.users({ prefix : $stateParams.groupId }).$promise.then( function( users ){
            $scope.currentGroupUsers = users; 
            var gvid = _group.gvid;
            _group.gvid = null;
            var gv = $filter('getById')( users, 'id', gvid );
            _group.gvid = gv.email; 
            _group.users = users.length;     
        });

        Rest.Group.documents({ prefix : $stateParams.groupId }).$promise.then( function( documents ){
            $scope.currentGroupDocuments = documents; 
            var _documents = document.length; 
            _group.documents = documents; 
        });

        $scope.group = _group; 
    });

});

