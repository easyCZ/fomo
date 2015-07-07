(() => {

    let FacebookLogin = ($openFB, $state, $location) => {
        const callbackUrl = `${$location.protocol()}://${$location.host()}:${$location.port()}/lib/ngOpenFB/oauthcallback.html`;
        $openFB.init({
            appId: '885671584822805',
            browserOauthCallback: callbackUrl
        });

        let redirectToLogin = () => {
            $state.go('login', {
                redirect: $state.current
            });
        };

        $openFB.isLoggedIn().then(
            (success) => angular.noop(),
            (error) => redirectToLogin()
        );
    };
    FacebookLogin.$inject = ['$openFB', '$state', '$location'];


    angular
        .module('fomo.login', [
            'fomo.login.routes',
            'ngOpenFB'
        ])
        .run(FacebookLogin);


})();