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
 * Problem :  Angular Promise return a fairly sl*t Array as response. 
 *            this response is bound with many promise related object
 *            that we dont need and which cause some interface that only
 *            need the Array AS-IS to break. 
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
    'angular-storage',
    'ngDraggable',
    'smart-table',
    'angularSpinner',
    'ngFileUpload',
    'pascalprecht.translate',
    'ngSanitize'
  ])
  .config(function ($routeProvider, $resourceProvider, $httpProvider, $stateProvider, usSpinnerConfigProvider, $translateProvider ) {

    usSpinnerConfigProvider.setDefaults({color: '#5CB85C', radius:1});
    $translateProvider.preferredLanguage('de');
    $translateProvider.useStaticFilesLoader({prefix: 'i18n/', suffix: '.json'})
    $httpProvider.defaults.xsrfHeaderName = "X-XSRFToken";
    $httpProvider.defaults.xsrfCookieName = 'csrftoken';
    $httpProvider.defaults.headers["hash-algorithm"] = { "hash-algorithm" : "SHA256" };
    $httpProvider.defaults.headers.Authorization = "SRP";
    $httpProvider.defaults.headers["WWW-Authenticate"] = "SRP";
    $httpProvider.defaults.headers.realm = "realm";

      $stateProvider
      .state('login', {
          url : "/session/login",
          controller : "LoginController",
          templateUrl : "/views/session/login.html"
      })
      .state('register', {
          url : "/session/register",
          controller : "RegisterController",
          templateUrl : "/views/session/register.html"
      });

      $stateProvider
      .state('users', {
          url : '/users', 
          controller : "UsersController",
          templateUrl : "/views/users.html"
      })
      .state('users.userId', {
          url : '/:userId', 
          controller : 'UserDetailsController',
          templateUrl : '/views/users/users.id.html'
      })

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
      .state('groups.groupId.details', {
          url         : '/details',
          parent : 'groups.groupId',
          views       : { "@" : {
              templateUrl : '/views/groups/groupdetails.details.html',
              controller  : 'GroupMoreDetailsController'
          }},
      });

      $stateProvider
      .state('documents',{
          controller : function($scope, $rootScope, $state ){ console.log('documents controller') /*just a work around*/ },
          url : '/documents',
          template: '<ui-view></ui-view>'
      })
      .state('documents.groupId', {
          url : '/:groupId',
          controller : 'DocumentsController',
          templateUrl : "/views/documents.html"
      })
      .state('documents.groupId.upload', {
          url : '/upload',
          controller : function($scope, $stateParams){},
          templateUrl : "/views/documents.html"
      })
      .state('groupId.documents.documentId', {
          controller : function(){},
          url : '/:groupId/documents/:documentId'
      });

      $stateProvider
      .state('friends', {
          url : '/friends',
          templateUrl : "/views/friends.html",
          controller : "FriendController"
      })
      .state('friends.friendId', {
          url : '/:friendId',
          controller : "FriendDetailController"
      })

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
