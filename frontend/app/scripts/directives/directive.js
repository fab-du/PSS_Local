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
        controller : function( $scope, $rootScope, $window, $mdToast, $http, Rest, $timeout, $compile, $filter, store, Auth, usSpinnerService, Upload ){


            $scope.files = [];
            $scope.filesString = [];

            if ( angular.isUndefined( $scope.groupFiles ) ){
                var groupId = $rootScope.groupId || store.get('currentGroupId');
                console.log("===================================");
                console.log( groupId );
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
                var docId   = $scope.groupFiles[ index ].id;
                var docName = $scope.groupFiles[ index ].name;
                var groupId = $rootScope.groupId || store.get('currentGroupId');
                var url     = "/api/groups/" + groupId + "/documents/" + docId + "/download/" + docName  ;
                $http.get( url, { headers : { 'Content-Type' : 'application/json; charset=utf-8' }, responseType : 'arraybuffer' } ).then( function( response ){
                    console.log( response );
                });
            };

            $scope.upload  = function( index ){
                var file = $scope.filesString[ index ];
                var currentUserId = '';

                var groupId = $rootScope.groupId || store.get('currentGroupId');
                var url = "/api/groups/" + groupId + "/documents";
                var promise = Upload.upload({
                    url: url,
                    data: {file: file}
                });

                // start upload-indicator
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
.directive( 'find',function() {
return {
    templateUrl : '/views/widget.find.html'
};
})
