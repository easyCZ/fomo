(() => {

    class CreateEventController {

        constructor(Event) {
            this.Event = Event;
        }



    }

    CreateEventController.$inject = ['Event'];

    angular
        .module('fomo.events.create', [
            'fomo.events.Event'
        ])
        .controller('CreateEvent', CreateEventController);

})();