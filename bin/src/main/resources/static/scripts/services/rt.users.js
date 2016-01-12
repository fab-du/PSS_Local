'use strict';

/**
 * @ngdoc service
 * @name cryptoClientApp.Rt.Users
 * @description
 * # Rt.Users
 * Factory in the cryptoClientApp.
 */
angular.module('cryptoClientApp')
  .factory('Rest', function ( $resource ) {

      var api = {};

         var users= {
                    url           : '/api/users',
                    actions: { 
                        get        : { url :'/api/users' ,method : 'GET', isArray : true },
                        getOne     : { url :'api/users/:userId' ,method : 'GET', params: { userId : '@userId' }},
                        validate   : { url :'api/users/:userId/validate' ,method : 'POST', params : {crypt: true}},
                        addFriend  : { url :'api/users/:userId/addFriend' ,method : 'POST',  params : {crypt: true}},
                        revoke     : { url :'api/users/:userId/revoke' ,method : 'POST',   params : {crypt: true}},
                        addToGroup : { url :'api/users/:userId/addToGroup' ,method : 'POST',  params : {crypt: true}},
                        getFriends : { url :'api/users/:userId/friends', method : 'GET' },
                        getOneFriend : { url :'/api/users/:userId/friends', method : 'GET' },
                        validate2   : { url :'/api/users/:userId/friends/:friendId/validate' ,method : 'POST', params : {crypt: true}},
                        addFriend2  : { url :'/api/users/:userId/friends/:friendId/addFriend' ,method : 'POST',  params : {crypt: true}},
                        revoke2     : { url :'/api/users/:userId/friends/:friendId/revoke' ,method : 'POST',   params : {crypt: true}},
                        addToGroup2 : { url :'/api/users/:userId/friends/:friendId/addToGroup' ,method : 'POST',  params : {crypt: true}},
                        add    : { url :'/api/users' ,method : 'POST', params : {crypt : true}  },
                    }};

        var friends = {
            url : '/friends',
            actions : {
                        get        : { url :'/friends' ,method : 'GET', isArray : true },
                        getOne     : { url :'/friends/:userId' ,method : 'GET', params: { userId : '@userId' }},
                        validate   : { url :'/friends/:userId/validate' ,method : 'POST', params : {crypt: true}},
                        addFriend  : { url :'/friends/:userId/addFriend' ,method : 'POST',  params : {crypt: true}},
                        revoke     : { url :'/friends/:userId/revoke' ,method : 'POST',   params : {crypt: true}},
                        addToGroup : { url :'/friends/:userId/addToGroup' ,method : 'POST',  params : {crypt: true}},
                        getFriends : { url :'/friends/:userId/friends', method : 'GET' },
                        getOneFriend : { url :'/friends/:userId/friends', method : 'GET' },
                        validate2   : { url :'/friends/:userId/friends/:friendId/validate' ,method : 'POST', params : {crypt: true}},
                        addFriend2  : { url :'/friends/:userId/friends/:friendId/addFriend' ,method : 'POST',  params : {crypt: true}},
                        revoke2     : { url :'/friends/:userId/friends/:friendId/revoke' ,method : 'POST',   params : {crypt: true}},
                        addToGroup2 : { url :'/friends/:userId/friends/:friendId/addToGroup' ,method : 'POST',  params : {crypt: true}},
                        add    : { url :'/friends' ,method : 'POST', params : {crypt : true}  },
            }};

          var groups = {
            url : '/api/groups',
            actions: {
                get    : { url :'/api/groups' ,method : 'GET', isArray : true },
                getOne : { url: '/api/groups/:groupId', method : 'GET', params: { groupId : '@groupId'} },
                save    : { url :'/api/groups' ,method : 'POST', isArray : true },

                getDocuments : { url:  '/api/groups/:groupId/documents', method : 'GET', params: { groupId : '@groupId'} },
                getDocumentId : { url: '/api/groups/:groupId/documents/:documentId', method : 'GET', params: { groupId : '@groupId', documentId : '@documentId'} },
                changeDocumentOwner : { url: '/api/groups/:groupId/documents/:documentId/changeOwner', method : 'POST', params: { groupId : '@groupId' , documentId : '@documentId'} },
                shareDocument : { url: '/api/groups/:groupId/documents/:documentId/shareDocument', method : 'POST', params: { groupId : '@groupId' , documentId : '@documentId' } },
                addDocument : { url :  '/api/groups/:groupId/addDocument', method : 'POST', params:{ crypt : true}},
                addDocument : { url :  '/api/groups/:groupId/documents/addDocument', method : 'POST', params:{ crypt : true}},

                getUsers : { url : '/api/groups/:groupId/users', method : 'GET'},
                addUser : { url :  '/api/groups/:groupId/users/:userId/removeUser', method : 'POST', params:{ crypt : true}},
            }};

         
         var documents  = {
             url : '/documents',
             actions: {
                get    : { url :'/documents' ,method : 'GET', isArray : true },
                getOne : { url: '/documents/:documentId', method : 'GET', params: { documentId : '@documentId'} },
                changeOwner : { url : '/documents/:documentId/changeOwner', method : 'POST',params: {documentId: '@documentId'}},
                shareDocument : { url : '/documents/:documentId/shareDocument', method : 'POST',params: {documentId: '@documentId'}},
                addDocument : { url : '/documents/addDocument', method : 'POST',params: {crypt : true }}
             }};


        /*
         *  Use in case the Client provide additional configurations details.
         *
         * supported configs :
         * -> url_base : for instance http://localhost:3000, concat with '/user'  
         * -> withCredentials : true/false, wheter the credentials is used or not
         *
         * */
        function rest_config( resource, opts ) {//{{{
            var ret = false;
            var url_base = null;
            var withCredentials = false;


            if ( angular.isObject( opts ) ){
                angular.forEach( opts, function( value , key ){
                    if( key  === 'url_base'){
                        url_base = value;
                    }
                    else if( key === 'withCredentials' ){
                        withCredentials = value;
                    }
                    else {}

                })
            }

            if( withCredentials === true ){
                angular.forEach( users.actions , function( value, key){
                     resource.actions[key].withCredentials =  true;
                });
            }

        }//}}}

        api.User = function( opts ){
            //rest_config( users, opts );
            return $resource( users.url , {}, users.actions );
        }

        api.Group = function( opts ){
            //rest_config( groups, opts );
            return $resource( groups.url, {}, groups.actions );
        };

        api.Document = function( opts ){
            //rest_config( documents, opts );
            return $resource(documents.url, {}, documents.actions );
        };

        api.Friend = function( opts ){
            rest_config( friends, opts );
            return $resource( friends.url, {}, friends.actions );
        }


        return api;
});
