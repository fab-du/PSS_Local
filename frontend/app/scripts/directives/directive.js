'use strict';

/**
 * @ngdoc directive
 * @name cryptClientApp.directive:directive
 * @description
 * # directive
 */
angular.module('cryptClientApp')
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
.directive('tabUser', function( ){
return {
    restrict : 'E',
    template : '<h4> {{ title }} <h4> <hr/><table st-table="items" class="table table-striped" st-sticky-header>'                     +
               '<thead>'                                                                           +
               '<tr>'                                                                              +
               '<th ng-repeat="header in headers">{{header}}'                                      +
               '</th>'                                                                             +
               '</tr>'                                                                             +
               '</thead>'                                                                          +
               '<tbody>'                                                                           +
               '<tr st-select-row="row" st-select-mode="multiple"  ng-repeat="row in items" >'    +
               '<td ng-repeat="header in headers">{{ row[header] }}</td>'+
               '<td>'+
               '<button class="btn btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-eye-open"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-info btn-sm" popover-placement="top" type="button" ng-click="addFriend($index)">'+
               '<i class="glyphicon glyphicon-plus"></i>                                             '+
               '</button>                                                                                '+
               '</td>'+
               '</tbody>'+
               '</table>',
    scope        : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items', // two way data binding with parent scope
        onSelect : '&' // mehod
    },
    controller : function( $scope, Rest ){
        $scope.headers = ['id', 'firstname', 'secondname', 'email'];
        $scope.items = null; 

        $scope.addFriend = function( index ){
            Rest.Friend.addFriend( { id : $scope.items[index].id } ).$promise.then( function( ){
                console.log("do this request");
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
.directive('tabFriend', function(){
return {
    restrict : 'E',
    template : '<h4> {{ title }} <h4> <hr/> <table st-table="items" class="table table-striped" st-sticky-header>'                     +
               '<thead>'                                                                           +
               '<tr>'                                                                              +
               '<th ng-repeat="header in headers">{{header}}'                                      +
               '</th>'                                                                             +
               '</tr>'                                                                             +
               '</thead>'                                                                          +
               '<tbody>'                                                                           +
               '<tr  st-select-row="row" st-select-mode="multiple"  ng-repeat="row in items" >'    +
               '<td ng-repeat="header in headers">{{ row[header] }}</td>'+
               '<td>'+
               '<button class="btn btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-eye-open"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-danger btn-sm" popover-placement="top" type="button" ng-click="revoke($index)">'+
               '<i class="glyphicon glyphicon-minus"></i>                                             '+
               '</button>                                                                                '+
               '</td>'+
               '</tbody>'+
               '</table>',
    scope : {
        title    : '@', // String binding
        items    : '=', // two way data binding with parent scope
        stTable  : '=items', // two way data binding with parent scope
        onSelect : '&' // mehod
    },
    controller : function( $scope , Rest ){
        $scope.items = null; 
        $scope.headers = ['id', 'firstname', 'secondname', 'email'];

        $scope.revoke = function( index ){
            console.log( index );
            Rest.Friend.revoke( { }, { friendId : $scope.items[index].id } ).$promise.then( function( ){
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
    template : '<table st-table="items" class="table table-striped" st-sticky-header>'                     +
               '<thead>'                                                                           +
               '<tr>'                                                                              +
               '<th ng-repeat="header in headers">{{header}}'                                      +
               '</th>'                                                                             +
               '</tr>'                                                                             +
               '</thead>'                                                                          +
               '<tbody>'                                                                           +
               '<tr  st-select-row="row" st-select-mode="multiple"  ng-repeat="row in items" >'    +
               '<td ng-repeat="header in headers">{{ row[header] }}</td>'+
               '<td>'+
               '<button class="btn btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-eye-open"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-success btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-share"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-danger btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-minus"></i>                                             '+
               '</button>                                                                                '+
               '</td>'+
               '</tbody>'+
               '</table>',
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

    link: function( scope, el, attrs ){

        scope.$watch( 'items', function( _old, _new ){
           if( _old !== null ){
           }
        });

    }

};
})
.directive('tabGroup', function(){
return {
    restrict : 'E',
    template : '<h4> {{ title }} <h4> <hr/><table st-table="items" class="table table-striped" st-sticky-header>'                     +
               '<thead>'                                                                           +
               '<tr>'                                                                              +
               '<th ng-repeat="header in headers">{{header}}'                                      +
               '</th>'                                                                             +
               '</tr>'                                                                             +
               '</thead>'                                                                          +
               '<tbody>'                                                                           +
               '<tr  st-select-row="row" st-select-mode="multiple"  ng-repeat="row in items" >'    +
               '<td ng-repeat="header in headers">{{ row[header] }}</td>'+
               '<td>'+
               '<button class="btn btn-sm" popover-placement="top" type="button"  ng-click="details(row)">'+
               '<i class="glyphicon glyphicon-eye-open"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-success btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-share"></i>                                             '+
               '</button>                                                                                '+
               '<button class="btn btn-danger btn-sm" popover-placement="top" type="button">'+
               '<i class="glyphicon glyphicon-minus"></i>                                             '+
               '</button>                                                                                '+
               '</td>'+
               '</tbody>'+
               '</table>',
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

    link: function( scope, el, attrs ){

        scope.$watch( 'items', function( _old, _new ){
           if( _old !== null ){
           }
        });

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
               });
               
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
        template :  '<div id="selectBox" class="md-alert" ng-hide="closed" on-close="closed=true" ><div><input type="text" style="width:200px;" name="" id="" value="{{substring}}" ng-model="substring" /></div>'+
                     '<div id="elements" ><div class="selectClass" ng-click="matchFilterSelect(it)" ng-repeat="it in data track by $email | filter:substring"> {{ it }} <hr style="background=lightblue"/>  </div></div></div>',

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

