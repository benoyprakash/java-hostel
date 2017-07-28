(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('LocationController', LocationController);

    LocationController.$inject = ['$state', 'DataUtils', 'Location', 'ParseLinks', 'AlertService', 'paginationConstants'
    , 'pagingParams', '$localStorage', '$scope'];

    function LocationController($state, DataUtils, Location, ParseLinks, AlertService, paginationConstants, pagingParams
    , $localStorage, $scope) {

        $scope.clientData = $localStorage.data.clientData;

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll () {

            if($scope.clientData ==null || ($scope.clientData.client == null)){
                AlertService.error("Select the Client");
            } else{
                Location.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    id:$scope.clientData.client.id,
                    clients: 'clients'

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

                    for (var key in data) {
                        if(data[key] != null){
                            if(data[key].client === $scope.clientData.client.id){
                                data[key].client = $scope.clientData.client.clientName;
                            }
                        }
                    }

                    vm.locations = data;
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
