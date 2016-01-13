'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.RouteFriends
 * @description
 * # REST - Resource : Friends
 */
angular.module('cryptClientApp')
.factory('RouteFriends', function () {

var routes = 
{
    url : '/api/friends/:friendId/:suffix',
    defaults : { friendId : '@friendId', suffix : '' },
    actions : 
    {
        find         : { method : 'GET' },
        findOne      : { method : 'GET' },
        addFriend    : { method : 'POST' },
        validate     : { method : 'POST', params : { suffix  : 'validate' }},
        revokeFriend : { method : 'POST', params : { suffix  : 'revoke' }},
        addToGroup   : { method : 'POST', params : { suffix  : 'addToGroup', group : '@groupId' }}
    }

};

return { routes : routes };
});
