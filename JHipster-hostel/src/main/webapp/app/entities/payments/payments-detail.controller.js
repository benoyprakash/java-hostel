(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('PaymentsDetailController', PaymentsDetailController);

    PaymentsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payments'];

    function PaymentsDetailController($scope, $rootScope, $stateParams, previousState, entity, Payments) {
        var vm = this;

        vm.payments = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hostelApp:paymentsUpdate', function(event, result) {
            vm.payments = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
