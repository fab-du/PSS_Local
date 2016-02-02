'use strict';

/**
 * @ngdoc filter
 * @name cryptClientApp.filter:filter
 * @function
 * @description
 * # filter
 * Filter in the cryptClientApp.
 */
angular.module('cryptClientApp')
.filter('getById', function () {
  return function(input, keyname, value ) {
      var ret = false;
      var obj;
      angular.forEach( input, function( v ){
          console.log( v[ keyname ] )
          
        if( v[ keyname ].toString().trim() === value.toString().trim() ){
            console.log( v[ keyname ] )
            ret = true;
            obj = v;
            return;
        }
      })

      if( ret ){
          return obj;
      }
      else{
          return null;
      }
  }
});

