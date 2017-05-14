(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('BuildingDialogController', BuildingDialogController);

    BuildingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Building',
            '$localStorage'];

    function BuildingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Building,
            $localStorage) {
        var vm = this;

        vm.building = entity;
        vm.building.location = $localStorage.data.clientData.location;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.building.id !== null) {
                vm.building.location = vm.building.location.id;
                Building.update(vm.building, onSaveSuccess, onSaveError);
            } else {
                vm.building.location = vm.building.location.id;
                Building.save(vm.building, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hostelApp:buildingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
