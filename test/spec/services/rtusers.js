'use strict';

describe('Service: rtusers', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var rtusers;
  beforeEach(inject(function (_rtusers_) {
    rtusers = _rtusers_;
  }));

  it('should do something', function () {
    expect(!!rtusers).toBe(true);
  });

});
