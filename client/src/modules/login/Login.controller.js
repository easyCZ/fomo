(() => {

    class LoginController {

        constructor($openFB, $state, RedirectState, $cookieStore) {
            this.$openFB = $openFB;
            this.$state = $state;
            this.RedirectState = RedirectState;
            this.cookies = $cookieStore;
        }

        login() {
            this.$openFB.login({
                scope: 'email,user_friends'
            }).then(
                (success) => this.onLoginSuccess(success),
                (error) => this.onLoginFailure(error)
            );
        }

        onLoginSuccess(user) {
            console.log('Logged into FB', user);
            this.cookies.put('fb-auth', user.authResponse.token);
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
        '$cookieStore'
    ];

    angular
        .module('fomo.login.LoginController', ['ngCookies'])
        .controller('LoginController', LoginController)

})();