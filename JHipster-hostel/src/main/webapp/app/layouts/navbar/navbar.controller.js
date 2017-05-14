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

        vm.isNavbarCollapsed = true;
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

        vm.clients = null;
        vm.selectedClient = null;
        vm.locations = null;
        vm.selectedLocation = null;
        vm.buildings = null;
        vm.selectedBuilding = null;

        $scope.clientData = {};
        $scope.clientData.client = null;
        $scope.clientData.location = null;
        $scope.clientData.building = null;

        $localStorage.data = {};
        $localStorage.data.clientData = null;

        getClients();

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

                function getClients(){
                    $log.info('fetching all clients');
                    Client.query().$promise.then(function(clients){
                        vm.clients = clients;
                    });
                }
                $scope.onClientChangeEvent = function (){
                    $log.info('onClientChangeEvent');
                    if($scope.selectedClient){
                        $scope.clientData.client = $scope.selectedClient;
                        Location.query({
                                clients: 'clients',
                                id: $scope.selectedClient.id
                            })
                            .$promise.then(function(locations){
                                vm.locations = locations;
                            });
                    }

                }

                $scope.onLocationChangeEvent = function (){
                    $log.info('onLocationChangeEvent');
                    if($scope.selectedLocation){
                    $scope.clientData.location = $scope.selectedLocation;
                        Building.query({
                            })
                            .$promise.then(function(buildings){
                                vm.buildings = buildings;
                            });
                    }
                }

                $scope.onBuildingChangeEvent = function (){
                    $log.info('onBuildingChangeEvent');
                    if($scope.selectedBuilding){
                        $scope.clientData.building = $scope.selectedBuilding;

                        $localStorage.data.clientData = $scope.clientData;
                    }
                }
    }
})();
