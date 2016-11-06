/**
 * Created by gaoying on 15/9/1.
 */
campaignapp.config(function($routeProvider) {

    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : '/Restructures/campaigns/templates/login.html',
            controller  : 'campaigncontroller'
        })

        .when('/register', {
            templateUrl : '/Restructures/campaigns/templates/register.html',
            controller  : 'campaigncontroller'
        })

        .when('/sturegister', {
            templateUrl : '/Restructures/campaigns/templates/sturegister.html',
            controller  : 'campaigncontroller'
        })

        .when('/mystu', {
            templateUrl : '/Restructures/campaigns/templates/mystudents.html',
            controller  : 'campaigncontroller'
        })

        .when('/datainfo_count', {
            templateUrl : '/Restructures/campaigns/templates/datainfo.html',
            controller  : 'campaigncontroller'
        })

        .when('/datainfo_stu', {
            templateUrl : '/Restructures/campaigns/templates/datainfo_stu.html',
            controller  : 'campaigncontroller'
        })

        .when('/datainfo_reff', {
            templateUrl : '/Restructures/campaigns/templates/datainfo_reff.html',
            controller  : 'campaigncontroller'
        })

        .when('/adminlogin', {
            templateUrl : '/Restructures/campaigns/templates/admin_login.html',
            controller  : 'campaigncontroller'
        })

        .otherwise({
            redirectTo: '/'
        });
})