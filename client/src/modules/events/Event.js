(() => {

    let Event = (Restangular) => {
        return Restangular.service('events');
    };

    Event.$inject = ['Restangular'];

    angular
        .module('fomo.events.Event', ['restangular', 'fomo.people.People'])
        .factory('Event', Event)
        .factory('EventList', ['Event', 'People', '$openFB', 'Restangular', (Event, People, $openFB, Restangular) => {
            class EventList {
                constructor() {
                    this.events = [];
                    this.notAttending = [];
                }

                getList() {
                    var self = this;
                    return new Promise((resolve, reject) => {
                        People.one().get().then((me) => {
                            self.me = me;
                            return Event.getList();
                        }).then((events) => {
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
                    return this._findEvent(this.events, id);
                }

                amIAttending(e) {
                    var response = _.find(e.responses, (r) => { return r.responder.id === this.me.id; });
                    return !response || response.isAttending;
                }

                addEvent(e) {
                    if (this.amIAttending(e) && !this._findEvent(this.notAttending, e.id || e.fbId)) {
                        this._addEvent(this.events, e);
                    } else {
                        this._addEvent(this.notAttending, e);
                    }
                }

                _addEvent(list, e) {
                    var dupedEvent = this._findEvent(list, e.id || e.fbId);
                    if (!dupedEvent) {
                        list.push(e);
                    } else {
                        var indexOfDupe = list.indexOf(dupedEvent);
                        if (~indexOfDupe) {
                            list[indexOfDupe] = e;
                        }
                    }
                }

                _findEvent(list, id) {
                    return _.find(list, (e) => {
                        return e.id === id || e.fbId === id;
                    });
                }

            }
            return new EventList(Event);
        }]);
})();