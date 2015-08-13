(() => {

    class CreateEventController {

        constructor(NewEvent, EventList, $state, $openFB, $ionicPlatform, uiGmapGoogleMapApi) {
            this.NewEvent = NewEvent;
            this.EventList = EventList;
            this.$state = $state;
            this.$ionicPlatform = $ionicPlatform;

            this.days = (() => {
                for (var i = 1, list = []; i <= 31; i++)
                    list.push(i);
                return list;
            })();

            this.months = ["January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            ];

            this.years = (() => {
                let date = new Date();
                let year = date.getFullYear();
                for (var i = year, years = []; i <= year + 5; i++)
                    years.push(i);
                return years;
            })();
            var self = this;
            $openFB.api({
                path: '/me/friends'
            }, (error, result) => {
                if (!error) {
                    self.friends = result.data.data;
                }
            });

            this.searchbox = {
                template: 'searchbox.tpl.html',
                    options: {
                        autocomplete: true,
                        componentRestrictions: {
                            country: 'gb'
                        }
                },
                events: {
                    place_changed: function (autocomplete) {
                        var place = autocomplete.getPlace();
                        if (place.address_components) {
                            self.map.center = {
                                latitude: place.geometry.location.lat(),
                                longitude: place.geometry.location.lng()
                            };
                            console.log(place);

                            self.map.marker = {
                                id:0,
                                coords: {
                                    latitude: place.geometry.location.lat(),
                                    longitude: place.geometry.location.lng()
                                },
                                place_id: place.place_id,
                                name: place.name,
                                options: {
                                    visible:false
                                },
                                templateparameter: place
                            };
                        }
                    }
                }
            };

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    self.map.center = {
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude
                    };
                    self.map.marker = {
                        id: 0,
                        coords: self.map.center
                    }

                });
            }

            uiGmapGoogleMapApi.then((maps) => {
                this.map = {
                    options : {
                        zoomControl:true,
                        mapTypeControl: true,
                        streetViewControl: true,
                        optimized: true
                    },
                    maxZoom :15,
                    zoom : 15,
                    minZoom : 10,
                    events: {
                        click:function() {
                            $scope.itemSelected = false;
                        }
                    },
                    clusterOptions: {
                        minimumClusterSize : 10
                    }
                };
            });
        }

        submit(eventForm) {
            this.submitting = true;
            if (eventForm.$invalid) {
                // Maybe do something more intelligent here
                return;
            }
            //TODO: Move all this into a service somewhere
            _.merge(this.NewEvent, eventForm.newEvent);

            this.NewEvent.people.forEach((person) => {
                person.fbId = person.id;
                delete person.id; // we only link to fb for now...
            });

            let startTime = new Date(this.NewEvent.meta.year, this.NewEvent.meta.month, this.NewEvent.meta.day);
            this.NewEvent.startTime = startTime.getTime();
            delete this.NewEvent.meta;
            this.NewEvent.location = {
                place_id: this.map.marker.place_id,
                formattedAddress: this.map.marker.formatted_address,
                name: this.map.marker.name,
                latitude: this.map.marker.coords.latitude,
                longitude: this.map.marker.coords.longitude
            };

            this.NewEvent.post().then(
                (event) => this.onEventSubmitSuccess(event),
                (err) => this.onEventSubmitError(err)
            );
        }

        onEventSubmitSuccess(event) {
            this.submitting = false;
            this.$ionicPlatform.onHardwareBackButton((e) => {
                this.EventList.getList().then(() => {
                    this.$scope.go('events.list');
                }); // refresh the list
                e.stopPropagation();
            });
            this.$state.go('events.detail.overview', {
                eventId: event.id,
                event: event
            });
        }

        onEventSubmitError(error) {
            this.submitting = false;
        }

    }

    CreateEventController.$inject = ['NewEvent', 'EventList', '$state', '$openFB', '$ionicPlatform', 'uiGmapGoogleMapApi'];

    angular.module('fomo.events.create', [
            'fomo.events.Event',
            'ngOpenFB',
            'fomo.select'
        ])
        .controller('CreateEventController', CreateEventController)
        .run(['$templateCache', function ($templateCache) {
            $templateCache.put('searchbox.tpl.html', '<input id="pac-input" class="pac-controls" type="text" placeholder="Search" ng-model="ngModel">');
        }]);

})();