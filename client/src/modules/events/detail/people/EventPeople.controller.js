(() => {

    class EventPeopleController {

        constructor(EventList, $stateParams) {
            EventList.get($stateParams.eventId).then((e) => {
                this.people = e.people;
            });
        }
    }

    EventPeopleController.$inject = ['EventList', '$stateParams'];

    angular.module('fomo.events.detail.people', [
            'fomo.people.list'
        ])
        .controller('EventPeopleController', EventPeopleController);

})();