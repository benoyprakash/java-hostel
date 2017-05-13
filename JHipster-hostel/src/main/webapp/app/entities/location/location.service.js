    (function() {
        'use strict';
        angular
            .module('hostelApp')
            .factory('Location', Location);

        Location.$inject = ['$resource'];

        function Location ($resource) {

                var resourceUrl =  'api/locations/:client/:clientId/:id';
                return $resource(resourceUrl, {client:'@client', clientId: '@clientId', id: '@id'}, {
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
                    'update': { method:'PUT' },
                    'locationByClient': {
                        method: 'GET',
                        transformResponse: function (data) {
                            if (data) {
                                data = angular.fromJson(data);
                            }
                            return data;
                        }
                    }
                });
        }
    })();
