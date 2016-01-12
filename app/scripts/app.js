'use strict';

/**
 * @ngdoc overview
 * @name cryptClientApp
 * @description
 * # cryptClientApp
 *
 * Main module of the application.
 */
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
    'ngMockE2E',
    'ui.grid',
    'ui.grid.selection',
    'ngSanitize'
  ])
  .config(function ($routeProvider, $resourceProvider, $stateProvider, $mdThemingProvider, $uiViewScrollProvider,uiGridConstants ) {

      $resourceProvider.defaults.stripTrailingSlashes = false;

      $stateProvider
      .state('login', {
            templateUrl : "/views/session/login.html"
      })
      .state('logout',{

      })
      .state('register', {
            templateUrl : "/views/session/register.html"

      });

      $stateProvider
      .state('users', {
          url : '/users', 
          templateUrl : "/views/users.html"
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
          templateUrl : "/views/groups.html"
      })
      .state('groups.admin', {
          url : '/groups_admin',
          templateUrl : "/views/groups.html"
      })
      .state('groups.groupId', {
          url : '/:groupId'
      })
      .state('groups.groupId.documents', {
          url : '/documents'
      })
      .state('groups.groupId.users', {
          url : '/users'
      });

      $stateProvider
      .state('documents', {
          url : '/documents',
          templateUrl : "/views/documents.html"
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

});
