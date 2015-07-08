(() => {

    // TODO: Load Google Maps dynamically with a promise
    let MapDirective = {
        restrict: 'E',
        scope: {
            location: '='
        },
        link: (scope, element, attrs) => {

            // Generate a new random id for the element
            var timestamp = Date.now() + '';
            element.attr('id', timestamp);

            var site;
            if (location.lat && location.lng) {
                site  = new google.maps.LatLng(55.9879314,-4.3042387);
            } else {
                // Find your location
            }

            var mapOptions = {
                streetViewControl:true,
                center: site,
                zoom: 18,
                mapTypeId: google.maps.MapTypeId.TERRAIN
            };

            var map = new google.maps.Map(document.getElementById(timestamp), mapOptions);





        }
    }

    angular
        .module('fomo.location.map', [])
        .directive('map', () => MapDirective);

})();