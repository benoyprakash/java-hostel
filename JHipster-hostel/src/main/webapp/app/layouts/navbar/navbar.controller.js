(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$log', '$localStorage',
                                 'Client', 'Location', 'Building'];

    function NavbarController ($scope, $state, Auth, Principal, ProfileService, LoginService, $log, $localStorage,
                                  Client, Location, Building) {
        var vm = this;
        vm.editEnabled = true;
        vm.editButtomLabel = "Save";
        vm.isNavbarCollapsed = true;
        vm.isClientSelectionNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;


        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $localStorage.data = {};
            $scope.clientData = {};
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }
    }
})();
