(() => {

    class LoginController {

        constructor($openFB, $state, RedirectState, $window) {
            this.$openFB = $openFB;
            this.$state = $state;
            this.RedirectState = RedirectState;
            this.window = $window;
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
            this.window.localStorage.fbAuth = user.authResponse.token;
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
        '$window'
    ];

    angular
        .module('fomo.login.LoginController', ['ngCookies'])
        .controller('LoginController', LoginController)

})();