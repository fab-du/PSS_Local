'use strict';

describe('Service: interceptor', function () {

  // instantiate service
  var interceptor,
    init = function () {
      inject(function (_interceptor_) {
        interceptor = _interceptor_;
      });
    };

  // load the service's module
  beforeEach(module('cryptClientApp'));

  it('should do something', function () {
    init();

    expect(!!interceptor).toBe(true);
  });

  it('should be configurable', function () {
    module(function (interceptorProvider) {
      interceptorProvider.setSalutation('Lorem ipsum');
    });

    init();

    expect(interceptor.greet()).toEqual('Lorem ipsum');
  });

});
