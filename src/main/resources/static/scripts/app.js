'use strict';

/**
 * @ngdoc overview
 * @name cryptoClientApp
 * @description
 * # cryptoClientApp
 *
 * Main module of the application.
 */
//'http-auth-interceptor'
angular
  .module('cryptoClientApp', [
    'ngAnimate',
    'ngAria',
    'ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ui.uploader',
    'ui.router',
    'ui.grid',
    'ui.grid.selection'
  ])
  .config(function ($routeProvider, $httpProvider, $locationProvider, $resourceProvider, $stateProvider, $provide ) {

    $stateProvider
      .state('index', {
        url : '/',
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'main'
      })
      .state('api', {
          url : '/api',
          templateUrl : 'views/api.html',
          controller : 'ApiCtrl'
      })
      .state('api.users', {
          url : '/api/users',
          templateUrl  : 'views/users/list.html',
          controller : 'CtrlUser'
      })
      .state('api.groups', {
          url : '/api/groups',
          templateUrl  : 'views/groups/list.html',
          controller : 'CtrlGroup'
      })
      .state('api.groups.id',{
          templateUrl : 'views/groups/detail.html'
      })
      .state('api.groups.id.upload',{
          templateUrl : 'views/documents/upload.html'
      })
       .state('api.documents', {
          url : '/api/documents',
          templateUrl  : 'views/documents/list.html',
      })
      .state('login', {
          ulr : '/session/login',
          templateUrl : 'views/login.html'
      })
      .state('register', {
          url : '/session/register',
          templateUrl : 'views/users/account.html'
      });



     $resourceProvider.defaults.stripTrailingSlashes = false;

    /*
     * Cross Site Request Forgery (XSRF) Protection
     * ============================================
     *
     * Ref : https://docs.angularjs.org/api/ng/service/$http 
     */
     $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
     $httpProvider.defaults.xsrfHeaderName =  'X-XSRF-TOKEN';
     $httpProvider.defaults.realm = 'realm';
     $httpProvider.defaults['hash-algorithm'] = 'sha256';
     $httpProvider.defaults['server-public-key']= '';
     $httpProvider.defaults['server-client-key']= '';
     $httpProvider.defaults['salt']= '';
     $httpProvider.defaults['Authorization']= 'SRP';
     $httpProvider.defaults['WWW-Authenticate']= 'SRP';




    $httpProvider.interceptors.push( function( $q ){
        return {

        /*
         * Automatically attach Authorization header
         *
         */
        
        request : function( config ){
            if( config.headers["X-XSRF-TOKEN"] === undefined ) config.header["X-XSRF-TOKEN"] = $cookieStore.get('X-XSRF-TOKEN');
            if( config.headers["Authorization"] === undefined ) config.header["Authorization"] = "SRP";
            if( config.headers["WWW-Authenticate"] === undefined ) config.header["WWW-Authenticate"] = "SRP";
            if( config.headers["hash-algorithm"] === undefined ) config.header["hash-algorithm"] = "SHA256";
            if( config.headers["realm"] === undefined )  config.header["realm"] = "realm";
            if( config.headers["expires_in"] === undefined ) config.header["expires_in"]  = 3600;
            if( config.headers["token_type"] === undefined ) config.header["token_type"]  = "bearer";
            if( config.headers["access_token"] === undefined )  config.header["access_token"]  = $cookieStore.get('X-XSRF-TOKEN') || ""; 
            if( config.headers["client-public-key"] === undefined )  config.header["client-public-key"]  = $cookieStore.get('client-public-key') || ""; 
            if( config.headers["server-public-key"] === undefined )  config.header["server-public-key"]  = $cookieStore.get('server-public-key') || ""; 

            return config;
        },

        /*
         * Save authorization header, if those was send back,
         */
        response : function( res ){
            console.log( res.header );
            return res;
        },

        /*
         * Handle authentication errors in response object
         */
        responseError: function(response) {
            console.log("error not auth");
            if(response.status === 401 || response.status === 403) {
                window.document.url ='#/login';
            }
            return $q.reject(response);
        },

        /*
         * Handle authentication errors in request object
         */
        requestError: function(rejection) {
            console.log(rejection);
            if (canRecover(rejection)) {
                return responseOrNewPromise
            }
            return $q.reject(rejection);
        }

        }
    });


  })
  .run( function( $rootScope ){
      $rootScope.$on('$locationChangeStart', function(scope, next, current ){
        console.log('route changed');
      });

  });
