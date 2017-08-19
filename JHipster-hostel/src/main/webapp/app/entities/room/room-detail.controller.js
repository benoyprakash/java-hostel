(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomDetailController', RoomDetailController);

    RoomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Room',
    '$localStorage'];

    function RoomDetailController($scope, $rootScope, $stateParams, previousState, entity, Room, $localStorage) {
        var vm = this;

        vm.room = entity;
        vm.previousState = previousState.name;
        $scope.clientData = $localStorage.data.clientData;
        if(vm.room != null){
            if(vm.room.building == $scope.clientData.building.id){
                vm.room.buildingName = $scope.clientData.building.name;
            }
        }

        var unsubscribe = $rootScope.$on('hostelApp:roomUpdate', function(event, result) {
            vm.room = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
