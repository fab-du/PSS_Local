'use strict';

describe('Service: AuthEvents', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var AuthEvents;
  beforeEach(inject(function (_AuthEvents_) {
    AuthEvents = _AuthEvents_;
  }));

  it('should do something', function () {
    expect(!!AuthEvents).toBe(true);
  });

});
