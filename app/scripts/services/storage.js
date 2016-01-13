'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.storage
 * @description
 * # storage
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Storage', function ( $cookies, $filter ) {

      /**
      *        path 
      *        {string} - The cookie will be available only for this 
      *                          path and its sub-paths. By default, this would be 
      *                          the URL that appears in your base tag.
      *
      *        domain 
      *        {string} - The cookie will be available only for this domain 
      *                            and its sub-domains. For obvious security reasons the 
      *                            user agent will not accept the cookie if the current 
      *                            domain is not a sub domain or equals to the requested domain.
      *
      *        expires - 
      *        {string|Date} - String of the form "Wdy, DD Mon YYYY HH:MM:SS GMT" or 
      *                a Date object indicating the exact date/time this cookie will expire.
      *        
      *        For our use case, this options will be set to 5 minutes every time
      *        we receive an response and/oder make the request.
      * 
      *
      *        secure - 
      *        {boolean} - The cookie will be available only in secured connection.
      *
      */
      var options = {
          secure : false
      };


      function refresh_token(){
        var d = new Date( new Date().getTime() + 600000);
        var n = d.toUTCString().toString();
        options.expires = n;
        $cookies.put( "refresh", "",  options );
      }

      /*
       *function init( ){
       *    var foo = { name : "toto"};
       *    var bar = "bar";
       *    var mar = [ "lksjfaksdf", "lksajflkjasf" ];
       *  console.log( $filter('json')( foo ) );
       *  console.log( $filter('json')( bar ) );
       *  console.log( $filter('json')( mar ) );
       *}
       */


      var get = function ( key ){
          var ret = $cookies.get(key);
          if ( ret === undefined) {
            return null;
          }
           return JSON.parse ( ret );
      };

      var set = function( key, value ){
          refresh_token();

          if ( !angular.isString( key ) ){
              return false; 
          }

          /*
           * Json object have to be stringify since *no* storage actually
           * saving json object
           ***/
          if ( angular.isObject( value ) ){
                $cookies.put( key, $filter('json')( value )  );
                return true;
          }

          if( angular.isString( value ) ){
              $cookies.put( key, value );
              return true;
          }
      };

      var getAll = function(){
          return $cookies.getAll();
      };

      var putAll = function( values ){
          //refresh_token();
          angular.forEach( values , function(value, key ){
              set( key, value, options );
          });
      };

      var remove =  function(){
          var cookies = getAll();

          angular.forEach( cookies , function( value, key ){
              $cookies.remove( key );
          });
      };


      var sessionStorage = {
        get    : get,
        set    : set,
        getAll : getAll,
        putAll : putAll,
        remove : remove
      };


      return sessionStorage;
  });

