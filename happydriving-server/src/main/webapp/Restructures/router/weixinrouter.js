/**
 * Created by gaoying on 15/8/21.
 */
weixinapp.config(function($routeProvider) {

    //$locationProvider.html5Mode(true);

    $routeProvider

        // route for the home page
        .when('/', {
            templateUrl : '/Restructures/templates/weixin/index.html',
            controller  : 'weixincontroller'
        })

        .when('/schoollist', {
            templateUrl : '/Restructures/templates/weixin/schoollist.html',
            controller  : 'weixincontroller'
        })

        .when('/schooldetail/:schoolid', {
            templateUrl : '/Restructures/templates/weixin/schooldetail.html',
            controller  : 'weixincontroller'
        })

         .when('/studentpayoff/:schoolid', {
                    templateUrl : '/Restructures/templates/weixin/studentpayoff.html',
                    controller  : 'weixincontroller'
                })
//------------------------����֧��ҳ��-------------------------------//
        .when('/coachlist', {
            templateUrl : '/Restructures/templates/weixin/coachlist.html',
            controller  : 'weixincontroller'
        })

        .when('/coachdetail/:coachid', {
            templateUrl : '/Restructures/templates/weixin/coachdetail.html',
            controller  : 'weixincontroller'
        })

        .when('/map', {
            templateUrl : '/Restructures/templates/weixin/map.html',
            controller  : 'weixincontroller'
        })

        .when('/aboutus', {
            templateUrl : '/Restructures/templates/weixin/aboutus.html',
            controller  : 'weixincontroller'
        })

        .when('/coorperation', {
            templateUrl : '/Restructures/templates/weixin/coorperation.html',
            controller  : 'weixincontroller'
        })

        .when('/trainingfee', {
            templateUrl : '/Restructures/templates/weixin/trainingfee.html',
            controller  : 'weixincontroller'
        })

        .when('/share', {
            templateUrl : '/Restructures/templates/weixin/share.html',
            controller  : 'weixincontroller'
        })

        .when('/sliderinto01', {
            templateUrl : '/Restructures/templates/weixin/sliderinto01.html',
            controller  : 'weixincontroller'
        })
        .when('/sliderinto02', {
            templateUrl : '/Restructures/templates/weixin/sliderinto02.html',
            controller  : 'weixincontroller'
        })
        .when('/sliderinto03', {
            templateUrl : '/Restructures/templates/weixin/sliderinto03.html',
            controller  : 'weixincontroller'
        })

        .when('/studentlogin', {
            templateUrl : '/Restructures/templates/weixin/studentlogin.html',
            controller  : 'weixincontroller'
        })
        .when('/courselist', {
            templateUrl : '/Restructures/templates/weixin/courselist.html',
            controller  : 'weixincontroller'
        })
        .when('/domore', {
            templateUrl : '/Restructures/templates/weixin/domore.html',
            controller  : 'weixincontroller'
        })
        .when('/coorpershops', {
            templateUrl : '/Restructures/templates/weixin/coorpershops.html',
            controller  : 'weixincontroller'
        })

        .when('/choosepaytype', {
            templateUrl : '/Restructures/templates/weixin/choosepaytype.html',
            controller  : 'weixincontroller'
        })

        .when('/course_introduct', {
            templateUrl : '/Restructures/templates/weixin/courseintroduct.html',
            controller  : 'weixincontroller'
        })

        .when('/consult', {
            templateUrl : '/Restructures/templates/weixin/consult.html',
            controller  : 'weixincontroller'
        })

        .otherwise({
            redirectTo: '/'
        });
})