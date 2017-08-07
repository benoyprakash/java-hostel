(function() {
    'use strict';
    angular
        .module('hostelApp')
        .factory('Payments', Payments);

    Payments.$inject = ['$resource', 'DateUtils'];

    function Payments ($resource, DateUtils) {
        var resourceUrl =  'api/payments/:param1/:param2';

        return $resource(resourceUrl, {param1 : '@param1', param2 : '@param2'}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateOfJoin = DateUtils.convertLocalDateFromServer(data.dateOfJoin);
                        data.paymentDate = DateUtils.convertLocalDateFromServer(data.paymentDate);
                        data.paymentFrom = DateUtils.convertLocalDateFromServer(data.paymentFrom);
                        data.paymentTo = DateUtils.convertLocalDateFromServer(data.paymentTo);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateOfJoin = DateUtils.convertLocalDateToServer(copy.dateOfJoin);
                    copy.paymentDate = DateUtils.convertLocalDateToServer(copy.paymentDate);
                    copy.paymentFrom = DateUtils.convertLocalDateToServer(copy.paymentFrom);
                    copy.paymentTo = DateUtils.convertLocalDateToServer(copy.paymentTo);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateOfJoin = DateUtils.convertLocalDateToServer(copy.dateOfJoin);
                    copy.paymentDate = DateUtils.convertLocalDateToServer(copy.paymentDate);
                    copy.paymentFrom = DateUtils.convertLocalDateToServer(copy.paymentFrom);
                    copy.paymentTo = DateUtils.convertLocalDateToServer(copy.paymentTo);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
