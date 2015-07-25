(() => {
    angular.module('fomo.select', [])
    .directive('ionSelect', ['$timeout', function ($timeout) {
        'use strict';
        return {
            restrict: 'EAC',
            scope: {
                label: '@',
                labelField: '@',
                provider: '=',
                ngModel: '=?',
                ngValue: '=?'
            },
            require: '?ngModel',
            transclude: false,
            replace: false,
            template: '<div class="select-container">'
            + '<label class="item item-input item-stacked-label">'
            + '<ul>'
            + '<li ng-repeat="item in ngModel">'
            +   '<div class="person-item">'
            +       '<img src="http://graph.facebook.com/{{::item.id}}/picture?type=square"><div>{{::item[labelField]}}</div>'
            +       '<div><button class="ion-minus-circled button icon button-icon" ng-click="ngModel.splice($index, 1)"></button><div>'
            + '</div>'
            + '</li>'
            + '</ul>'
            + '<div class="item item-input-inset">'
            + '<label class="item-input-wrapper">'
            + '<i class="icon ion-ios7-search placeholder-icon"></i>'
            + '<input type="search" ng-model="inputModel" ng-value="ngValue" ng-keydown="onkeydown()"/>'
            + '</label>'
            + '<i class="icon ion-chevron-down"></i>'
            + '</button>'
            + '</div>'
            + '</label>'
            + '<div class="optionList padding-left padding-right" ng-show="showList">'
            + '<ion-scroll>'
            + '<ul class="list person-select-list">'
            + '<li ng-click="select(item)" ng-repeat="item in provider | filter:inputModel">'
            + '<div class="person-item"><img src="http://graph.facebook.com/{{::item.id}}/picture?type=square"><div>{{::item[labelField]}}</div></div>'
            + '</ul>'
            + '</ion-scroll>'
            + '</div>'
            + '</div>'
            ,
            link: function (scope, element, attrs, ctrl) {
                scope.ngValue = scope.ngValue !== undefined ? scope.ngValue : 'item';
                scope.ngModel = scope.ngModel || [];

                scope.select = (item) => {
                    scope.ngModel.push(item);
                    scope.showList = false;
                    scope.inputModel = "";
                };

                element.bind('click', function () {
                    element.find('input').focus();
                });

                scope.onkeydown = () => {
                    $timeout(() => {
                        scope.showList = !!scope.inputModel;
                    });
                };
            }
        };
    }]);
})();