'use strict';

describe('Service: Inter', function () {

  // instantiate service
  var Inter,
    init = function () {
      inject(function (_Inter_) {
        Inter = _Inter_;
      });
    };

  // load the service's module
  beforeEach(module('cryptClientApp'));

  it('should do something', function () {
    init();

    expect(!!Inter).toBe(true);
  });

  it('should be configurable', function () {
    module(function (InterProvider) {
      InterProvider.setSalutation('Lorem ipsum');
    });

    init();

    expect(Inter.greet()).toEqual('Lorem ipsum');
  });

});
