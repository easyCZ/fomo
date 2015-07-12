(() => {

    class EventPeopleController {

        constructor() {
            this.people = this.generateMockPeople();
        }

        generateMockPeople(count=10) {
            for (var i = 0, list = []; i < count; i++) {
                list.push({
                    id: i,
                    name: 'Person ' + i,
                    img: 'https://d13yacurqjgara.cloudfront.net/users/1390/avatars/normal/db4c26f5c3acf2c3773790648da8f2af.png?1405438412',
                    attending: i % 2 === 0,
                    response: i % 2 === 0
                        ? 'Will be there in a few'
                        : 'Sorry mate, cannot make it'
                });
            }
            return list;
        }

    }

    angular
        .module('fomo.events.detail.people', [
            'fomo.people.list'
        ])
        .controller('EventPeopleController', EventPeopleController);

})();