(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('RoomAllocationController', RoomAllocationController);

    RoomAllocationController.$inject = ['$scope', '$state', '$localStorage', 'RoomAllocation', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function RoomAllocationController($scope, $state, $localStorage, RoomAllocation, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;
        $scope.clientData = $localStorage.data.clientData;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        function loadAll () {

            if($scope.clientData ==null || ($scope.clientData.client ==null  && $scope.clientData.location ==null  && $scope.clientData.building ==null)){
                AlertService.error("Select the Client, Location and Building");
            } else{

                RoomAllocation.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    param1:$scope.clientData.building.id,
                    param2:'ACTIVE'
                }, onSuccess, onError);
                function sort() {
                    var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                    if (vm.predicate !== 'id') {
                        result.push('id');
                    }
                    return result;
                }
                function onSuccess(data, headers) {
                    vm.links = ParseLinks.parse(headers('link'));
                    vm.totalItems = headers('X-Total-Count');
                    vm.queryCount = vm.totalItems;
                    vm.roomAllocations = data;
                    vm.page = pagingParams.page;
                }
                function onError(error) {
                    AlertService.error(error.data.message);
                }
            }

        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
