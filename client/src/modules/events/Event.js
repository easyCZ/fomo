(() => {

    let Event = (Restangular) => {
        return Restangular.service('events');
    };

    Event.$inject = ['Restangular'];

    angular
        .module('fomo.events.Event', ['restangular'])
        .factory('Event', Event)
        .factory('EventList', ['Event', '$openFB', 'Restangular', (Event, $openFB, Restangular) => {
            class EventList {
                constructor() {
                    this.events = [];
                }

                getList() {
                    var self = this;
                    return new Promise((resolve, reject) => {
                        Event.getList().then((events) => {
                            events.forEach((e) => {
                                self.addEvent(e);
                                self.sort();
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
                                        self.addEvent({
                                            img: event.cover.source,
                                            title: event.name,
                                            location: event.place,
                                            id: event.id,
                                            fbId: event.id,
                                            description: event.description,
                                            type: 'fb',
                                            startTime: event.start_time
                                        });
                                        self.sort();
                                    });
                                }
                                resolve(self.events)
                            })
                        });
                    });
                }

                get(id) {
                    var self = this;
                    var event = this.findEvent(id);
                    if (!event) {
                        return new Promise((resolve) => {
                            Promise.resolve(self.getList()).then(() => {
                                var returnEvent = self.findEvent(id);
                                resolve(returnEvent);
                            });
                        });
                    } else {
                        return new Promise((resolve) => {
                            resolve(event);
                        });
                    }
                }

                sort() {
                    this.events.sort((a, b) =>{
                        a = new Date(a.startTime);
                        b = new Date(b.startTime);
                        return a>b ? 1 : a<b ? -1 : 0;
                    });
                }

                notGoingTo(e) {
                    if (e.type === 'fb') {
                        delete e.id;
                        delete e.type;
                    }
                    Restangular.service('notGoing', Restangular.one('events')).post(e).then(() => {
                            this.events.splice(this.events.indexOf(e), 1);
                        },
                        () => {
                            console.log("errored attempting to respond to an event.")
                        });
                }

                findEvent(id) {
                    return _.find(this.events, (e) => {
                        return e.id === id || e.fbId === id;
                    });
                }

                addEvent(e) {
                    var dupedEvent = this.findEvent(e.id || e.fbId);
                    if (!dupedEvent) {
                        this.events.push(e);
                    } else {

                        var indexOfDupe = this.events.indexOf(dupedEvent);
                        if (~indexOfDupe) {
                            this.events[indexOfDupe] = e;
                        }
                    }
                }
            }
            return new EventList(Event);
        }]);
})();