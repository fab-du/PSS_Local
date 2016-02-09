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
    'ngDraggable',
    'smart-table',
    'angularSpinner',
    'ngFileUpload',
    'ngSanitize'
  ])
  .config(function ($routeProvider, $resourceProvider, $httpProvider, $stateProvider, usSpinnerConfigProvider) {

    usSpinnerConfigProvider.setDefaults({color: '#5CB85C', radius:1});

    $httpProvider.defaults.xsrfHeaderName = "X-XSRFToken";
    $httpProvider.defaults.xsrfCookieName = 'csrftoken';
    $httpProvider.defaults.headers["hash-algorithm"] = { "hash-algorithm" : "SHA256" };
    $httpProvider.defaults.headers.Authorization = "SRP";
    $httpProvider.defaults.headers["WWW-Authenticate"] = "SRP";
    $httpProvider.defaults.headers.realm = "realm";

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
          templateUrl : '/views/groups/mygroups.html'
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
          templateUrl : '/views/groups.html',
      })
      .state('groups.groupId', {
          url        : '/:groupId',
          templateUrl: '/views/groups/groupdetails.html',
          controller : 'GroupDetailsController'
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

