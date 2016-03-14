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
      var obj = null;
      angular.forEach( input, function( v ){
        if( v[ keyname ].toString().trim() === value.toString().trim() ){
            obj = v;
        }
      });

     return obj;
  }
})
.filter('isGv', function( Auth ){
    return function( group ){
        return Auth.isGv( group );
    }
})
.filter('isGroupMember', function( Auth ){
    return function( group ){
        var ret = Auth.isGroupMember( group )
        return Auth.isGroupMember( group );
    }
});

