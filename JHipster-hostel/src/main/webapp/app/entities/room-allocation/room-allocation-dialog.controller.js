(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomAllocationDialogController', RoomAllocationDialogController);

    RoomAllocationDialogController.$inject = ['$timeout', '$scope', '$localStorage', '$stateParams', '$uibModalInstance', 'entity', 'RoomAllocation', 'Room', 'User'];

    function RoomAllocationDialogController ($timeout, $scope, $localStorage, $stateParams, $uibModalInstance, entity, RoomAllocation, Room, User) {
        var vm = this;
        $scope.clientData = $localStorage.data.clientData;
        vm.roomAllocation = entity;
        //vm.roomAllocation.roomId = null;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        Room.query({
            buildings : 'buildings',
            id:$scope.clientData.building.id
        }).$promise.then(function(rooms){
            vm.rooms = rooms;
        });

        User.query({
            clients : 'customer',
            idx:$scope.clientData.client.id
        }).$promise.then(function(customers){
            vm.users = customers;
        });

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
                vm.roomAllocation.buildingId = $scope.clientData.building.id;
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
