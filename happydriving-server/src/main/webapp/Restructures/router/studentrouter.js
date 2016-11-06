/**
 * Created by gaoying on 15/8/21.
 */
studentapp.config(function($routeProvider) {

    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : '/Restructures/templates/home/register.html',
            controller  : 'studentcontroller'
        })

        .when('/studentbasic', {
            templateUrl : '/Restructures/templates/student/studentbasic.html',
            controller  : 'studentcontroller'
        })

        .otherwise({
            redirectTo: '/'
        });
})