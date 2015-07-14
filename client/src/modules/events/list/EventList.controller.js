(() => {

    class EventListController {

        constructor(Event, $scope, $log, $openFB, $timeout) {
            this.Event = Event;
            this.$log = $log;
            this.$scope = $scope;

            $openFB.api({
                path: '/me/friends'
            }, (error, result) => {
                console.log('friends', error, result);
            });

            this.swipable = true;
            this.update();
            var that = this;
            $timeout(()=> {
                that.showList = true;
            }, 4000)
        }

        update() {
            var self = this;
            this.Event.getList().then((events) => {
                self.events = events
            }, (error) => {
                self.error = error;
            });
        }
    }

    EventListController.$inject = ['Event', '$scope', '$log', '$openFB', '$timeout'];

    angular.module('fomo.events.list.EventListController', [])
           .controller('EventListController', EventListController);


})();