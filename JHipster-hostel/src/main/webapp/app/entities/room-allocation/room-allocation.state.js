(function() {
    'use strict';

    angular
        .module('hostelApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('room-allocation', {
            parent: 'entity',
            url: '/room-allocation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RoomAllocations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/room-allocation/room-allocations.html',
                    controller: 'RoomAllocationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('room-allocation-detail', {
            parent: 'room-allocation',
            url: '/room-allocation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'RoomAllocation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/room-allocation/room-allocation-detail.html',
                    controller: 'RoomAllocationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'RoomAllocation', function($stateParams, RoomAllocation) {
                    return RoomAllocation.get({param1 : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'room-allocation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('room-allocation-detail.edit', {
            parent: 'room-allocation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/room-allocation/room-allocation-dialog.html',
                    controller: 'RoomAllocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RoomAllocation', function(RoomAllocation) {
                            return RoomAllocation.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('room-allocation.new', {
            parent: 'room-allocation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/room-allocation/room-allocation-dialog.html',
                    controller: 'RoomAllocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                roomId: null,
                                userId: null,
                                fromDate: null,
                                toDate: null,
                                currStatus: null,
                                updatedBy: null,
                                updatedDateTime: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('room-allocation', null, { reload: 'room-allocation' });
                }, function() {
                    $state.go('room-allocation');
                });
            }]
        })
        .state('room-allocation.edit', {
            parent: 'room-allocation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/room-allocation/room-allocation-dialog.html',
                    controller: 'RoomAllocationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RoomAllocation', function(RoomAllocation) {
                            return RoomAllocation.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('room-allocation', null, { reload: 'room-allocation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('room-allocation.delete', {
            parent: 'room-allocation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/room-allocation/room-allocation-delete-dialog.html',
                    controller: 'RoomAllocationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RoomAllocation', function(RoomAllocation) {
                            return RoomAllocation.get({param1 : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('room-allocation', null, { reload: 'room-allocation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
