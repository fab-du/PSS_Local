'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.rest
 * @description
 * # rest
 * Service in the cryptClientApp.
 */
angular.module('cryptClientApp')
.factory('Rest', function ( $resource, RouteUsers , RouteDocuments, RouteFriends, RouteGroups ) {

    var api = {};
    var routes = {
        'User'     : RouteUsers.routes,
        'Group'    : RouteGroups.routes,
        'Document' : RouteDocuments.routes,
        'Friend'   : RouteFriends.routes
    };

    /*
    *  Use in case the Client provide additional configurations details.
    * supported configs :
    * -> url_base : for instance http://localhost:3000, concat with **url**  
    * -> withCredentials : true/false, wheter the credentials is used or not
    *
    * @ref: api/ngResource.$resource for more details 
    *
    * */
    function rest_config( routes, opts ) {
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
                    routes.actions[key].withCredentials =  true;
            });
        }

    }

    function configs(){
        agular.forEach(  routes , function( v, k ){
           api[ k ] = $resource( v );
        });
    }


    configs();
    return api;
});
