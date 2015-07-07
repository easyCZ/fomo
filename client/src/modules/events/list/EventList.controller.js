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
            })

            this.swipable = true;

            // this.update();
            this.$log.debug(NAME, 'Initialized.');

            this.events = [
                {
                    id: 1,
                    title: 'Thames Rafting',
                    description: 'Description 1',
                    location: 'East Bank, the Thames',
                    img: 'https://d13yacurqjgara.cloudfront.net/users/1390/avatars/normal/db4c26f5c3acf2c3773790648da8f2af.png?1405438412'
                },
                {
                    id: 2,
                    title: 'Event 2',
                    description: 'Description 1',
                    location: 'East Bank, the Thames',
                    img: 'https://d13yacurqjgara.cloudfront.net/users/1390/avatars/normal/db4c26f5c3acf2c3773790648da8f2af.png?1405438412'

                },
                {
                    id: 3,
                    title: 'Event 3',
                    description: 'Description 1',
                    location: 'East Bank, the Thames',
                    img: 'https://d13yacurqjgara.cloudfront.net/users/1390/avatars/normal/db4c26f5c3acf2c3773790648da8f2af.png?1405438412'
                },
                {
                    id: 4,
                    title: 'Event 4',
                    description: 'Description 1',
                    location: 'East Bank, the Thames',
                    img: 'https://d13yacurqjgara.cloudfront.net/users/1390/avatars/normal/db4c26f5c3acf2c3773790648da8f2af.png?1405438412'
                }
            ];
        }

        update() {
            this.Event.getList().then((events) => {
                this.events = events
            }, (error) => {

            });
        }



    }

    EventListController.$inject = ['Event', '$scope', '$log', '$openFB'];


    angular
        .module('fomo.events.list.EventListController', [])
        .controller('EventListController', EventListController);


})();