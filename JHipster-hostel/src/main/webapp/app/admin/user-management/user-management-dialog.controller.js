(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User', '$localStorage'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User, $localStorage) {
        var vm = this;

        vm.authorities = ['ROLE_MANAGER', 'ROLE_STAFF', 'ROLE_CUSTOMER'];
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;
        vm.clientData = $localStorage.data.clientData;
        vm.user.client = vm.clientData.client.id;
        vm.user.clientName = vm.clientData.client.clientName;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                vm.user.client = vm.clientData.client.id;
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                vm.user.langKey = 'en';
                vm.user.client = vm.clientData.client.id;
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
