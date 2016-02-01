'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $q, $scope, usSpinnerService, $window, $timeout,  $rootScope, AUTH_EVENTS,  $state, $mdSidenav, $mdBottomSheet, $mdToast, Storage, Auth, $mdDialog, Rest ) {

   var timo= $timeout( function(){
        usSpinnerService.spin('spinner-1');
    }, 40000 ).then( function(){ usSpinnerService.stop('spinner-1');})

    console.log( timo )

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


    $scope.navBarShowL = true;
    $scope.navBarShowR = !$scope.navBarShowL;
    /*
     * navbar : 0 left, 1 right
     * */
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

            Rest.Group.mygroups( { suffix : currentUserId} ).$promise.then( function( mygroups ){
                $scope.mygroups = mygroups;
            });
           $state.reload();
        } 
    });

    $scope.dragSuccess = function( data, ev ){
        console.log( ev.event )
    }

    $scope.dropSuccess = function( data, ev, _self ){
        console.log( _self );
        /*
         *console.log( ev.event );
         */
        /*
         *console.log( data );
         */
    }


    /*
     *$scope.$watch('navBarShow', function(_new, _old){
     *   $scope.navBarToggle = _new; 
     *});
     */


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
