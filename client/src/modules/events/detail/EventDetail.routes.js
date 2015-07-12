(() => {

  let EventsRoute = ($stateProvider) => {
    $stateProvider

      .state('events.detail', {
        abstract: true,
        url: '/:eventId',
        templateUrl: 'modules/events/detail/EventDetail.html'
      })

      .state('events.detail.overview', {
        url: '/overview',
        templateUrl: 'modules/events/detail/overview/EventDetailOverview.html',
        controller: 'EventOverviewController',
        params: {
          eventId: undefined,
          event: undefined
        }
      })

      .state('events.detail.location', {
        url: '/location',
        templateUrl: 'modules/events/detail/location/EventLocation.html',
        controller: 'EventLocationController as location'
      })

      .state('events.detail.people', {
        url: '/people',
        templateUrl: 'modules/events/detail/people/EventPeople.html',
        controller: 'EventPeopleController as eventPeopleCtrl'
      })
  };


  let GoogleMapsConfig = (uiGmapGoogleMapApiProvider) => {
      uiGmapGoogleMapApiProvider.configure({
        //    key: 'your api key',
        v: '3.17',
        sensor: true,
        // libraries: 'weather,geometry,visualization'
      });
  };
  GoogleMapsConfig.$inject = ['uiGmapGoogleMapApiProvider'];

  EventsRoute.$inject = ['$stateProvider'];
  let dependencies = [
    'ionic',
    'fomo.events.detail.location',
    'fomo.events.detail.people',
    'fomo.events.detail.overview'
  ];

  angular
    .module('fomo.events.detail.routes', dependencies)
    .config(EventsRoute)
    .config(GoogleMapsConfig);

})();
