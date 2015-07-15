(() => {
    angular.module('fomo.events', [
            'fomo.events.Event',
            'fomo.events.create',
            'fomo.events.routes',
            'fomo.events.list',
            'ngOpenFB',
            'fomo.select'
        ]);
})();