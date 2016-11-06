/**
 * Created by gaoying on 15/9/1.
 */
campaignapp.controller("campaigncontroller",function($scope,$http){

    $scope.user={
        name:"",
        phone:"",
        city:"2",
        verifynum:"",
        message:"发送验证码",

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
        },

        login:function(){
           //加一些验证
            var re = /^[0-9]+.?[0-9]*$/;

                if(this.phone!=""&&(this.phone).length==11&&re.test((this.phone))){
                    $http({
                        url: "/campaign/spreadReferrer/login",
                        method: "post",
                        params: {phone:this.phone,inputCode:this.verifynum}
                    }).success(function (response) {
                        if(response.result){
                            //将userid放到session
                            $.session.set('userid',response.returnObject.id);
                            window.location="#mystu";
                        }else{
                            alert(response.message);
                        }
                    }).error(function(){

                    })
                }else{
                    alert("请输入正确的手机号");
                }

        },

        register:function(){

            var re = /^[0-9]+.?[0-9]*$/;
            if(this.name!=""){
                if(this.phone!=""&&(this.phone).length==11&&re.test((this.phone))){
                    $http({
                        url: "/campaign/spreadReferrer/register",
                        method: "post",
                        params: {"phone":this.phone,"name":this.name,"cityId":this.city,"inputCode":this.verifynum}
                    }).success(function (response) {
                        if(response.result){
                            $.session.set('userid',response.returnObject.id);
                            window.location="#mystu";
                        }else{
                            alert(response.message);
                        }
                    }).error(function(){
                       alter("error")
                    })
                }else{
                    alert("请输入正确的手机号");
                }
            }else{
                alert("姓名不能为空");
            }
        }


    };

    $scope.mystu={

        mystulist:[],
        reffid:"",

        initmystu:function(){


            $("body").showLoading();

            var userid= $.session.get("userid");
            //判断是否由登陆注册过来
            if(userid==undefined||userid==null||userid==""){
              window.location.href="#login";
            }

            $scope.mystu.reffid=userid;

            $http({
                url: "/campaign/spreadReferrer/mySpreadStudents",
                method: "post",
                cache:false,
                params: {"referrerId":userid}
            }).success(function (response) {
                if(response.result){
                    var returndata=response.returnObject;
                    for(num in returndata){
                        $scope.mystu.mystulist.push({

                            "id":returndata[num].id,
                            "phone":returndata[num].phone,
                            "name":returndata[num].name,
                            "isTransfer":returndata[num].isTransfer==true?"是":"否",
                            "createdAt":new Date(parseInt(returndata[num].createdAt)).Format("yyyy-MM-dd")
                        });
                    }


                }else{

                }
            }).error(function(){

            });


            //渲染二维码

            $http({
                url: "/campaign/spreadReferrer/manualTryDownloadGenerateCode",
                method: "get",
                params: {"referrerId":userid,"mandatory":false}
            }).success(function (response) {
                if(response.result){
                    $("#generateCode_box").empty();
                    $("#generateCode_box").html("<img class='img-responsive' src='http://www.ejiapei.com/images/generateCode/"+userid+".jpg'/>");
                    $("body").hideLoading();
                }else{
                    $("#generateCode_box").empty();
                    $("#generateCode_box").html("<p>"+response.message+"</p>");
                    $("body").hideLoading();
                }
            }).error(function(){

            })
        }
    };

    $scope.stu={

        stulist:[],

        getallstu:function(){
            $http({
                url: "/campaign/spreadStudent/getAllStudentDetails",
                method: "get",
            }).success(function (response) {
                if(response.result){
                    var returndata=response.returnObject;
                    var index=1;
                    for(num in returndata){
                        $scope.stu.stulist.push({
                            "studentId":returndata[num].studentId,
                            "studentName":returndata[num].studentName,
                            "referrerId":returndata[num].referrerId,
                            "referrerName":returndata[num].referrerName,
                            "transfer":returndata[num].transfer==true?"是":"否",
                            "createdAt":new Date(parseInt(returndata[num].createdAt)).Format("yyyy-MM-dd"),
                            "studentComment":returndata[num].studentComment,
                            "index":index++
                        });
                    }
                }else{

                }
            }).error(function(){

            })

        },


    };


    $scope.update_generateCode=function(param){

       var refferedid=param;

            $http({
                url: "/campaign/spreadReferrer/manualTryDownloadGenerateCode",
                method: "get",
                params: {"referrerId":refferedid,"mandatory":true}
            }).success(function (response) {
                if(response.result){
                    $("#generateCode_box").empty();
                    $("#generateCode_box").html("<img class='img-responsive' src='http://www.ejiapei.com/images/generateCode/"+refferedid+".jpg'/>");
                }else{
                    $("#generateCode_box").empty();
                    $("#generateCode_box").html("<p>"+response.message+"</p>");
                }
            }).error(function(){

            });
    };



    $scope.admin={

        phone:"",
        verifynum:"",

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
        },


        login:function(){

            $http({
                url: "/campaign/spreadAdmin/login",
                method: "get",
                params: {"phone":$scope.admin.phone,"inputCode":$scope.admin.verifynum}
            }).success(function (response) {
                if(response.result){
                   $.session.set("adminid",$scope.admin.phone) ;
                   window.location="#datainfo_count"
                }else{
                   alert(response.message);
                }
            }).error(function(){

            })
        }
    }

    $scope.manage={

        stulist:[],
        refflist:[],
        citydatalist:[],
        city:"1",
        studentid:"",
        istansform:"否",
        comment:"",



        stutotal:function(){
            //判断是否由登陆而来

            if($.session.get("adminid")==undefined||$.session.get("adminid")==null){
                window.location="#adminlogin"
            }

            $http({
                url: "/campaign/spreadAdmin/getStudentsByCity",
                method: "post",
                params: {"cityId":$scope.manage.city}
            }).success(function (response) {
                $scope.manage.stulist=[];
                if(response.result){
                    var returndata=response.returnObject;
                    var index=1;
                    for(num in returndata){
                        $scope.manage.stulist.push({
                            "studentId":returndata[num].studentId,
                            "studentName":returndata[num].studentName,
                            "studentPhone":returndata[num].studentPhone,
                            "createAtString":returndata[num].createAtString,
                            "referrerId":returndata[num].referrerId,
                            "referrerName":returndata[num].referrerName,
                            "transferAtString":returndata[num].transferAtString,
                            "transfer":returndata[num].transfer==true?"是":"否",
                            "createdAt":new Date(parseInt(returndata[num].createdAt)).Format("yyyy-MM-dd"),
                            "studentComment":returndata[num].studentComment,
                            "index":index++
                        });
                    }
                }else{

                }
            }).error(function(){

            })
        },

        stu_swichcity:function(){
            $("#studata tr").remove();
            $scope.manage.stutotal();
        },



        refftotal:function(){


            if($.session.get("adminid")==undefined||$.session.get("adminid")==null){
                window.location="#adminlogin"
            }

            $http({
                url: "/campaign/spreadAdmin/getReferrerStatisticByCity",
                method: "get",
                params: {"cityId":$scope.manage.city}
            }).success(function (response) {
                $scope.manage.refflist=[];
                if(response.result){
                    var returndata=response.returnObject;
                    var index=1;
                    for(num in returndata){
                        $scope.manage.refflist.push({
                            "referrerId":returndata[num].referrerId,
                            "referrerName":returndata[num].referrerName,
                            "referrerPhone":returndata[num].referrerPhone,
                            "totalReferrerCount":returndata[num].totalReferrerCount,
                            "totalTransferCount":returndata[num].totalTransferCount,
                            "createAtString":returndata[num].createAtString,
                            "transferRate":returndata[num].transferRate,
                            "dailyAverageTransferCount":returndata[num].dailyAverageTransferCount,
                            "dailyAverageReferrerCount":returndata[num].dailyAverageReferrerCount,
                            //"createdAt":new Date(parseInt(returndata[num].createdAt)).Format("yyyy-MM-dd"),
                            "index":index++
                        });
                    }
                }else{

                }
            }).error(function(){

            });

        },

        reff_swichcity:function(){
            $("#reffdata tr").remove();
            $scope.manage.refftotal();
        },


        updatestatusmodel:function(stuid,transfer,comment){
            //弹窗
            $.session.set("updateid",stuid);
            $('#updatestu').modal({show:true});
            //transfer=="是"?$("input[type=radio][name=transform][value=是]").attr("checked","checked"):$("input[type=radio][name=transform][value=否]").attr("checked","checked");
            $("#comment").val(comment);
        },

        updatestatus:function(){

            $('#updatestu').modal('hide');

            $http({
                url: "/campaign/spreadAdmin/updateStudentStatus",
                method: "post",
                params: {"studentId": $.session.get("updateid"),"comment":$scope.manage.comment,"isTransfer":$scope.manage.istansform=="是"?true:false}
            }).success(function (response) {
                if(response.result){
                    window.location.reload();
                }else{
                    alert("更新失败,请重新操作!")
                }
            }).error(function(){
            })
        },


        citytotal:function(){

            $http({
                url: "/campaign/spreadAdmin/getCityStatisticReferrerCount",
                method: "get",
            }).success(function (response) {
                $scope.manage.citydatalist=[];
                if(response.result){
                    var returndata=response.returnObject;
                    var index=1;
                    for(num in returndata){
                        $scope.manage.citydatalist.push({
                            "cityId":returndata[num].cityId,
                            "cityName":returndata[num].cityName,
                            "todayTransferCount":returndata[num].todayTransferCount,
                            "yesterdayTransferCount":returndata[num].yesterdayTransferCount,
                            "transferRate":returndata[num].transferRate,
                            "transferStudentCount":returndata[num].transferStudentCount,
                            "totalStudentCount":returndata[num].totalStudentCount,
                            "referrerCount":returndata[num].referrerCount,
                            "index":index++
                        });
                    }
                }else{
                }
            }).error(function(){

            });

        },

    }

});