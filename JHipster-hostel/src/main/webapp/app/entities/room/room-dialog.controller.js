(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomDialogController', RoomDialogController);

    RoomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Room',
            '$localStorage'];

    function RoomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Room, $localStorage) {
        var vm = this;

        vm.room = entity;
        vm.room.building = $localStorage.data.clientData.building;

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
            if (vm.room.id !== null) {
                vm.room.building = vm.room.building.id;
                Room.update(vm.room, onSaveSuccess, onSaveError);
            } else {
                vm.room.building = vm.room.building.id;
                Room.save(vm.room, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hostelApp:roomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
