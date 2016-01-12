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
  .filter('filter', function () {
    return function (input) {
      return 'filter filter: ' + input;
    };
  });
