/**
 * Created by gaoying on 15/8/27.
 */
weixinapp.controller("weixincontroller",function($scope,$http,$routeParams,$sce){

    //************业务逻辑***************************


    //------------页面初始化----------------------
    $scope.homepage={

        initpage:function(){

            //初始化轮播
            initswiper();

            //推荐人id处理
            if($.session.get("refererid")==undefined||$.session.get("refererid")==null){
                var url = location.href;
                var refererId=url.indexOf('?')==-1?"-1":url.substring(url.indexOf('?')+1,url.lastIndexOf('#'));
                $.session.set("refererid",refererId);
            }

            //通过经纬度获取城市id

            if($.session.get("location")==undefined){

                $scope.weixinlocation.getlocation();
            }

        }
    }

    //----------------驾校列表,驾校详情-----------------------

    $scope.school={

        schoolinfo:[],

        loadschoolinfo:function(){
            $http({
                url: "/drivingSchool/detailByCityId",
                method: "get",
                params: {"cityId":$.session.get("location")}
            }).success(function (response) {
                var tmpschool=[];
                if(response.result){
                    var resultlist=response.returnObject;
                    for(inum in resultlist){
                        tmpschool.push({
                            "cityId":resultlist[inum].cityId,
                            "cityName":resultlist[inum].cityName,
                            "schoolId":resultlist[inum].schoolId,
                            "schoolName":resultlist[inum].schoolName,
                            "schoolAddress":resultlist[inum].schoolAddress.length>14?resultlist[inum].schoolAddress.substr(0,14)+"...":resultlist[inum].schoolAddress,
                            "schoolLatitude":resultlist[inum].schoolLatitude,
                            "schoolLongtitude":resultlist[inum].schoolLongtitude,
                            "schoolDetail":resultlist[inum].schoolDetail,
                            "schoolImgUrl":resultlist[inum].schoolImgUrl,
                            "schoolAvator":resultlist[inum].schoolAvator,
                            "canSelect":resultlist[inum].canSelect,
                            "price":Number(resultlist[inum].price)/100
                        })
                    }

                    if(tmpschool.length>0){
                        $scope.school.schoolinfo=tmpschool;
                        $("#noschools span").html("");
                    }

                    //将所有信息放在localstory
                    var schoolstr=JSON.stringify(tmpschool);
                }else{
                }
            }).error(function () {
            });
        }
    }
    $scope.schooldetail={

        sdetail:{},
        price:1,
        schooldetail:"",
        getdetail:function(){
            var schoolid=$.session.get('selectschoolid');
            $http({
                url: "/drivingSchool/detailBySchoolId?",
                method: "get",
                params: {"schoolId":$routeParams.schoolid,"nouse":Math.random()}
            }).success(function (response) {
                if(response){
                    $scope.schooldetail.sdetail= response.returnObject;
                    $scope.schooldetail.price=Number(response.returnObject.price)/100;
                    $scope.schooldetail.schooldetail=$sce.trustAsHtml(response.returnObject. schoolDetail.replace(/\n/g,"<br/>"));
                }
            }).error(function(){

            })
        }
    }



//----------------------------教练列表,教练详情---------------------------------------
    $scope.coach={
        coachinfo:[],
        loadcoachlinfo:function(){
            $http({
                url: "/coach/getCoachDetailByCity",
                method: "get",
                params: {"cityId":$.session.get("location")}
            }).success(function (response) {
                var tmpcoach=[];
                if(response.result){
                    var resultlist=response.returnObject;
                    for(inum in resultlist){
                        tmpcoach.push({
                            "coachId":resultlist[inum].coachId,
                            "coachName":resultlist[inum].coachName,
                            "coachSex":resultlist[inum].coachSex,
                            "cityId":resultlist[inum].cityId,
                            "cityName":resultlist[inum].cityName,
                            "schoolId":resultlist[inum].schoolId,
                            "schoolName":resultlist[inum].schoolName,
                            "phoneNumber":resultlist[inum].phoneNumber,
                            "studentCount":resultlist[inum].studentCount,
                            "passingRate":resultlist[inum].passingRate,
                            "coachImgUrl":resultlist[inum].coachImgUrl,
                            "coachAvator":resultlist[inum].coachAvator
                        })
                    }
                    if(tmpcoach.length>0){
                        $scope.coach.coachinfo=tmpcoach;
                        $("#nocoaches span").html("");
                    }

                    //将所有信息放在localstory,备用...
                    var coachstr=JSON.stringify(tmpcoach);
                    sessionStorage.setItem("allcoachinfo",coachstr);
                }
            }).error(function () {
            });
        }
    }


      $scope.coachdetail={
          cdetail:{},
          getdetail:function(){
              var coachid=$.session.get('selectcoachid');
              $http({
                  url: "/coach/getCoachDetailByCoachId",
                  method: "get",
                  params: {"userId":$routeParams.coachid}
              }).success(function (response) {

                  if(response){
                  $scope.coachdetail.cdetail=response.returnObject;
              }
              }).error(function(){

              })
          }
      };

    //--------------------------场地地图----------------------------------

    $scope.map={

        initmap:function(){

            var height=$(window).height();
            $("#drivingMap").css({"margin-top":"0px","height":height});

            var cityid=$.session.get("location");

            $http({
                url: '/serviceCity/city',
                method: "get",
                params: {cityId:cityid}
            }).success(function(cityLocation){

                if(cityLocation.result){

                    $http({
                        url: '/drivingSchool/city',
                        method: "get",
                        params: {cityId:cityid}
                    }).success(function(response){

                        $.showMap({
                            content:'drivingMap',
                            zoom: 11,
                            owner: {lng: cityLocation.returnObject.longitude, lat: cityLocation.returnObject.latitude},//当前点坐标
                            //owner: {lng: 118.124687, lat: 24.520327},
                            schoolLocations: response
                        });
                    })
                }
            })
        }
    }

    //------------------------提交咨询-------------------------

    $scope.userinfo={

        name:"",
        phone:"",
        city:"2",
        verifynum:"",

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
        },

        showmodel:function(){
            $scope.userinfo.city=$.session.get("location");
            var height=$(window).height();
            $("#signinModel").css({"margin-top":height*0.15})
            $('#signinModel').modal({show:true,backdrop:'static'});
            $scope.effect.changetab(3);

        },

        signin:function(){

            if($.session.get("refererid")==null||$.session.get("refererid")==undefined||$.session.get("refererid")==""){
                var url = location.href;
                var refererId=url.indexOf('?')==-1?"-1":url.substring(url.indexOf('?')+1,url.lastIndexOf('#'));
                $.session.set("refererid",refererId);
            }

            var re = /^[0-9]+.?[0-9]*$/;
            if(this.name!=""){
                if(this.phone!=""&&(this.phone).length==11&&re.test((this.phone))){
                    $http({
                        url: "/campaign/spreadStudent/register",
                        method: "post",
                        params: {name:this.name,phone:this.phone,cityId:this.city,referrerId:$.session.get("refererid"),"inputCode":this.verifynum}
                    }).success(function (response) {
                        if(response.result){
                            $('#signinModel').modal('hide');
                            alert("提交成功，专属客服会在后续联系您！");
                        }else{
                            $('#signinModel').modal('hide');
                            alert(response.message);
                        }
                    }).error(function () {

                    })
                }else{
                    alert("请输入正确的手机号");
                }
            }else{
                alert("姓名不能为空");
            }
        },
    };

    //-------------------学员预约登陆--------------------

    $scope.login={

        phone:"",
        verifynum:"",

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
        },

        submit:function(){

            window.location="#courselist";

            var re = /^[0-9]+.?[0-9]*$/;
            if(this.phone!=""){
                if(this.phone!=""&&(this.phone).length==11&&re.test((this.phone))){
                    // 登陆验证逻辑,
                    $http({
                        url: "/dsSignupStudent/studentLoginByPhone",
                        method: "post",
                        params: {phone:this.phone,inputCode:this.verifynum}
                    }).success(function (response) {
                        if(response.result){
                           // $.session.set("studentId",response.returnObject.id);
                            window.localStorage.setItem("studentId",response.returnObject.id);
                            window.location="#courselist";
                        }else{
                            alert(response.message);
                        }
                    }).error(function () {

                    })
                }else{
                    alert("手机号不正确,请重新输入");
                }
            }else{
                alert("手机号或验证码不能为空");
            }
        }

    }

    //--------------预约信息列表-----------------------
    $scope.course={

        courselist:[],
        studentId:"",
        idempty:"",
        studyhour:"",
        studyProgress:"",

        initcourselist:function(){

            //this.studentId= $.session.get("studentId");
            this.studentId=window.localStorage.getItem("studentId");
            if(this.studentId==null||this.studentId==undefined){
                window.location="#studentlogin" ;
            }

            //initpie01();
            //initpie02();

            $http({
                url: '/dsSignupStudent/getAvailableCourse',
                method: "post",
                params:{"studentId":window.localStorage.getItem("studentId")}
            }).success(function(result){
                var resultlist=result.returnObject.dsTrainingSchedules;
                var studystatus=result.returnObject.dsSignupStudent.studyProgress;
                $scope.course.studyProgress=studystatus;

                if(studystatus=="2"){
                    $scope.course.studyhour=result.returnObject.dsSignupStudent.course2Hour;
                }else if(studystatus=="3"){
                    $scope.course.studyhour=result.returnObject.dsSignupStudent.course3Hour;
                }

                if(resultlist==null){
                    $("#nocoursedata").css({"display":""})
                }else{
                    var index=0;
                    var templist=[];
                    $.session.set("alreadyorder","N")
                    for(inum in resultlist){

                        templist.push({
                            "id":resultlist[inum].id,
                            "trainingDate":resultlist[inum].stringTrainingDate.substr(5,5),
                            "trainingTime":resultlist[inum].trainingTime,
                            "coachId":resultlist[inum].coachId,
                            "isSelected":resultlist[inum].isSelected,
                            "trainingSiteAddress":resultlist[inum].trainingSiteAddress!=null&&resultlist[inum].trainingSiteAddress.length>10?resultlist[inum].trainingSiteAddress.substring(0,10)+"...":resultlist[inum].trainingSiteAddress,
                            "coachImgUrl":resultlist[inum].coachImgUrl
                        })
                        if(resultlist[inum].isSelected==1){
                            $.session.set("alreadyorder","Y");
                        }
                    }
                    $("#nocoursedata").css({"display":"none"})
                }

                $scope.course.courselist=templist;
                          })

        },
    }

    //-------------------预约课程---------------------------

    $scope.order={
        courseid:"",

        selectcourse:function(courseid,date,time){
            //弹出预约框

            if($.session.get("alreadyorder")=='Y'){

                alert("您已预约过课程，不可再预约!");

            }else{
                var height=$(window).height();
                $("#order").css({"margin-top":height*0.15})
                $('#order').modal({show:true,backdrop:'static'});
                $("#coursedateinfo").html(" "+time+" ");
                $("#courseidtext").html(courseid);
            }
        },

        isorder:function(isorder){
            var courseid=$("#courseidtext").html();

            if(isorder=="Y"){
                $('#order').modal('hide');

                $http({
                    url: '/dsSignupStudent/studentReserveCourse',
                    method: "post",
                    params:{courseId:courseid,"studentId":window.localStorage.getItem("studentId")}
                }).success(function(response){
                    if(response.result){
                        $.session.set("alreadyorder","Y");
                        window.location="#courselist";
                    }else{
                        alert(response.message);
                    }
                })
            }else{
                $('#order').modal('hide');
            }

        },
        cancel:function(courseid,time){
           //弹出取消框cancelcourse
            var height=$(window).height();
            $("#cancelcourse").css({"margin-top":height*0.15})
            $('#cancelcourse').modal({show:true,backdrop:'static'});
            $("#cancelinfo").html(" "+time+" ");
            $("#cancelid").html(courseid);
        },

        cancelcourse:function(isorder){

            var courseid=$("#cancelid").html();
            if(isorder=="Y"){
                $('#cancelcourse').modal('hide');

                $http({
                    url: '/dsSignupStudent/deleteReservedCourse',
                    method: "post",
                    params:{courseId:courseid,"studentId":window.localStorage.getItem("studentId")}
                }).success(function(response){
                    if(response.result){
                        $.session.set("alreadyoder","N");
                        window.location="#courselist";
                    }else{
                        alert("你选的课程不可取消,如有特殊情况,请联系客服!");
                    }
                })
            }else{
                $('#cancelcourse').modal('hide');
            }
        }


    }


    //------------------------支付相关---------------

    //支付页面初始化

    $scope.pay={

        sdetail:[],
        orderid:"",
        randomnum:"",
        price:1,

        payoffinit:function(){
            //加载驾校信息
            $http({
                url: "/drivingSchool/detailBySchoolId",
                method: "get",
                params: {"schoolId":$routeParams.schoolid}
            }).success(function (response) {
                if(response){
                    $scope.pay.sdetail= response.returnObject;
                    $scope.pay.price=Number(response.returnObject.price)/100
                }
            }).error(function(){

            })

            //生成订单号

            var randomnum=Math.floor(Math.random()*1000000).toString();
            this.randomnum=randomnum;
            var today=new Date().Format("yyyy-MM-dd");
            var orderid=faultylabs.MD5(randomnum+today);
            this.orderid=orderid;


        }
    }

    //微信支付
    $scope.wxpay={

        name:"",
        phone:"",
        verifynum:"",
        orderid:"",

        payment: function (schoolid,paytype) {


            var randomnum=$("#rand").html();
            var orderid=$("#oriderid").html();

            //手机号验证码校验
            var re = /^[0-9]+.?[0-9]*$/;
            if(this.phone!=""){
                if(this.phone!=""&&(this.phone).length==11&&re.test((this.phone))){

                    $http({
                        url: '/common/verifyUserPayInfo',
                        method: "post",
                        params:{"name":$scope.wxpay.name,"phone":$scope.wxpay.phone,"verifynum":$scope.wxpay.verifynum,"orderid":orderid,"randomnum":randomnum,"schoolid":schoolid}
                    }).success(function(response){
                        if(response.result){
                            console.log("校验成功")

                            if(paytype==1){
                                window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx1e810861e20c0c30&redirect_uri=http%3a%2f%2fwww.ejiapei.com%2fwxpay%2fpayModelAndView%3forderid="+orderid+"&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";
                                //window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx66de4a61ba088c2f&redirect_uri=http%3a%2f%2ftest.ejiapei.com%2Fwxpay%2FpayModelAndView?orderid="+orderid+"&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";
                            }else if(paytype==2){
                                $http({
                                    url: '/alipay/getPayUrl?',
                                    method: "get",
                                    params:{"orderid":orderid}
                                }).success(function(result){
                                    var _url = result.message;
                                    window.location.href = _url;
                                });
                            }

                        }else{
                            alert(response.message);
                        }
                    });

                }else{
                    alert("手机号不正确,请重新输入");
                }
            }else{
                alert("手机号或验证码不能为空");
            }
        },

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
        }
    }

    //支付宝支付,待定,未用

    $scope.alipay={
        paytest:function(){

            $http({
                url: '/alipay/getPayUrl?',
                method: "get",
                params:{"coach_product_id":123456}
            }).success(function(result){
                var _url = result.message;
                window.location.href = _url;
            });

        }
    }


    //-----------------更过->选择城市,退出登陆-----------------

    $scope.choosecity={
        showmodel:function(){
            $('#CityModal').modal({show:true,backdrop:'static'});
        },
    }

    $scope.setlocation=function(cityid){
        this.location= cityid;
        $.session.set('location',this.location);
        $('#CityModal').modal('hide');
    }


    $scope.layout=function(){
        if(window.localStorage.getItem("studentId")!=undefined){
            window.localStorage.clear("studentId");
        }

        window.location="#studentlogin";
        $scope.effect.changetab(2);
    }



