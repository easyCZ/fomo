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
        templateUrl: 'modules/events/detail/EventDetailOverview.html'
      })

      .state('events.detail.location', {
        url: '/location',
        templateUrl: 'modules/events/detail/location/EventLocation.html',
        controller: 'EventLocationController as location'
      })

      .state('events.detail.people', {
        url: '/people',
        templateUrl: 'modules/events/detail/EventDetail.html'
      })
  };

  EventsRoute.$inject = ['$stateProvider'];
  let dependencies = [
    'ionic',
    'fomo.events.detail.location'
  ];

  angular
    .module('fomo.events.detail.routes', dependencies)
    .config(EventsRoute)

})();
