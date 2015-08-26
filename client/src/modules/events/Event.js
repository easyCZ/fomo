(() => {

    let Event = (Restangular) => {
        return Restangular.service('events');
    };

    Event.$inject = ['Restangular'];

    angular
        .module('fomo.events.Event', ['restangular'])
        .factory('Event', Event)
        .factory('EventList', ['Event', '$openFB', (Event, $openFB) => {
            class EventList {
                constructor() {
                    this.events
                }

                getList() {
                    var self = this;
                    return new Promise((resolve, reject) => {
                        Event.getList().then((events) => {
                            events.forEach((e) => {
                                self.events.push(e);
                            });
                        }, (error) => {
                            self.error = error;
                        }).then(function() {
                            $openFB.api({
                                path: '/v2.4/search',
                                params: {
                                    fields: 'name,cover,description,location,start_time',
                                    type: 'event',
                                    limit: 100,
                                    since: 'now',
                                    until: 'next year',
                                    q: 'a'
                                }
                            }, (error, result) => {
                                if (result && result.data && result.data.data) {
                                    result.data.data.forEach((event) => {
                                        event.cover = event.cover || {};
                                        self.events.push({
                                            img: event.cover.source,
                                            title: event.name,
                                            location: event.place,
                                            id: event.id,
                                            description: event.description,
                                            type: 'fb'
                                        });
                                    });
                                }
                                resolve(self.events)
                            })
                        });
                    });
                }

                get(id) {
                    var self = this;
                    var findEvent = (id) => { return _.find(this.events, (e) => {
                        return e.id == id;
                    })};
                    var event = findEvent(id);
                    if (!event) {
                        return new Promise((resolve) => {
                            Promise.resolve(self.getList()).then(() => {
                                var returnEvent = findEvent(id);
                                resolve(returnEvent);
                            });
                        });
                    } else {
                        return new Promise((resolve) => {
                            resolve(event);
                        });
                    }
                }
            }
            return new EventList(Event);
        }]);
})();