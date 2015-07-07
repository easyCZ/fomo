(() => {

    class EventPeopleController {

        constructor() {

        }

    }

    angular
        .module('fomo.events.detail.people', [
            'fomo.people.list'
        ])
        .controller('EventPeopleController', EventPeopleController);

})();