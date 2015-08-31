(() => {
    angular.module('fomo.onswipe', ['ngTouch', 'fomo.events.Event'])
        .directive('onSwipe', ['$swipe', 'EventList', function ($swipe, EventList) {
            'use strict';
            return {
                restrict: 'A',
                link: function (scope, element, attrs, ctrl) {
                    $swipe.bind(element, {
                        'start': function(coords, e) {
                            element.addClass('red-item');
                        },
                        'cancel': function(e) {
                            element.removeClass('red-item');
                        },
                        'end': function(coords, e) {
                            EventList.notGoingTo(scope.item);
                        }
                    });
                }
            };
        }]);
})();