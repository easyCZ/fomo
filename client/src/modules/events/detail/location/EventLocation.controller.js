(() => {


    class EventLocationController {

        constructor() {

            console.log('constructed');

            this.map = {
                center: {
                    latitude: 45,
                    longitude: -73
                },
                zoom: 8
            };

        }

    }


    angular
        .module('fomo.events.detail.location', [
            'uiGmapgoogle-maps'
        ])
        .controller('EventLocationController', EventLocationController);

})();