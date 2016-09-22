'use strict';

angular.module('cryptClientApp')
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
        $scope.headers = [ "name"];
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
        controller : function( $scope, $rootScope, Documents, $stateParams, $window, $mdToast, $http, Rest, $timeout, $compile, $filter, $state, store, Auth, usSpinnerService, Upload ){

            $scope.files = [];
            $scope.filesString = [];

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

                        $state.reload();
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
.directive('cryptFind', function(){
  return {
    restrict : 'E',
    transclude : true,
    scope : {
        items : '='
    },
    templateUrl: '/views/widget.find.html',
    link : function( scope, el, attr ){
        var element = angular.element( document.querySelector('input.typeahead') )


        scope.$watch('items', initialize);
        function initialize() {
            var users = [];
            angular.forEach( scope.items , function ( v ){
                users.push( v.email );
            })

            element.typeahead( { highlight : false, hint : true }, { name : 'User' , source : substringMatcher( users ) } );
            console.log( scope.items )
        }

var substringMatcher = function(strs) {
  return function findMatches(q, cb) {
    var matches, substringRegex;

    // an array that will be populated with substring matches
    matches = [];

    // regex used to determine if a string contains the substring `q`
    substringRegex = new RegExp(q, 'i');

    // iterate through the pool of strings and for any string that
    // contains the substring `q`, add it to the `matches` array
    angular.forEach(strs, function(str,  i) {
      if (substringRegex.test(str)) {
        matches.push(str);
      }
    });

    cb(matches);
  };
};



    }
  };
});

