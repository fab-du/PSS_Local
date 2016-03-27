
angular.module('cryptClientApp')
.factory('Documents', function($q, $http){

var download = function( document, groupId , handler ){
    var url = toDownloadUrl( document, groupname );
    _download( url, handler );
};

function _download( url, handler ){
    $http.get( url, { headers : { 'Content-Type' : 'application/json; charset=utf-8' }, responseType : 'arraybuffer' })
    .then( function( response ){
        console.log( response );
        handler( response );
    });
}

function toDownloadUrl ( document, groupId ){
    var docId   = doc.id;
    var docName = doc.name;
    var url     = "/api/groups/" + groupId + "/documents/" + docId + "/download/" + docName  ;
    return url;
}

var upload = function( url ){

};

return {
    download      : download,
    upload        : upload
};

})
