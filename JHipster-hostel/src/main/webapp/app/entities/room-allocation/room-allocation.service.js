(function() {
    'use strict';
    angular
        .module('hostelApp')
        .factory('RoomAllocation', RoomAllocation);

    RoomAllocation.$inject = ['$resource', 'DateUtils'];

    function RoomAllocation ($resource, DateUtils) {
        var resourceUrl =  'api/room-allocations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fromDate = DateUtils.convertLocalDateFromServer(data.fromDate);
                        data.toDate = DateUtils.convertLocalDateFromServer(data.toDate);
                        data.updatedDateTime = DateUtils.convertLocalDateFromServer(data.updatedDateTime);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fromDate = DateUtils.convertLocalDateToServer(copy.fromDate);
                    copy.toDate = DateUtils.convertLocalDateToServer(copy.toDate);
                    copy.updatedDateTime = DateUtils.convertLocalDateToServer(copy.updatedDateTime);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.fromDate = DateUtils.convertLocalDateToServer(copy.fromDate);
                    copy.toDate = DateUtils.convertLocalDateToServer(copy.toDate);
                    copy.updatedDateTime = DateUtils.convertLocalDateToServer(copy.updatedDateTime);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
