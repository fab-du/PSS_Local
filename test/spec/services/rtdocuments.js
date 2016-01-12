'use strict';

describe('Service: rtdocuments', function () {

  // load the service's module
  beforeEach(module('cryptClientApp'));

  // instantiate service
  var rtdocuments;
  beforeEach(inject(function (_rtdocuments_) {
    rtdocuments = _rtdocuments_;
  }));

  it('should do something', function () {
    expect(!!rtdocuments).toBe(true);
  });

});
