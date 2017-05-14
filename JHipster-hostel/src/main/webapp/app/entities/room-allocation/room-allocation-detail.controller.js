(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomAllocationDetailController', RoomAllocationDetailController);

    RoomAllocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RoomAllocation'];

    function RoomAllocationDetailController($scope, $rootScope, $stateParams, previousState, entity, RoomAllocation) {
        var vm = this;

        vm.roomAllocation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('hostelApp:roomAllocationUpdate', function(event, result) {
            vm.roomAllocation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
