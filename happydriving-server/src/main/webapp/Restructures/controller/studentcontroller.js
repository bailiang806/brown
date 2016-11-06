/**
 * Created by gaoying on 15/8/21.
 */
studentapp.controller("studentcontroller",function($scope,$http){
    $scope.message = 'Everyone come and see how good I look!';
    $scope.login = {
        phonenumber: "",
        massage: "",
        registermes: "注册",
        sendmessage: function () {
            $.sendMessage({
                button: $(this),
                tel: this.phonenumber,
            });
        },

        register: function () {
            window.location.href="#studentbasic";
            //var drtel = this.phonenumber;
            //var drcheck = this.massage;
            //
            //if (drtel !== '') {
            //    if (drcheck !== '') {
            //        this.registermes = "正在注册......"
            //        $http({
            //            url: "/user/doRegisterCoach",
            //            method: "post",
            //            params: {phone: drtel, inputcode: drcheck}
            //        }).success(function (response) {
            //            if (response.result) {
            //                //跳转页面
            //                window.location.href=path;
            //            }else{
            //                alert("此手机号已被注册,请直接登陆");
            //            }
            //        }).error(function () {
            //            this.registermes = "确定";
            //            alert('注册失败，请重新注册!');
            //        })
            //    } else {
            //        alert("请输入验证码");
            //    }
            //} else {
            //    if (drcheck !== '') {
            //        alert("请输入手机号");
            //    } else {
            //        alert("请输入手机号和验证码");
            //    }
            //}
        }
    }

    $scope.studentinfo={

        sname:"",
        saddress:"",
        province:"",
        city:"",
        area:"",
        town:"",

        submit:function(){

        },

        initcity:function(){
            $('#element_id').cxSelect({
                url: '/Restructures/json/cityData.json',   // 提示：如果服务器不支持 .json 类型文件，请将文件改为 .js 文件
                selects: ['province', 'city', 'area','town'],
            });
        }

    }


})