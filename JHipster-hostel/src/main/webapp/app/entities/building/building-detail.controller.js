(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('BuildingDetailController', BuildingDetailController);

    BuildingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Building'];

    function BuildingDetailController($scope, $rootScope, $stateParams, previousState, entity, Building) {
        var vm = this;

        vm.building = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hostelApp:buildingUpdate', function(event, result) {
            vm.building = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
