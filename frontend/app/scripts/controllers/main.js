'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $q, $scope, $window, $rootScope, AUTH_EVENTS,  $state, $mdSidenav, $mdBottomSheet, $mdToast, Storage, Auth, $mdDialog, Rest ) {
    $scope.errors = "";
    $rootScope.isLoggedIn = false;
    $scope.$watch( "errors", function( newErrors , oldErrors ){
        if( newErrors !== oldErrors  ){
            $scope.errors = newErrors; 
        }
    });

    $scope.tabs = 
    {
        "users"     : {label : "users"    , contents : ["friends", "groups", "search"]  },
        "documents" : {label : "documents", contents : ["private", "groups" ] },
        "groups"    : {label : "groups"   , contents : ["admin", "member", "search", "add" ] },
        "friends"   : {label : "friends"  , contents : ["find" ] },
    };

    $scope.openLeftMenu = function(){
        $mdSidenav("left").toggle();
    };


    $scope.onTabSelected = function( tablabel ){
        if( $rootScope.isLoggedIn ){
            $scope.title = tablabel;
            $state.go( tablabel );
        }
    };

    $scope.tabContentClick = function( tablabel, tabcontent ){
        if( tabcontent === "add" && tablabel === "groups" ){
            $scope.showAddGroup();
        }

        if( $rootScope.isLoggedIn && tabcontent !== "add" ){
            $state.go( tablabel + "." + tabcontent );
        }
    };


    $scope.navBarShow = true;
    $scope.toggleNavBar = function(){
        $scope.navBarHide = true;
        var toggle = $scope.navBarShow;
        $scope.navBarHide = toggle;
        $scope.navBarShow = !toggle;
    };

    $scope.$watch('navBarShow', function(_new, _old){
        console.log( _new )
       $scope.navBarToggle = _new; 
    });


    $scope.goHome = function( ){
        $state.go("main");
    };

    $scope.goUpload = function( ){
        $state.go("documents.upload");
    };

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
                        console.log( obj )
                        Rest.Group.create( obj ).$promise.then( function(){
                        });
                    }
                    $mdDialog.hide( );
                };

            }
        };

        var dialog = $mdDialog.show( content );
    }




    // auth message handler 
	$rootScope.$on(AUTH_EVENTS.notAuthorized, function(){
        Storage.deleteAll();
        console.log( AUTH_EVENTS.notAuthorized );
    });
	$rootScope.$on(AUTH_EVENTS.notAuthenticated, function(){
        $rootScope.isLoggedIn = false;
        Storage.remove();
        window.location.href = "/#/session/login"
    });
	$rootScope.$on(AUTH_EVENTS.sessionTimeout, function(){
        console.log( AUTH_EVENTS.sessionTimeout );
        Storage.remove();
        window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(){
        $rootScope.isLoggedIn = false;
        Storage.remove();
        window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.loginSuccess, function(){
        $rootScope.isLoggedIn = Auth.isLoggedIn();
        window.location.href = "#/users";
    });

	$rootScope.$on(AUTH_EVENTS.notFound, function(){
        showMessage("success-toast",  AUTH_EVENTS.notFound );
    });

});
