(() => {

    class EventOverviewController {

        constructor($scope, $stateParams, EventList, $ionicPlatform) {
            EventList.get($stateParams.eventId).then((e) => {
                this.event = e;
                $scope.$apply(); // why is this necessary???
            });
            $ionicPlatform.onHardwareBackButton((e) => {
                this.EventList.getList(); // refresh the list
                this.$scope.go('events.list');
                e.stopPropagation();
            });

        }

    }

    EventOverviewController.$inject = ['$scope', '$stateParams','EventList', '$ionicPlatform'];

    angular.module('fomo.events.detail.overview', [])
           .controller('EventOverviewController', EventOverviewController);
})();