'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.rtusers
 * @description
 * # rtusers
 * Factory in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('RouteUsers', function () {
    
var routes = 
{
    url : '/api/users/:userId/:suffix',
    defaults : { userId : '@userId', suffix : '' },
    actions : 
    {
        find         : { method : 'GET'  },
        findOne      : { method : 'GET'  },
        create       : { method : 'POST' },
        validate     : { method : 'POST', params : { suffix : 'validate' }},
        addFriend    : { method : 'POST', params : { suffix : 'addFriend' }},
        revoke       : { method : 'POST', params : { suffix : 'revoke' }},
        addToGroup   : { method : 'POST', params : { suffix : 'addToGroup' }},
        friends      : { method : 'GET',  params : { suffix : 'friends' }},
        groups       : { method : 'GET',  params : { suffix : 'groups' }},
    }

};

return { routes : routes };

});
