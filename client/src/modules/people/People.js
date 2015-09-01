(() => {

    let People = (Restangular) => {
        return Restangular.service('people');
    };

    People.$inject = ['Restangular'];

    angular
        .module('fomo.people.People', ['restangular'])
        .factory('People', People);
})();