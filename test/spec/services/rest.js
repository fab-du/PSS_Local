'use strict';

describe('Service: rest', function () {

    var $http , $scope, Rest, log;

  // load the service's module
  beforeEach(module('cryptClientApp'));

  beforeEach(inject(function ($injector) {
      $http = $injector.get("$httpBackend");
      $scope = $injector.get("$rootScope");
      Rest = $injector.get("Rest");
      log = $injector.get('$log');
  }));

  it('should do something', function () {
      log.log( Rest  );
  });

});
