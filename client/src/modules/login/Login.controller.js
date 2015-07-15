(() => {

    class LoginController {

        constructor($openFB, $state, RedirectState, $cookies) {
            this.$openFB = $openFB;
            this.$state = $state;
            this.RedirectState = RedirectState;
            this.cookies = $cookies;
        }

        login() {
            this.$openFB.login({
                // I think we'll want offline_access for a proper native app (gives us a long-lived token)
                scope: 'email,user_friends,read_custom_friendlists'
            }).then(
                (success) => this.onLoginSuccess(success),
                (error) => this.onLoginFailure(error)
            );
        }

        onLoginSuccess(user) {
            console.log('Logged into FB', user);

            this.cookies.fbAuth = user.authResponse.token;
            return this.RedirectState
                ? this.$state.go(this.RedirectState)
                : this.$state.go('events.list');
        }

        onLoginFailure(error) {
            console.error('Failed to login to FB', error);
        }

    }

    LoginController.$inject = [
        '$openFB',
        '$state',
        'RedirectState',
        '$cookies'
    ];

    angular
        .module('fomo.login.LoginController', ['ngCookies'])
        .controller('LoginController', LoginController)

})();