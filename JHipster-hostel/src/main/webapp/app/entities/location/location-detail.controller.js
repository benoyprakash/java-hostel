(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity',
        'Location', '$localStorage'];

    function LocationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Location,
        $localStorage) {

        $scope.clientData = $localStorage.data.clientData;
        var vm = this;

        vm.location = entity;


        if(vm.location != null){
            if(vm.location.client == $scope.clientData.client.id){
                vm.location.clientName = $scope.clientData.client.clientName;
            }
        }


        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('hostelApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
