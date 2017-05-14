(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('LocationDialogController', LocationDialogController);

    LocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity'
            , 'Location', '$localStorage'];

    function LocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Location,
            $localStorage) {
        var vm = this;

        vm.location = entity;
        vm.location.client = $localStorage.data.clientData.client;

        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.location.id !== null) {
                vm.location.client = vm.location.client.id;
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                vm.location.client = vm.location.client.id;
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hostelApp:locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
