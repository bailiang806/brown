/**
 * Created by gaoying on 15/8/21.
 */

coachapp.config(function($routeProvider) {

    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : '/Restructures/templates/home/register.html',
            controller  : 'coachcontroller'
        })

        .when('/coachbasic', {
            templateUrl : '/Restructures/templates/coach/coachbasic.html',
            controller  : 'coachcontroller'
        })

        .otherwise({
            redirectTo: '/'
        });
})