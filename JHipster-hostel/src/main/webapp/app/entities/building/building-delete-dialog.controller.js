(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('BuildingDeleteController',BuildingDeleteController);

    BuildingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Building'];

    function BuildingDeleteController($uibModalInstance, entity, Building) {
        var vm = this;

        vm.building = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Building.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
