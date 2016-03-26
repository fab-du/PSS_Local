'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.RouteFriends
 * @description
 * # REST - Resource : Friends
 */
angular.module('cryptClientApp')
.factory('RouteFriends', function ( Auth ) {

var routes = 
{
    url : '/api/:currentUserId/friends/:friendId/:suffix/:param',
    defaults : { friendId : '@friendId', currentUserId: '@currentUserId', suffix : '' },
    actions : 
    {
        find         : { method : 'GET',  isArray : true },
        findOne      : { method : 'GET' },
        addFriend    : { method : 'POST'},
        revoke       : { method : 'DELETE', params : {  suffix  : 'revoke' }},
        addToGroup   : { method : 'PUT', params : {  suffix  : 'addToGroup', param : '@param' }}
    }

};

return { routes : routes };
});
