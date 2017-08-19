//(function() {
//    'use strict';
//
//    angular
//        .module('hostelApp')
//        .directive('clientSelectionDirective', function(){
//
//        return {
//                template: ''
//        +'<form name="form" role="form" novalidate>'
//        +    '<div class="col-md-3">'
//        +        '<div class="form-group" >'
//        +            '<div class="row">'
//        +                '<div class="col-md-4">'
//        +                    '<label >Client</label>'
//        +                '</div>'
//        +                '<div class="col-md-8">'
//        +                    '<select class="form-control"  ng-model="selectedClient" ng-options="client as client.clientName for client in vm.clients" '
//        +                                     ' ng-change="onClientChangeEvent(this)" ng-disabled="! vm.editEnabled">'
//        +                    '</select>'
//        +               ' </div>'
//        +            '</div>'
//        +        '</div>'
//        +    '</div>'
//
//        +    '<div class="col-md-3">'
//        +        '<div class="form-group" >'
//        +            '<div class="row">'
//        +                '<div class="col-md-4">'
//        +                    '<label >Location</label>'
//        +                '</div>'
//        +                '<div class="col-md-8">'
//        +                    '<select class="form-control"  ng-model="selectedLocation" ng-options="location as location.locationName for location in vm.locations" '
//        +                           ' ng-change="onLocationChangeEvent()" ng-disabled="! vm.editEnabled">'
//        +                    '</select>'
//        +               ' </div>'
//        +            '</div>'
//        +        '</div>'
//        +    '</div>'
//
//        +    '<div class="col-md-3">'
//        +        '<div class="form-group" >'
//        +            '<div class="row">'
//        +                '<div class="col-md-4">'
//        +                    '<label >Building</label>'
//        +                '</div>'
//        +                '<div class="col-md-8">'
//        +                    '<select class="form-control"  ng-model="selectedBuilding" ng-options="bldg as bldg.name for bldg in vm.buildings" '
//        +                           ' ng-change="onBuildingChangeEvent()" ng-disabled="! vm.editEnabled">'
//        +                    '</select>'
//        +               ' </div>'
//        +            '</div>'
//        +        '</div>'
//        +    '</div>'
//        +    '<div class="col-md-2">'
//        +        '<button type="submit" class="btn btn-primary" ng-click="toggleEdit()" >{{vm.editButtomLabel}}</button>'
//        +    '</div>'
//        +'</form>'
//
//            };
//
//        });
//})();
