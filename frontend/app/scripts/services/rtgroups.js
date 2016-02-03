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
    url : '/api/groups/:groupId/:suffix1/:suffix2',
    defaults : { groupId : '@groupId', suffix1 : '', suffix2 : '' },
    actions : 
    {
        find         : { method : 'GET', isArray : true  },
        findOne      : { method : 'GET'  },
        mygroups     : { method : 'GET', params : { groupId : 'users', suffix1 : '@suffix1' }, isArray : true  },
        create       : { method : 'POST' },
        addFriend    : { method : 'POST' },
        documents    : { method : 'GET',  params : {  suffix1 : 'documents' }, isArray: true},
        users        : { method : 'GET',  params : {  suffix1 : 'users' } , isArray : true}
    }, 
    opts : {}
};

return { routes : routes };

});
