(() => {

    let LoginRoutes = ($stateProvider) => {

        $stateProvider

            .state('login', {
                url: '/login',
                templateUrl: 'modules/login/Login.html',
                controller: 'LoginController as loginCtrl',
                resolve: {
                    RedirectState: ['$stateParams', ($stateParams) => {
                        return $stateParams.redirect;
                    }]
                }
            });

    };
    LoginRoutes.$inject = ['$stateProvider'];

    angular
        .module('fomo.login.routes', [
            'ionic',
            'fomo.login.LoginController',
            'ngCookies'
        ])
        .config(LoginRoutes);
})();