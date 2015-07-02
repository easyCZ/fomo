(() => {

  class AccountController {

    constructor(Event) {
        this.newEvent = {};

        this.Event = Event;
    //     var options = {
    //         date: new Date(),
    //         mode: 'date', // or 'time'
    //         minDate: new Date() - 10000,
    //         allowOldDates: true,
    //         allowFutureDates: false,
    //         doneButtonLabel: 'DONE',
    //         doneButtonColor: '#F2F3F4',
    //         cancelButtonLabel: 'CANCEL',
    //         cancelButtonColor: '#000000'
    //       };

    // $cordovaDatePicker.show(options).then(function(date){
    //     alert(date);
    // });


    }

    submit(event) {
        this.Event.post(event);
    }

  }

  AccountController.$inject = ['Event'];

  angular
    .module('starter.account.controller', [])
    .controller('AccountController', AccountController);
})();