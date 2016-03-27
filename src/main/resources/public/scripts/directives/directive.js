'use strict';

angular.module('cryptClientApp')
.directive('tabUser', function( ){
return {
    restrict     : 'E',
    templateUrl  : '/views/users/widget.user.list.html',
    scope        : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items', // two way data binding with parent scope
        onSelect : '&' // mehod
    },
    controller : function( $scope, Rest, Auth, store, $state ){
        $scope.headers = ['id', 'firstname', 'secondname', 'email'];
        $scope.items = null; 

        $scope.addFriend = function( index ){
            Rest.Friend.addFriend({currentUserId:store.get("currentUserId")}, { id : $scope.items[index].id } ).$promise.then(function(){
                $state.reload();
            });
        };
    }

};
})
.directive('tabFriend', function(){
return {
    restrict : 'E',
    templateUrl : '/views/users/widget.friend.list.html',
    scope : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items', // two way data binding with parent scope
        onSelect : '&' // mehod
    },
    controller : function( $scope , Rest, store, Auth ){
        $scope.items = null; 
        $scope.headers = ['id', 'firstname', 'secondname', 'email'];

        $scope.revoke = function( index ){
            Rest.Friend.revoke( {}, { friendId : $scope.items[index].id, currentUserId : store.get("currentUserId") } ).$promise.then( function( ){
                if (index !== -1) {
                    $scope.items.splice(index, 1);
                }
            });
        };
    },

    link: function( scope, el, attrs ){

        scope.$watch( 'items', function( _old, _new ){
           if( _old !== null ){
           }
        });

    }

};
})
.directive('tabDocument', function(){
return {
    restrict : 'E',
    templateUrl : "/views/documents/widget.document.tab.html",
    scope : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items', // two way data binding with parent scope
        onSelect : '&' // mehod
    },
    controller : function( $scope ){
        $scope.items = null; 
        $scope.headers = [ "id", "path", "name", "created at" ];
    },

    link: function( scope, el, attrs ){}
};
})
.directive('tabGroup', function(){
return {
    restrict : 'E',
    templateUrl : '/views/groups/widget.group.list.html',
    scope : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items' // two way data binding with parent scope
    },
    controller : function( $scope, $state ){
        $scope.items = null; 
        $scope.headers = [ "id", "name"];
        $scope.details = function( row ){
             $state.go('groups.groupId', { groupId : row.id });
        };
    },
    link: function( scope, el, attrs ){}
};
})
.directive('uploader', function( ){
    return{
        restrict : 'E',
        transclude : true,
        templateUrl : '/views/documents/widget.uploader.html',
        scope : {
            groupFiles : '='
        },
        controller : function( $scope, $rootScope, Documents, $stateParams, $window, $mdToast, $http, Rest, $timeout, $compile, $filter, store, Auth, usSpinnerService, Upload ){

            $scope.files = [];
            $scope.filesString = [];

            if ( angular.isUndefined( $scope.groupFiles ) ){
                var groupId = $stateParams.groupId || store.get('currentGroupId');
                Rest.Group.documents({ groupId : groupId }).$promise.then( function( documents ){
                    $scope.groupFiles = documents;
                });
            }

            $scope.$watch( "files", function( _new, _old ){
                if ( _new !== _old  && _new !== null ){
                    $scope.filesString.push( _new[0] );
                }
            });

            $scope.removeFile = function( index ){
                $scope.filesString.pop(index); 
            };

            $scope.download = function( index ){
                var doc   = $scope.groupFiles[ index ];
                var groupId = $stateParams.groupId || store.get('currentGroupId');
                Documents.download( doc, groupId );
            };

            $scope.upload  = function( index ){
                var file = $scope.filesString[ index ];
                var currentUserId = store.get('currentUserId');

                var groupId = $stateParams.groupId || store.get('currentGroupId');
                var url = "/api/groups/" + groupId + "/documents";
                var promise = Upload.upload({
                    url: url,
                    data: {file: file}
                });

                usSpinnerService.spin('spinner-upload-' + index);
                promise.then( function( res ){

                    $timeout( function(){
                        var index = null;
                        angular.forEach( $scope.filesString, function(v, k){
                            if ( v.name === res.data.name ) {
                                index = k;
                            }
                        });

                        if( index !== null ){
                            $scope.removeFile( index );
                        }

                        usSpinnerService.stop('spinner-upload-' + index);

                        $mdToast.show({
                            template: '<md-toast class="md-toast success">' + res.data.name + " uploaded in "+ res.data.path + '</md-toast>',
                            hideDelay: 4000,
                            position: 'center left'
                        });

                    }, 1000 );

                }, function( err){
                    usSpinnerService.stop('spinner-upload-' + index);
                });

            }

            $scope.uploadAll = function(){
                angular.forEach($scope.filesString, function(file, index) {
                    $scope.upload( index );
                });
            };

        },

        link : function($scope, element, attributs){
            $scope.$watch('groupFiles')
        },
    };
})
.directive('cryptUpload', function(){
  return {
    restrict : 'E',
    transclude : true,
    templateUrl: '/views/documents/widget.document.upload.html',
    link : function( $scope, el, attr ){
        console.log('commer heeee');
    }
  };
});


