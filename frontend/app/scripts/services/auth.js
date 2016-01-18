'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.Auth
 * @description
 * # Auth
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Auth', function ($http, $state, $q, AUTH_EVENTS, Storage, HEADERS,  $rootScope ) {
var api = {};

   function findHeader( header, headers ){
       var ret = false;
       var q = $q.defer();
       var value;
       
      angular.forEach( headers , function( _v, _k ){
          if ( !angular.isUndefined( _k )  &&  _k !== null && header === _k  ){
                  value = _v; 
                  ret = true;
          }else{}
      });

      if ( ret ){
          q.resolve( { name :  header, value :  value } );
      }
      else{
          q.reject( { err : 'header error \t' + header } );
      }
      return q.promise;
   }

api.login = function( user ){
    var q = $q.defer();
    $http.post('/session/login', user).success(function( response, status , headers ){
        var authHeaders = headers();
        Storage.set("currentUser", user.email );

        angular.forEach( HEADERS , function( _v ){
               findHeader( _v.name, authHeaders).then( 
                function( header ){
                    Storage.set( header.name , header.value  );
               },function(err){
                    $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
                    q.reject( err );
               });
        });
        

        $rootScope.$broadcast( AUTH_EVENTS.loginSuccess );
        q.resolve( status  );
    }).error( function(err){
        $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        q.reject( err );
    });

    return q.promise;
};



api.isLoggedIn =  function() {
    var currentUser = Storage.get("currentUser");
    var ret = currentUser !== null;
    return  ret;
};


api.logout =  function(){
    Storage.remove();
};


api.register = function( credentials ){
    var q = $q.defer();

    console.log('come here');

    $http.post('/session/register', credentials ).success( function( res ){
        $rootScope.$broadcast( AUTH_EVENTS.registrationSuccess );
        q.resolve( res );
    }).error( function( err ){
        $rootScope.$broadcast( AUTH_EVENTS.registrationFailed );
        q.reject( err );
    });
    return q.promise;
};


return api;

});
