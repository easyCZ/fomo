(() => {

  let EventsRoute = ($stateProvider) => {
    $stateProvider
      .state('events', {
        url: '/events',
        abstract: true,
        template: '<ion-nav-view></ion-nav-view>'

        // views: {
        //   'tab-chats': {
        //     templateUrl: 'modules/events/Events.html',
        //     controller: 'ChatsController as chats'
        //   }
        // }
      })

      .state('events.list', {
        url: '',
        templateUrl: 'modules/events/Events.html',
        // controller: 'EventListController as eventsCtrl'
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
      })

      // .state('events.detail', {
      //   url: '/:eventId',
      //   templateUrl: 'modules/events/detail/EventDetail.html'
      // });
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
