(function() {
    'use strict';

    angular
        .module('hostelApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];
    // public registration is disabled.
    // by setting the ROLE as ADMIN, so only admin can access this
    function stateConfig($stateProvider) {
        $stateProvider.state('register', {
            parent: 'account',
            url: '/register',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/register/register.html',
                    controller: 'RegisterController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
