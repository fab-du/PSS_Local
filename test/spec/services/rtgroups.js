'use strict';

describe('Service: rtgroups', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var rtgroups;
  beforeEach(inject(function (_rtgroups_) {
    rtgroups = _rtgroups_;
  }));

  it('should do something', function () {
    expect(!!rtgroups).toBe(true);
  });

});
