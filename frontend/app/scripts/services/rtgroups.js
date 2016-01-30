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
    url : '/api/groups/:prefix/:groupId/:suffix',
    defaults : { groupId : '@groupId', suffix : '', prefix : '' },
    actions : 
    {
        find         : { method : 'GET', isArray : true  },
        findOne      : { method : 'GET'  },
        mygroups     : { method : 'GET', params : { prefix : 'users', suffix : '@suffix' }, isArray : true  },
        create       : { method : 'POST' },
        addFriend    : { method : 'POST' },
        documents    : { method : 'GET',  params : { suffix : 'documents' }},
        users        : { method : 'GET',  params : { suffix : 'users' }}
    }, 
    opts : {}
};

return { routes : routes };

});
