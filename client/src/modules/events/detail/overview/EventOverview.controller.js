(() => {

    class EventOverviewController {

        constructor($scope, $stateParams, Event) {
            console.log("here");
            if ($stateParams.event) {
                $scope.event = $stateParams.event;
            } else {
                Event.get($stateParams.eventId).then((event) => {
                        $scope.event = event;
                    }, (error) => {

                    }
                );

            }

        }

    }

    EventOverviewController.$inject = ['$scope','$stateParams','Event']

    angular.module('fomo.events.detail.overview', [])
           .controller('EventOverviewController', EventOverviewController);
})();