(() => {

    class CreateEventController {

        constructor(NewEvent, $state) {
            this.NewEvent = NewEvent;
            this.$state = $state;

            this.days = (() => {
                for (var i = 1, list = []; i <= 31; i++)
                    list.push(i);
                return list;
            })();

            this.months = ["January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            ];

            this.years = (() => {
                let date = new Date();
                let year = date.getFullYear();
                for (var i = year, years = []; i <= year + 5; i++)
                    years.push(i);
                return years;
            })();
        }

        submit(newEvent) {
            this.submitting = true;

            let startTime = new Date(
                newEvent.meta.year, newEvent.meta.month, newEvent.meta.day);
            newEvent.startTime = startTime.getTime();
            delete newEvent.meta;

            newEvent.post().then(
                (event) => this.onEventSubmitSuccess(event),
                (err) => this.onEventSubmitError(err)
            );
        }

        onEventSubmitSuccess(event) {
            this.submitting = false;
            this.$state.go('events.detail.overview', {
                eventId: event.id
            });
        }

        onEventSubmitError(error) {
            this.submitting = false;
        }



    }

    CreateEventController.$inject = ['NewEvent', '$state'];

    angular
        .module('fomo.events.create', [
            'fomo.events.Event'
        ])
        .controller('CreateEventController', CreateEventController);

})();