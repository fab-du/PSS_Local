'use strict';

describe('Controller: FriendsCtrl', function () {

  // load the controller's module
  beforeEach(module('cryptClientApp'));

  var FriendsCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    FriendsCtrl = $controller('FriendsCtrl', {
      $scope: scope
      // place here mocked dependencies
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(FriendsCtrl.awesomeThings.length).toBe(3);
  });
});
