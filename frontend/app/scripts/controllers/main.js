'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $q, $scope, $window, $rootScope, AUTH_EVENTS,  $state, $mdSidenav, $mdBottomSheet, $mdToast, Storage, Auth ) {

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
        "groups"    : {label : "groups"   , contents : ["admin", "member", "search" ] },
        "friends"   : {label : "friends"  , contents : ["find" ] },
    };

    $scope.openLeftMenu = function(){
        $mdSidenav("left").toggle().then( function(){
        });
    };


    $scope.onTabSelected = function( tablabel ){
        if( $rootScope.isLoggedIn ){
            $scope.title = tablabel;
            $state.go( tablabel );
        }
    };

    $scope.tabContentClick = function( tablabel, tabcontent ){
        if( $rootScope.isLoggedIn ){
            $state.go( tablabel + "." + tabcontent );
        }
    };


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


    // auth message handler 
	$rootScope.$on(AUTH_EVENTS.notAuthorized, function(){
        Storage.deleteAll();
        console.log( AUTH_EVENTS.notAuthorized );
    });
	$rootScope.$on(AUTH_EVENTS.notAuthenticated, function(){
        $rootScope.isLoggedIn = false;
        Storage.remove();
        $window.location.href = "/#/session/login"
    });
	$rootScope.$on(AUTH_EVENTS.sessionTimeout, function(){
        console.log( AUTH_EVENTS.sessionTimeout );
        Storage.remove();
        $window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(){
        $rootScope.isLoggedIn = false;
        Storage.remove();
        $window.location.href = "/"
    });
	$rootScope.$on(AUTH_EVENTS.loginSuccess, function(){
        $rootScope.isLoggedIn = Auth.isLoggedIn();
        $window.location.href = "#/";
    });

	$rootScope.$on(AUTH_EVENTS.loginFailed, function(){
        $window.location.href = "#/";
        $rootScope.isLoggedIn = false;
        Storage.remove();
    });

	$rootScope.$on(AUTH_EVENTS.notFound, function(){
        showMessage("success-toast",  AUTH_EVENTS.notFound );
    });


});
