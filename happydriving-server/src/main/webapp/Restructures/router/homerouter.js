/**
 * Created by gaoying on 15/8/21.
 */
homeapp.config(function($routeProvider) {

    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : '/Restructures/templates/home/index.html',
            controller  : 'homecontroller'
        })

        .when('/product', {
            templateUrl : '/Restructures/templates/home/product.html',
            controller  : 'homecontroller'
        })
        .when('/fuzhoumap', {
            templateUrl : '/Restructures/templates/home/fuzhoumap.html',
            controller  : 'homecontroller'
        })

        .when('/aboutus', {
            templateUrl : '/Restructures/templates/home/aboutus.html',
            controller  : 'homecontroller'
        })

        .when('/coorperation', {
            templateUrl : '/Restructures/templates/home/coorperation.html',
            controller  : 'homecontroller'
        })

        .when('/trainingfee', {
            templateUrl : '/Restructures/templates/home/trainingfee.html',
            controller  : 'homecontroller'
        })

        .when('/legal', {
            templateUrl : '/Restructures/templates/home/legal.html',
            controller  : 'homecontroller'
        })

        .when('/schooldetail/:schoolid', {
            templateUrl : '/Restructures/templates/home/schooldetail.html',
            controller  : 'homecontroller'
        })

        .when('/index2', {
            templateUrl : '/Restructures/templates/home/index2.html',
            controller  : 'homecontroller'
        })

        .when('/product2', {
            templateUrl : '/Restructures/templates/home/product2.html',
            controller  : 'homecontroller'
        })

        .when('/sliderinto01', {
            templateUrl : '/Restructures/templates/weixin/sliderinto01.html',
            controller  : 'homecontroller'
        })
        .when('/sliderinto02', {
            templateUrl : '/Restructures/templates/weixin/sliderinto02.html',
            controller  : 'homecontroller'
        })
        .when('/sliderinto03', {
            templateUrl : '/Restructures/templates/weixin/sliderinto03.html',
            controller  : 'homecontroller'
        })

        .otherwise({
            redirectTo: '/'
        });
})