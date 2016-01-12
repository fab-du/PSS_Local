'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $scope, $state, Storage, $http ) {


    Storage.set( "currentUser" , { email : "tux@linux.com", x_token : "xxxx", id : 1 }  );
    Storage.set( "isLoggedIn" , true );

    $scope.tabs = 
    {
        "users"     : {label : "users"    , contents : ["friends", "groups", "search"]  },
        "documents" : {label : "documents", contents : ["private", "groups" ] },
        "groups"    : {label : "groups"   , contents : ["admin", "member", "search" ] },
        "friends"   : {label : "friends"  , contents : ["find" ] },
    }

    $scope.title = "Index";

    $scope.$watch( "title", function( new_title, old_title ){
        if( new_title !== old_title )  old_title = new_title; 
    })

    $scope.onTabSelected = function( tablabel ){
        $scope.title = tablabel;
        $state.go( tablabel );
    };

    $scope.tabContentClick = function( tablabel, tabcontent ){
        $state.go( tablabel + "." + tabcontent );
    };

});
