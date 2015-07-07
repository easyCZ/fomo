(() => {

    class LoginController {

        constructor($openFB) {
            this.$openFB = $openFB;
        }

        login() {
            this.$openFB.login({
                scope: 'email,user_friends'
            }).then((response) => {
                console.log('response', response);
                if (response.status === 'connected') {
                    console.log('Facebook login succeeded');
                    // $scope.closeLogin();
                } else {
                    // alert('Facebook login failed');
                }
            });
        }

    }
    LoginController.$inject = ['$openFB']

    angular
        .module('fomo.login.LoginController', [])
        .controller('LoginController', LoginController)

})();