(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomAllocationDialogController', RoomAllocationDialogController);

    RoomAllocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RoomAllocation'];

    function RoomAllocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RoomAllocation) {
        var vm = this;

        vm.roomAllocation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.roomAllocation.id !== null) {
                RoomAllocation.update(vm.roomAllocation, onSaveSuccess, onSaveError);
            } else {
                RoomAllocation.save(vm.roomAllocation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hostelApp:roomAllocationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fromDate = false;
        vm.datePickerOpenStatus.toDate = false;
        vm.datePickerOpenStatus.updatedDateTime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
