
angular.module('cryptClientApp')
.constant('ERRORS_EVENTS', {
    notFound : 'Not-Found:'
})

.constant('SUCCESS_EVENTS', {
    created : "Created:",  // 201 
    deleted : "Deleted:",
    nothingToDelete : "nothing to Delete"
});
