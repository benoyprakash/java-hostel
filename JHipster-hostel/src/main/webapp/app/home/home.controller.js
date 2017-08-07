(function() {
    'use strict';

    angular
        .module('hostelApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$localStorage'];

    function HomeController ($scope, Principal, LoginService, $state, $localStorage) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        //$scope.currentUser = $localStorage.data.user;

        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                if(account){
                    $localStorage.data.user = {};
                    $localStorage.data.user = account;
                }
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
