(() => {

  let EventsRoute = ($stateProvider) => {
    $stateProvider

      .state('events.detail', {
        abstract: true,
        url: '/:eventId',
        templateUrl: 'modules/events/detail/EventDetail.html',
          controller: 'EventDetailController as detailCtrl'
      })

      .state('events.detail.overview', {
        url: '/overview',
        templateUrl: 'modules/events/detail/overview/EventDetailOverview.html',
        controller: 'EventOverviewController as overviewCtrl',
        params: {
          eventId: 0,
          event: 0
        }
      })

      .state('events.detail.people', {
        url: '/people',
        templateUrl: 'modules/events/detail/people/EventPeople.html',
        controller: 'EventPeopleController as peopleCtrl'
      })
  };

  let GoogleMapsConfig = (uiGmapGoogleMapApiProvider) => {
      uiGmapGoogleMapApiProvider.configure({
        key: 'AIzaSyBPs1HkUn63_ZrexiZnDfKPDzbLbAt8_wo',
        v: '3.17',
        sensor: true,
        libraries: 'places,weather,geometry,visualization'
      });
  };
  GoogleMapsConfig.$inject = ['uiGmapGoogleMapApiProvider'];

  EventsRoute.$inject = ['$stateProvider'];
  let dependencies = [
    'ionic',
    'fomo.events.detail',
    'fomo.events.detail.people',
    'fomo.events.detail.overview'
  ];

  angular
    .module('fomo.events.detail.routes', dependencies)
    .config(EventsRoute)
    .config(GoogleMapsConfig);

})();
