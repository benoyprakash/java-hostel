(function() {
    'use strict';

    angular
        .module('hostelApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('building', {
            parent: 'entity',
            url: '/building?page&sort&search',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER'],
                pageTitle: 'Buildings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/buildings.html',
                    controller: 'BuildingController',
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
        .state('building-detail', {
            parent: 'building',
            url: '/building/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Building'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/building-detail.html',
                    controller: 'BuildingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Building', function($stateParams, Building) {
                    return Building.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'building',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('building-detail.edit', {
            parent: 'building-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building.new', {
            parent: 'building',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                location: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('building');
                });
            }]
        })
        .state('building.edit', {
            parent: 'building',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building.delete', {
            parent: 'building',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-delete-dialog.html',
                    controller: 'BuildingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
