(() => {

    class EventDetailController {
        constructor($stateParams) {
            this.id = $stateParams.eventId;
        }
    }

    EventDetailController.$inject = ['$stateParams'];

    angular.module('fomo.events.detail', [ ])
        .controller('EventDetailController', EventDetailController);

})();