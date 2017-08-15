(function() {
    'use strict';

    angular
        .module('hostelApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payments', {
            parent: 'entity',
            url: '/payments',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Payments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payments/payments.html',
                    controller: 'PaymentsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('payments-detail', {
            parent: 'payments',
            url: '/payments/{id}',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Payments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payments/payments-detail.html',
                    controller: 'PaymentsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Payments', function($stateParams, Payments) {
                    return Payments.get({param1 : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'payments',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('payments-detail.edit', {
            parent: 'payments-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payments/payments-dialog.html',
                    controller: 'PaymentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payments', function(Payments) {
                            return Payments.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payments.new', {
            parent: 'payments',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payments/payments-dialog.html',
                    controller: 'PaymentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                room: null,
                                customer: null,
                                dateOfJoin: null,
                                amount: null,
                                paymentDate: null,
                                paymentFrom: null,
                                paymentTo: null,
                                paymentStatus: null,
                                payentAgainst: null,
                                comments: null,
                                building: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payments', null, { reload: 'payments' });
                }, function() {
                    $state.go('payments');
                });
            }]
        })
        .state('payments.edit', {
            parent: 'payments',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payments/payments-dialog.html',
                    controller: 'PaymentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payments', function(Payments) {
                            return Payments.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payments', null, { reload: 'payments' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payments.delete', {
            parent: 'payments',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN', 'ROLE_MANAGER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payments/payments-delete-dialog.html',
                    controller: 'PaymentsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Payments', function(Payments) {
                            return Payments.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payments', null, { reload: 'payments' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
