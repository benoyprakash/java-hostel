(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('PaymentsDetailController', PaymentsDetailController);

    PaymentsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payments',
        '$localStorage'];

    function PaymentsDetailController($scope, $rootScope, $stateParams, previousState, entity, Payments, $localStorage) {
        var vm = this;

        vm.payments = entity;
        vm.previousState = previousState.name;
        $scope.clientData = $localStorage.data.clientData;
        if(vm.payments != null){
            if(vm.payments.building == $scope.clientData.building.id){
                vm.payments.buildingName = $scope.clientData.building.name;
            }
        }

        var unsubscribe = $rootScope.$on('hostelApp:paymentsUpdate', function(event, result) {
            vm.payments = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
