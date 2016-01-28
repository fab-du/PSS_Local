'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.RouteFriends
 * @description
 * # REST - Resource : Friends
 */
angular.module('cryptClientApp')
.factory('RouteFriends', function ( Auth ) {

var currentUser = Auth.getCurrentUser();
var currentUserId = currentUser.currentUserId; 

var routes = 
{
    url : '/api/:currentUserId/friends/:friendId/:suffix',
    defaults : { friendId : '@friendId', suffix : '' },
    actions : 
    {
        find         : { method : 'GET' , params : { currentUserId : currentUserId  } },
        findOne      : { method : 'GET' },
        addFriend    : { method : 'POST' },
        revokeFriend : { method : 'POST', params : { currentUserId : currentUserId , suffix  : 'revoke' }},
        addToGroup   : { method : 'POST', params : { currentUserId : currentUserId , suffix  : 'addToGroup', group : '@groupId' }}
    }

};

return { routes : routes };
});
