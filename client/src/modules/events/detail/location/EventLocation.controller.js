(() => {


    class EventLocationController {

        constructor(uiGmapGoogleMapApi) {

            uiGmapGoogleMapApi.then((maps) => {
                this.map = {
                    center: {
                        latitude: 45,
                        longitude: -73
                    },
                    zoom: 8
                };
            });

        }

    }

    EventLocationController.$inject = [
        'uiGmapGoogleMapApi'
    ];


    angular
        .module('fomo.events.detail.location', [
            'uiGmapgoogle-maps'
        ])
        .controller('EventLocationController', EventLocationController);

})();