/*
 *<uploader groupFiles="groupFiles"> </uploader>
 *
 *<!--
 *   -<table class="table table-hover table-striped">
 *   -    <thead>
 *   -        <tr>
 *   -            <th> {{ 'groups.group' | translate }} : groupname  </th>
 *   -            <th></th>
 *   -            <th></th>
 *   -            <th></th>
 *   -            <th></th>
 *   -            <th></th>
 *   -        </tr>
 *   -        <tr>
 *   -            <th>{{ 'documents.name' | translate }}</th>
 *   -            <th> {{ 'documents.path' | translate }}</th>
 *   -            <th>createdAt</th>
 *   -            <th>{{ 'documents.size' | translate }}</th>
 *   -            <th>{{ 'documents.type' | translate }}</th>
 *   -            <th></th>
 *   -        </tr>
 *   -    </thead>
 *   -    <tbody>
 *   -        <tr class="sibling" ng-repeat="_file in documents">
 *   -            <td> {{ _file.name  }} </td>
 *   -            <td> {{ _file.path  }} </td>
 *   -            <td> {{ _file.createdAt | date  }} </td>
 *   -            <td> {{ _file.size  }} </td>
 *   -            <td> {{ _file.type  }} </td>
 *   -            <td> 
 *   -
 *   -                <div class="container-fluid">
 *   -                    <div>
 *   -                        <button ng-click="download($index)" class="btn btn-success btn-sm"> 
 *   -                            <span class="glyphicon glyphicon-download-alt" ></span>
 *   -                        </button>
 *   -                    </div>
 *   -                    
 *   -                    <div>
 *   -                        <button class="btn btn-danger btn-sm"> 
 *   -                            <span class="glyphicon glyphicon-minus" ></span>
 *   -                        </button>
 *   -                    </div>
 *   -                    
 *   -                    <div class="dropdown">
 *   -                        <button class="btn btn-info btn-sm dropdown-toggle" type="button" id="shareDropdown" data-toggle="dropdown" aria-expanded="false"> 
 *   -                            <span class="glyphicon glyphicon-share" ></span>
 *   -                        </button>
 *   -                        <ul class="dropdown-menu" aria-labelledby="shareDropdown">
 *   -                            <li><a href="#">share with users  </a></li>
 *   -                            <li><a href="#">share with friends</a></li>
 *   -                            <li><a href="#">share with group  </a></li>
 *   -                        </ul>
 *   -                    </div>
 *   -                </div>
 *   -
 *   -            </td>
 *   -        </tr>
 *   -    </tbody>
 *   -</table>
 *   -->
 */
