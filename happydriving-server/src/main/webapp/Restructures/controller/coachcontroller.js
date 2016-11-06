/**
 * Created by gaoying on 15/8/21.
 */
coachapp.controller("coachcontroller",function($scope,$http) {

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
            window.location.href="#coachbasic";
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

    $scope.classtype = {

        selectedclass:"",
        classinfo:[],

        getclasstype:function(){
            var array=[];
            $http({
                url: "/classtype/all",
                method:"get"
            })
                .success(function (response) {
              if(response!=null&&response.returnObject!=undefined){
                  var retdata=response.returnObject;
                  for(inum in retdata){
                      $scope.classtype.classinfo.push({id:retdata[inum].id,classtype:retdata[inum].classType})
                  }
              }
            }).error(function () {
         })

        }
    }

    $scope.cartype = {

        selectedcar:"",
        carinfo:[],

        getcartype:function(){
            var array=[];
            $http({
                url: "/cartype/all",
                method:"get"
            })
                .success(function (response) {
                    if(response!=null&&response.returnObject!=undefined){
                        var retdata=response.returnObject;
                        for(inum in retdata){
                            $scope.cartype.carinfo.push({id:retdata[inum].id,cartype:retdata[inum].carType})
                        }
                    }
                }).error(function () {
                })

        }
    }


});

