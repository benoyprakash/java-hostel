(function() {
    'use strict';
    angular
        .module('hostelApp')
        .factory('Building', Building);

    Building.$inject = ['$resource'];

    function Building ($resource) {
        var resourceUrl =  'api/buildings/:locations/:id';

        return $resource(resourceUrl, {locations: '@locations', id: '@id'}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
