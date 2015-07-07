(() => {

    let PeopleListDirective = {
        restrict: 'E',
        scope: {
            people: '='
        },
        templateUrl: 'modules/people/list/PeopleList.html',
        controller: 'PeopleListController as peopleCtrl'

    };

    angular
        .module('fomo.people.list', [
            'fomo.people.list.PeopleListController'
        ])
        .directive('peopleList', () => PeopleListDirective);


})();