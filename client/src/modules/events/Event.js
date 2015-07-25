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
                    return Event.getList().then((events) => {
                        self.events = events;
                        return events[0];
                    }, (error) => {
                        self.error = error;
                    });
                }

                get(id) {
                    var self = this;
                    var findEvent = (id) => { return _.find(this.events, (e) => {
                        return e.id == id;
                    })};
                    if (!this.events) {
                        return new Promise((resolve) => {
                            Promise.resolve(self.getList()).then(() => {
                                resolve(findEvent(id));
                            });
                        });
                    } else {
                        return new Promise((resolve) => {
                            resolve(findEvent(id));
                        });
                    }
                }
            }
            return new EventList(Event);
        }]);
})();