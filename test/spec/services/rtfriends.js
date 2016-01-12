'use strict';

describe('Service: rtfriends', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var rtfriends;
  beforeEach(inject(function (_rtfriends_) {
    rtfriends = _rtfriends_;
  }));

  it('should do something', function () {
    expect(!!rtfriends).toBe(true);
  });

});
