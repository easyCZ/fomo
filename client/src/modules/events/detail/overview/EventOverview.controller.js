(() => {

    class EventOverviewController {

        constructor($scope, $stateParams, EventList, $ionicPlatform, uiGmapGoogleMapApi) {
            EventList.get($stateParams.eventId).then((e) => {
                this.event = e;
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