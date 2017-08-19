(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('ClientSelectionController', ClientSelectionController);

    ClientSelectionController.$inject = ['$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', '$log', '$localStorage',
                                 'Client', 'Location', 'Building'];

    function ClientSelectionController ($scope, $state, Auth, Principal, ProfileService, LoginService, $log, $localStorage,
                                  Client, Location, Building) {
        var vm = this;
        vm.editEnabled = true;
        $scope.clientData = {};
        vm.isAuthenticated = Principal.isAuthenticated;

        vm.isClientSelectionNavbarCollapsed = true;
        vm.toggleClientSelectionNavbar = toggleClientSelectionNavbar;

        if($localStorage.data && $localStorage.data.clientData != null){
            vm.editEnabled = false;
            vm.editButtomLabel = "Edit";

            vm.selectedClient = $localStorage.data.clientData.client;
            vm.selectedLocation = $localStorage.data.clientData.location;
            vm.selectedBuilding = $localStorage.data.clientData.building;

            vm.clientName = vm.selectedClient.clientName;
            vm.locationName = vm.selectedLocation.locationName;
            vm.buildingName = vm.selectedBuilding.name;
        } else {
            vm.editEnabled = true;
            vm.editButtomLabel = "Save";
            resetClientSelection();
            $localStorage.data = {};

            // get all clients
            getClients();
        }



        $scope.toggleEdit = function(){
            vm.editEnabled = !vm.editEnabled;
            if(vm.editEnabled == true){
                vm.editButtomLabel = 'Save';
                getClients();
            } else {
                vm.editButtomLabel = 'Edit';
                $state.go('home');
            }

        }
        function toggleClientSelectionNavbar() {
            vm.isClientSelectionNavbarCollapsed = !vm.isClientSelectionNavbarCollapsed;
        }

        function resetClientSelection(){
            vm.clients = null;
            vm.selectedClient = null;
            vm.locations = null;
            vm.selectedLocation = null;
            vm.buildings = null;
            vm.selectedBuilding = null;
            $scope.clientData.client = null;
            $scope.clientData.location = null;
            $scope.clientData.building = null;
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
                $state.go('home');
            }
        }
    }
})();
