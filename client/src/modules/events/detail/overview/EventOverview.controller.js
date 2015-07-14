(() => {

    class EventOverviewController {

        constructor($scope, $stateParams, Event) {
            console.log("here");
            if ($stateParams.event) {
                $scope.event = $stateParams.event;
            } else {
                Event.one($stateParams.eventId).get().then((event) => {
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