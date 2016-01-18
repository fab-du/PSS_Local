'use strict';

/**
 * @ngdoc service
 * @name cryptClientApp.AuthEvents
 * @description
 * # AuthEvents
 * Constant in the cryptClientApp.
 */
angular.module('cryptClientApp')
.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notFound: 'resource-not-found',
    registrationFailed: 'registration-failed',
    registrationSuccess: 'registration-success',
    notAuthorized: 'auth-not-authorized'
});
