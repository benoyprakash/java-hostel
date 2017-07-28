(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('BuildingDetailController', BuildingDetailController);

    BuildingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Building', '$localStorage'];

    function BuildingDetailController($scope, $rootScope, $stateParams, previousState, entity, Building, $localStorage) {
        var vm = this;

        vm.building = entity;
        $scope.clientData = $localStorage.data.clientData;
        if(vm.building != null){
            if(vm.building.location === $scope.clientData.location.id){
                vm.building.locationName = $scope.clientData.location.locationName;
            }
        }

        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hostelApp:buildingUpdate', function(event, result) {
            vm.building = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
