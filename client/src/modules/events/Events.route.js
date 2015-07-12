(() => {

  let EventsRoute = ($stateProvider) => {
    $stateProvider
      .state('events', {
        url: '/events',
        abstract: true,
        template: '<ion-nav-view></ion-nav-view>'
      })

      .state('events.list', {
        url: '',
        templateUrl: 'modules/events/Events.html'
      })

      .state('events.create', {
        url: '/create',
        templateUrl: 'modules/events/create/CreateEvent.html',
        resolve: {
          NewEvent: ['Event', (Event) => {
            return Event.one();
          }]
        },
        controller: 'CreateEventController as create'
      });
  };

  EventsRoute.$inject = ['$stateProvider'];
  let dependencies = [
    'ionic',
    'fomo.events.detail.routes'
  ];

  angular
    .module('fomo.events.routes', dependencies)
    .config(EventsRoute)

})();
