(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomAllocationDeleteController',RoomAllocationDeleteController);

    RoomAllocationDeleteController.$inject = ['$uibModalInstance', 'entity', 'RoomAllocation'];

    function RoomAllocationDeleteController($uibModalInstance, entity, RoomAllocation) {
        var vm = this;

        vm.roomAllocation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RoomAllocation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
