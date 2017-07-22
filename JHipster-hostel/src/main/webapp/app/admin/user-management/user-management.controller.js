(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('UserManagementController', UserManagementController);

    UserManagementController.$inject = ['Principal', 'User', 'ParseLinks', 'AlertService', '$state', 'pagingParams',
            'paginationConstants', '$scope', '$localStorage'];

    function UserManagementController(Principal, User, ParseLinks, AlertService, $state, pagingParams,
            paginationConstants, $scope, $localStorage) {
        var vm = this;

        $scope.clientData = $localStorage.data.clientData;

        vm.authorities = ['ROLE_MANAGER', 'ROLE_STAFF', 'ROLE_CUSTOMER'];
        vm.currentAccount = null;
        vm.languages = null;
        vm.loadAll = loadAll;
        vm.setActive = setActive;
        vm.users = [];
        vm.page = 1;
        vm.totalItems = null;
        vm.clear = clear;
        vm.links = null;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.transition = transition;

        vm.roleValues = [];
        vm.showRoleChanger = null;

        vm.dataOf = null;
        vm.dataOfSelected = 'CUSTOMER';

        if($localStorage.data && $localStorage.data.user){
            if($localStorage.data.user.authorities && $localStorage.data.user.authorities.indexOf('ROLE_CUSTOMER') >= 0){
                vm.showRoleChanger = false;
            } else {
                vm.showRoleChanger = true;
                if($localStorage.data.user.authorities.indexOf('ROLE_ADMIN') >= 0){
                    vm.roleValues = ['ALL', 'MANAGER', 'STAFF', 'CUSTOMER'];
                } else if($localStorage.data.user.authorities.indexOf('ROLE_MANAGER') >= 0){
                   vm.roleValues = ['MANAGER', 'STAFF', 'CUSTOMER'];
                } else if($localStorage.data.user.authorities.indexOf('ROLE_STAFF') >= 0){
                    vm.roleValues = ['CUSTOMER'];
                }
            }
        } else {
            AlertService.error("Something went wrong. Please log out and login again.");
        }

        vm.loadAll();
        Principal.identity().then(function(account) {
            vm.currentAccount = account;
        });

        function setActive (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                vm.loadAll();
                vm.clear();
            });
        }

        function loadAll () {
            if($scope.clientData == null || $scope.clientData.client == null){
                AlertService.error("Select the Client, Location and Building");
            } else{
                //var dataOf = 'customer';
                if(vm.dataOfSelected == null ||vm.dataOfSelected === "CUSTOMER"){
                    vm.dataOf = 'customer';
                } else {
                    if(vm.dataOfSelected == 'MANAGER' || vm.dataOfSelected == 'ALL'){
                        vm.dataOf = 'clients';
                    } else {
                        vm.dataOf = 'customer';
                    }
                }
                User.query({
                    page: pagingParams.page - 1,
                    size: vm.itemsPerPage,
                    sort: sort(),
                    clients: vm.dataOf,
                    idx: $scope.clientData.client.id
                }, onSuccess, onError);
            }
        }

        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.page = pagingParams.page;

            for (var key in data) {
                if(data[key] != null){
                    if(data[key].client == $scope.clientData.client.id){
                        data[key].client = $scope.clientData.client.clientName;
                    }
                }
            }

            vm.users = data;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        function clear () {
            vm.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null, client: null
            };
        }

        function sort () {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }

        function loadPage (page) {
            vm.page = page;
            vm.transition();
        }

        function transition () {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
