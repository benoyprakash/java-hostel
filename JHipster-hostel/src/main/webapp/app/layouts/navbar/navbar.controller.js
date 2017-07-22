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

        getClients();

        if($localStorage.data && $localStorage.data.clientData != null){
            vm.selectedClient = $localStorage.data.clientData.client;
            vm.selectedLocation = $localStorage.data.clientData.location;
            vm.selectedBuilding = $localStorage.data.clientData.building;
        } else {
            $localStorage.data = {};
        }

        $scope.toggleEdit = function(){
            vm.editEnabled = !vm.editEnabled;
            console.log(vm.editEnabled);
            if(vm.editEnabled == true){
                vm.editButtomLabel = 'Save';
            } else {
                vm.editButtomLabel = 'Edit';
            }

        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $localStorage.data = {};
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
                        $localStorage.data.clientData = $scope.clientData;
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
                    $localStorage.data.clientData = $scope.clientData;
                        Building.query({
                            locations: 'locations',
                                id: $scope.selectedLocation.id
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