//**********************页面效果**********************************


    $scope.keyup= function(input) {

        keyup(input);

    }
      $scope.focus= function() {
        var $html = "</br><h5>请输入手机号和验证码</h5>";
        $("#tile").replaceWith($html);
      }
    //-----------------tab切换跳转-----------------
    $scope.gotoindex=function(){
        window.location="#";
    }

    $scope.gotoorder=function(){
        if(window.localStorage.getItem("studentId")!=null&&window.localStorage.getItem("studentId")!=undefined){
            window.location="#courselist";
        }else{
            window.location="#studentlogin"
        }
    }

    $scope.gotosingin=function(){
        //$scope.effect.changetab(3);
        //$scope.userinfo.showmodel();
        window.location="#consult";
    }

    $scope.gomore=function(){
        //$scope.effect.changetab(4);
        window.location="#domore";
    }


    $scope.changeshopinfo=function(){
            $scope.effect.changetab(4);
            $(".coorpershops").empty();
            var locationid=$.session.get("location");
            initcoorperinfo(locationid);
        }

    $scope.effect= {
        changetab: function (param) {
            changetab(param);
        },
    }


    //**********************weixintest************
    $scope.weixinlocation={

       getlocation:function(){

           if(isWeiXin()){

               var nouse=Math.floor(Math.random()*100000);
               $http({
                   url: '/wxaccess/JSJDKCfg',
                   method: "get",
                   params:{"nouse":nouse}
               }).success(function(result){

                   if(result){

                       wx.config({
                           debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                           appId : result.appId,      //公众号名称，由商户传入
                           timestamp: result.timestamp,    //时间戳，自1970年以来的秒数
                           nonceStr : result.nonceStr,     //随机串
                           signature:result.signature,// 必填，签名，见附录1
                           jsApiList: ['getLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                       });

                       wx.ready(function () {
                           wx.getLocation({
                               success: function (res) {
                                   var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                                   var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                                   var speed = res.speed; // 速度，以米/每秒计
                                   var accuracy = res.accuracy; // 位置精度

                                   sessionStorage.setItem("latitude",latitude);
                                   sessionStorage.setItem("longitude",longitude);
                                   sessionStorage.setItem("speed",speed);
                                   sessionStorage.setItem("accuracy",accuracy);

                                   $http({
                                       url: "/map/getCity",
                                       method: "get",
                                       params: {"lag":latitude,"lng":longitude}
                                   }).success(function (response) {
                                       if(response.result){
                                           $.session.set("location",response.returnObject.id);
                                       }else{
                                           alert("抱歉,获取地理位置信息失败,需要您手动选择城市!");
                                       }
                                   }).error(function () {
                                   });

                               }
                           });
                       });
                   }
               });

           }else{
                var cityname=remote_ip_info.city;
                var cityid=getLocationId(cityname);
                $.session.set("location",cityid);
           }
       }
    }





});