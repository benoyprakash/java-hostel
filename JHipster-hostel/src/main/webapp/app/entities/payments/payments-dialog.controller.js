(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('PaymentsDialogController', PaymentsDialogController);

    PaymentsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payments',
        '$localStorage', 'Room', 'User'];

    function PaymentsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Payments,
        $localStorage, Room, User) {

        var vm = this;
        vm.payments = entity;
        $scope.clientData = $localStorage.data.clientData;

        vm.payments.buildingName = $scope.clientData.building.name;
        vm.payments.building = $scope.clientData.building.id;
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
            if (vm.payments.id !== null) {
                Payments.update(vm.payments, onSaveSuccess, onSaveError);
            } else {
                Payments.save(vm.payments, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('hostelApp:paymentsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfJoin = false;
        vm.datePickerOpenStatus.paymentDate = false;
        vm.datePickerOpenStatus.paymentFrom = false;
        vm.datePickerOpenStatus.paymentTo = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
