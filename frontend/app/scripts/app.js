'use strict';

/**
 * @ngdoc overview
 * @name cryptClientApp
 * @description
 * # cryptClientApp
 *
 * Main module of the application.
 */

/*
 * Angular Independant 
 *
 * Problem : Angular Promise return a fairly sl*t Array as response. 
 *           this response is bound with many promise related object
 *           that we dont need and which cause some interface that only
 *           need the Array AS-IS to break. 
 * Solution : extends Array object with an function that can sort the Array
 *            return from the promise.
 */

Array.prototype.sortPromise = function(){
    var _self = this;
    var response = new Array();

    _self.forEach( function(item){
        response.push( item );
    });

    return response;
};



angular
  .module('cryptClientApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngMaterial',
    'ui.router',
    /*
     *'ngMockE2E',
     */
    'smart-table',
    'ngFileUpload',
    'ngSanitize'
  ])
  .config(function ($routeProvider, $resourceProvider, $stateProvider) {


      $stateProvider
      .state('main', {
        templateUrl : "/views/scratch.html"
      });

      $stateProvider
      .state('login', {
            url : "/session/login",
            controller : "LoginController",
            templateUrl : "/views/session/login.html",
            templateProvider : function($templateCache){
                var templ = $templateCache.get('/views/session/login.html');
            }
      })
      .state('register', {
            controller : "RegisterController",
            templateUrl : "/views/session/register.html",
            templateProvider : function($templateCache){
                return $templateCache.get('/views/session/register.html');
            }
      });

      $stateProvider
      .state('users', {
          url : '/users', 
          controller : "UsersController",
          templateUrl : "/views/users.html",
            templateProvider : function($templateCache){
                return $templateCache.get('/views/users.html');
            }
      })
      .state('users.groups', {
          url : '/users_grp_admin',
          controller : 'UserGroupController',
          templateUrl : '/views/groups/mygroups.html',
            templateProvider : function($templateCache){
                return $templateCache.get('/views/groups/mygroups.html');
            }
      })
      .state('users.userId', {
          url : '/:userId'
      })
      .state('users.userId.groups', {
          url : '/groups'
      })
      .state('users.userId.friends', {
          url : '/friends'
      })
      .state('users.userId.documents', {
          url : '/documents'
      });

      $stateProvider
      .state('groups', {
          url : '/groups',
          controller : 'GroupsController',
          templateUrl : '/views/groups.html'
      })
      .state('groups.groupId', {
          url : '/:groupId',
          template : '<div></div>',
          controller  : function( $scope ){
              console.log('come here');
          }
      })
      .state('groups.groupId.documents', {
          url : '/documents',
      })
      .state('groups.groupId.users', {
          url : '/users',
      });

      $stateProvider
      .state('documents', {
          url : '/documents',
          templateUrl : "/views/documents.html"
      })
      .state('documents.upload', {
          url : '/documents/upload',
          templateUrl : "/views/documents/documents.upload.html"
      })
      .state('documents.documentId', {
          url : '/:documentId'
      });

      $stateProvider
      .state('friends', {
          url : '/friends',
          templateUrl : "/views/friends.html"
      })
      .state('friends.friendId', {
          url : '/:friendId'
      })
      .state('friends.friendId.groups', {
          url : '/groups'
      })
      .state('friends.friendId.friends', {
          url : '/friends'
      });

      $routeProvider.otherwise({redirectTo: '/'});

})
.run(function ($rootScope, AUTH_EVENTS, Auth) {
    $rootScope.$on('$stateChangeStart', 
    function(){
        if( !Auth.isLoggedIn() ){
            $rootScope.$broadcast(AUTH_EVENTS.notAuthenticated);
        }

    });
})

