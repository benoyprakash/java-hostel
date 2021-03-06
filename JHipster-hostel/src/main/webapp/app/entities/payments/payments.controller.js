(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('PaymentsController', PaymentsController);

    PaymentsController.$inject = ['Payments', 'ParseLinks', 'AlertService', 'paginationConstants', '$scope', '$localStorage'];

    function PaymentsController(Payments, ParseLinks, AlertService, paginationConstants, $scope, $localStorage) {


        $scope.clientData = $localStorage.data.clientData;
        var vm = this;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.paymentStatus = ['PAID', 'PAID_PARTIAL', 'NOT_PAID', 'CANCELLED', 'OTHERS'];
        vm.searchPaymentStatusSelected = null;
        vm.searchFromDate = null;
        vm.searchToDate = null;

        vm.payments = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        $scope.onSearch = function (){
            loadAll ();
        }

        function loadAll () {

            if($scope.clientData ==null || ($scope.clientData.client == null)){
                AlertService.error("Select the Client");
            } else{
                Payments.query({
                    page: vm.page,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    param1 : 'building',
                    param2: $scope.clientData.building.id,
                    searchFromDate : vm.searchFromDate,
                    searchToDate : vm.searchToDate,
                    searchPaymentStatus : vm.searchPaymentStatusSelected
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
                    vm.payments = [];
                    for (var i = 0; i < data.length; i++) {
                        vm.payments.push(data[i]);
                    }
                }

                function onError(error) {
                    AlertService.error(error.data.message);
                }
            }
        }

        function reset () {
            vm.page = 0;
            vm.payments = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        vm.datePickerOpenStatus.searchFromDate = false;
        vm.datePickerOpenStatus.searchToDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
