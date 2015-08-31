(() => {

    class EventOverviewController {

        constructor($scope, $stateParams, EventList, $ionicPlatform, uiGmapGoogleMapApi) {
            EventList.get($stateParams.eventId).then((e) => {
                this.event = e;
                if (this.map) {
                    if (e.type === 'fb' && e.location) {
                        this.map.center = e.location.location;
                        this.map.marker = {
                            id: 0,
                            coords: {
                                latitude: e.location.location.latitude,
                                longitude: e.location.location.longitude
                            }

                        }
                    } else if (e.location) {
                        this.map.center = e.location;
                        this.map.marker = {
                            id: 0,
                            coords: {
                                latitude: e.location.latitude,
                                longitude: e.location.longitude
                            }

                        }
                    }
                }
                $scope.$apply(); // why is this necessary???
            });
            $ionicPlatform.onHardwareBackButton((e) => {
                this.EventList.getList(); // refresh the list
                this.$scope.go('events.list');
                e.stopPropagation();
            });
            uiGmapGoogleMapApi.then((maps) => {
                this.map = {
                    center: {
                        latitude: 51.51419,
                        longitude: -0.10811
                    },
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

    }

    EventOverviewController.$inject = ['$scope', '$stateParams','EventList', '$ionicPlatform', 'uiGmapGoogleMapApi'];

    angular.module('fomo.events.detail.overview', ['uiGmapgoogle-maps'])
           .controller('EventOverviewController', EventOverviewController);
})();