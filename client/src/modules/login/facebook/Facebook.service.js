(() => {

    let Facebook = (ngFB) => {
        return {

            init: () => ngFB.init({appId: '885671584822805'})

        };
    }


    angular
        .module('fomo.login.facebook', ['ngFB'])

})();