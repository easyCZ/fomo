(() => {

    const NAME = '[EventListController]';

    // let getEvents = () => {
    //     for (var )
    // }

    class EventListController {

        constructor(Event, $scope, $log, $openFB) {
            this.Event = Event;
            this.$log = $log;
            this.$scope = $scope;

            $openFB.api({
                path: '/me/friends'
            }, (error, result) => {
                console.log('friends', error, result);
            });

            this.swipable = true;

            // this.update();
            this.$log.debug(NAME, 'Initialized.');
            this.update();
        }

        update() {
            this.Event.getList().then((events) => {
                this.events = events
            }, (error) => {
                this.$log(error);
            });
        }



    }

    EventListController.$inject = ['Event', '$scope', '$log', '$openFB'];


    angular
        .module('fomo.events.list.EventListController', [])
        .controller('EventListController', EventListController);


})();