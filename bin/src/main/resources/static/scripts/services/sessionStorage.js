'use strict';

angular.module('cryptoClientApp')
  .factory('sessionStorage', function ( $cookies ) {

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
  
        options.expires = n
        $cookies.put( "refresh", "",  options )
      }

      function init( ){
      }


      var get = function ( key ){
          return $cookies.getObject(key);
      };

      var set = function( key, value ){
          refresh_token();
          $cookies.put( key, value, options );
      };

      var getAll = function(){
          return $cookies.getAll();
      };

      var putAll = function( values ){
          //refresh_token();
          angular.forEach( values , function(value, key ){
              console.log( key );
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
