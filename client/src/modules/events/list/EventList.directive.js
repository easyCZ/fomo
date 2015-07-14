(() => {

    let EventList = {
        restrict: 'E',
        templateUrl: 'modules/events/list/EventList.html',
        controller: 'EventListController as eventsCtrl'
    };

    angular.module('fomo.events.list', [ 'fomo.events.list.EventListController' ])
        .directive('eventList', () => EventList);

})();