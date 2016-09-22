'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $q, $scope, usSpinnerService, $window, $timeout,  $rootScope, AUTH_EVENTS,  $state, $translate, $mdSidenav, $mdBottomSheet, $mdToast, store, Auth, $mdDialog, Rest ,ERRORS_EVENTS, SUCCESS_EVENTS ) {
    $scope.prefferedLanguage = $translate.use();
    $scope.switchLanguage = function (key) {
        var currentkey = $translate.use();
        if ( currentkey !== key ){
            $translate.use( key );
            $scope.prefferedLanguage = key;
        }
    };

   var timo= $timeout( function(){
        usSpinnerService.spin('spinner-1');
    }, 40000 ).then( function(){ usSpinnerService.stop('spinner-1');})

    $scope.errors = "";
    $rootScope.isLoggedIn = false;
    $scope.$watch( "errors", function( newErrors , oldErrors ){
        if( newErrors !== oldErrors  ){
            $scope.errors = newErrors; 
        }
    });


    /*
    * navbar : 0 left, 1 right
    * */
    $scope.navBarShowL = false;
    $scope.navBarShowR = !$scope.navBarShowL;
    $scope.toggleNavBar = function( navbar ){
        if( navbar === 0 ){
            var toggle = $scope.navBarShowL;
            $scope.navBarHideL = true;
            $scope.navBarHideL = toggle;
            $scope.navBarHideR = true;
            $scope.navBarShowL = !toggle;
        }

        if ( navbar === 1 ){
            var toggle = $scope.navBarShowR;
            $scope.navBarHide = true;
            $scope.navBarHideR = toggle;
            $scope.navBarHideL = true;
            $scope.navBarShowR = !toggle;
        }
    };


    // watchers 
    $scope.friends = [];
    $scope.mygroups = [];

    $rootScope.$watch('isLoggedIn', function(n,o){
        if ( n === true ){
            var currentUserId = Auth.getCurrentUser().currentUserId;
            Rest.Friend.find( { currentUserId : currentUserId  } ).$promise.then( function( friends ){
                $scope.friends = friends;
            });

            $rootScope.currentUserGroupId = store.get('currentUserGroupId');

            Rest.Group.mygroups( { suffix1 : Auth.getCurrentUser().currentUserId } ).$promise.then( function( mygroups ){
                $scope.mygroups = mygroups;
            });
        } 
    });

    $scope.dropSuccess = function( data, ev, _self ){
        Rest.Friend.addToGroup( { friendId : data.id, currentUserId : Auth.getCurrentUser().currentUserId, param : _self.id  }).$promise.then( function(resp){ } )
    }

    function showMessage( type, msg ){
        $mdToast.show({
                template: '<md-toast class="md-toast ' + type +'">' + msg + '</md-toast>',
                hideDelay: 6000,
                position: 'center left'
        });
    }


    $scope.showAddGroup = showAddGroup;  
    function showAddGroup( actionOnSuccess ){
        var content = {
            parent : angular.element('window'),
            template:'<md-dialog aria-label="Mango (Fruit)"> <md-content class="md-padding"> <form name="newGroupForm"> <div layout layout-sm="column"> <md-input-container flex> <label>Groupname</label> <input ng-model="newgroup.name" placeholder="Placeholder text"> </md-input-container> </md-content> <div class="md-actions" layout="row"> <span flex></span> <md-button ng-click="closeDialog()"> Cancel </md-button> <md-button ng-click="success( newgroup )" class="md-primary"> Save </md-button> </div></md-dialog>',
            controller : function ( $scope, $mdDialog ){
                $scope.newgroup = {};
                $scope.closeDialog = function () {
                    $mdDialog.hide( );
                };

                $scope.success = function( groupname ){
                    if( groupname === null || groupname === undefined ){
                        return null;
                    }

                    if(  Auth.isLoggedIn() ){
                        var currentUserId = Auth.getCurrentUser().currentUserId;
                        var obj = { gvid : currentUserId, name : groupname.name };
                        Rest.Group.create( obj ).$promise.then( function(){
                            $state.reload();
                        });
                    }
                    $mdDialog.hide( );
                };

            }
        };
        var dialog = $mdDialog.show( content );
    }


    // Message handler
	$rootScope.$on(AUTH_EVENTS.notAuthorized, function(){
        store.remove();
    });
	$rootScope.$on(AUTH_EVENTS.notAuthenticated, function(){
        $rootScope.isLoggedIn = false;
        store.remove();
        window.location.href = "/#/session/login"
    });
	$rootScope.$on(AUTH_EVENTS.sessionTimeout, function(){
        store.remove();
        window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(){
        $rootScope.isLoggedIn = false;
        store.remove();
        window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.loginSuccess, function(){
        $rootScope.isLoggedIn = Auth.isLoggedIn();
        $state.go('users');
    });

	$rootScope.$on(AUTH_EVENTS.notFound, function(){
        showMessage("success-toast",  AUTH_EVENTS.notFound );
    });

	$rootScope.$on(AUTH_EVENTS.notFound, function(){
        showMessage("success-toast",  AUTH_EVENTS.notFound );
    });

	$rootScope.$on(AUTH_EVENTS.badRequest, function(){
        showMessage("success-toast",  "Bad Request" );
    });

	$rootScope.$on(SUCCESS_EVENTS.deleted, function(){
        showMessage("success-toast",  SUCCESS_EVENTS.deleted );
    });

	$rootScope.$on(SUCCESS_EVENTS.created, function(){
        showMessage("success-toast",  SUCCESS_EVENTS.created );
    });

});
