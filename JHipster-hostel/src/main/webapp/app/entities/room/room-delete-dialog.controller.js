(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomDeleteController',RoomDeleteController);

    RoomDeleteController.$inject = ['$uibModalInstance', 'entity', 'Room'];

    function RoomDeleteController($uibModalInstance, entity, Room) {
        var vm = this;

        vm.room = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Room.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
