(() => {

    class EventListController {

        constructor(EventList, $scope, $log, $openFB, $timeout, $state) {
            this.EventList = EventList;
            this.$log = $log;
            this.$scope = $scope;

            $openFB.api({
                path: '/me/friends'
            }, (error, result) => {
                if (error) {
                    $state.go('login');
                }
            });

            this.EventList.getList();
            var that = this;
            $timeout(()=> {
                that.showList = true;
            }, 4000)
        }
    }

    EventListController.$inject = ['EventList', '$scope', '$log', '$openFB', '$timeout', '$state'];

    angular.module('fomo.events.list.EventListController', [])
           .controller('EventListController', EventListController);
})();