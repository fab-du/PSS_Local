'use strict';

/**
 * @ngdoc function
 * @name cryptClientApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the cryptClientApp
 */
angular.module('cryptClientApp')

.controller('MainController', function ( $scope, $state ) {

    $scope.tabs = 
    {
        "users"     : {label : "users"    , contents : [ "users", "friends", "groups", "search"]  },
        "documents" : {label : "documents", contents : ["private", "groups" ] },
        "groups"    : {label : "groups"   , contents : ["admin", "member", "search" ] },
        "friends"   : {label : "friends"  , contents : [ "find" ] },
    }

    $scope.onTabSelected = function( tablabel ){
        $state.go( tablabel );
    };

    $scope.tabContentClick = function( tablabel, tabcontent ){
        $state.go( tablabel + "." + tabcontent );
    };

});
