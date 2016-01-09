'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.rtgroups
 * @description
 * # rtgroups
 * Factory in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('RouteGroups', function () {

var routes = 
{
    url : '/api/groups/:groupId/:suffix',
    defaults : { groupId : '@groupId', suffix : '' },
    actions : 
    {
        find         : { method : 'GET'  },
        findOne      : { method : 'GET'  },
        create       : { method : 'POST' },
        addFriend    : { method : 'POST' },
        documents    : { method : 'GET',  params : { suffix : 'documents' }},
        users        : { method : 'GET',  params : { suffix : 'users' }}
    }

};

return { routes : routes };

});
