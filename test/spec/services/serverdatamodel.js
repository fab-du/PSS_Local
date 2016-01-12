'use strict';

describe('Service: ServerDataModel', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var ServerDataModel;
  beforeEach(inject(function (_ServerDataModel_) {
    ServerDataModel = _ServerDataModel_;
  }));

  it('should do something', function () {
    expect(!!ServerDataModel).toBe(true);
  });

});
