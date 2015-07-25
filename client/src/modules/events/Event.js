(() => {

    let Event = (Restangular) => {
        return Restangular.service('events');
    };

    Event.$inject = ['Restangular'];

    angular
        .module('fomo.events.Event', ['restangular'])
        .factory('Event', Event)
        .factory('EventList', ['Event', (Event) => {
            class EventList {
                constructor() {
                }

                getList() {
                    var self = this;
                    Event.getList().then((events) => {
                        self.events = events
                    }, (error) => {
                        self.error = error;
                    });
                }
            }
            return new EventList(Event);
        }]);
})();