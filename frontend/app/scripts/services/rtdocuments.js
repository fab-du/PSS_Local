'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.rtdocuments
 * @description
 * # rtdocuments
 * Factory in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('RouteDocuments', function () {

var routes = 
{
    url : '/api/groups/:group/documents/:verb',
    defaults : { verb : '@documentId' },
    actions : 
    {
        find          : { method : 'GET', isArray : true  },
        findOne       : { method : 'GET' },
        changeOwner   : { method : 'POST', params : { verb  : 'changeOwner' }},
        shareDocument : { method : 'POST', params : { verb  : 'shareDocument', group : '@group' }},
        addDocument   : { method : 'POST', params : { group : 'group' }, transformRequest : angular.entity, headers : { 'Content-Type' : undefined } }
    }

};

return { routes : routes };
});
