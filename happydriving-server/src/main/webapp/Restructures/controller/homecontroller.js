/**
 * Created by gaoying on 15/8/21.
 */
homeapp.controller("homecontroller",function($sce,$scope,$http,$routeParams){

//------------提交咨询-------------------
    $scope.index_data={
        nearestlist:[],
        majorlist:[],

        getnearest:function(){
            $http({
                url: "/drivingSchool/getNearestSchoolByCity",
                method: "get",
                params: {"cityId":$.session.get("cityid")}
            }).success(function (response) {
                var tmpschool=[];
                if(response){
                    var resultlist=response.returnObject;
                    for(inum in resultlist){
                        tmpschool.push({
                            "schoolId":resultlist[inum].schoolId,
                            "schoolName":resultlist[inum].schoolName,
                            "schoolAddress":resultlist[inum].schoolAddress.length>8?resultlist[inum].schoolAddress.substr(0,8):resultlist[inum].schoolAddress,
                            "imgURL":resultlist[inum].imgURL,
                        })
                    }
                    $scope.index_data.nearestlist=tmpschool;
                }
            })
        },

        getmajor:function(){
            $http({
                url: "/drivingSchool/getMajorSchool",
                method: "get",
                params: {"cityId":$.session.get("cityid")}
            }).success(function (response) {
                var tmpschool=[];
                if(response){
                    var resultlist=response.returnObject;
                    for(inum in resultlist){
                        tmpschool.push({
                            "schoolId":resultlist[inum].schoolId,
                            "schoolName":resultlist[inum].schoolName,
                            "schoolAddress":resultlist[inum].schoolAddress.length>8?resultlist[inum].schoolAddress.substr(0,8):resultlist[inum].schoolAddress,
                            "imgURL":resultlist[inum].imgURL,

                        })
                    }
                    $scope.index_data.majorlist=tmpschool;
                }
            })

        }
    },

    $scope.city={

        setcurrentcity:function(){

            if(localStorage.getItem("cityid")==undefined||localStorage.getItem("cityid")==null){

                var cityname=remote_ip_info.city;

                if(cityname=="上海市"||cityname=="上海"){
                    $.session.set("cityid",4);
                    localStorage.setItem("cityid",4)
                    $scope.user.city= $.session.get("cityid");

                }else if(cityname=="厦门市"||cityname=="厦门"){
                    $.session.set("cityid",2);
                    localStorage.setItem("cityid",2);
                    $scope.user.city= $.session.get("cityid");

                }else if(cityname=="福州市"||cityname=="福州"){
                    $.session.set("cityid",1);
                    localStorage.setItem("cityid",1);
                    $scope.user.city= $.session.get("cityid");

                }else{
                    $.session.set("cityid",4);
                    localStorage.setItem("cityid",4)
                    $scope.user.city= $.session.get("cityid");
                }
            }else{
                $.session.set("cityid",localStorage.getItem("cityid"));
            }
            $scope.index_effect.changeshaopinfo( $.session.get("cityid"));

            //渲染驾校列表
            $scope.index_data.getnearest();
            $scope.index_data.getmajor();

        }
    }

    $scope.user={
        name:"",
        phone:"",
        city:"4",
        verifynum:"",

        sendmessage:function(){
            $.sendMessage({
                button: $(this),
                tel: this.phone,
            });
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
                            alert("提交成功，专属客服会在后续联系您！");
                            $('#signinModel').modal('hide');

                        }else{
                            alert(response.message);
                            $('#signinModel').modal('hide');
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

        showmodel:function(){
            var height=$(window).height();
            $("#signinModel").css({"margin-top":height*0.15})
            $('#signinModel').modal({show:true,backdrop:'static'});
        }
    };

    $scope.setrefferdid=function(){

        //推荐人id
        if($.session.get("refererid")==undefined||$.session.get("refererid")==null){
            var url = location.href;
            var refererId=url.indexOf('?')==-1?"-1":url.substring(url.indexOf('?')+1,url.lastIndexOf('#'));
            $.session.set("refererid",refererId);
        };

        var swiper = new Swiper('.swiper-container', {
            pagination: '.swiper-pagination',
            paginationClickable: true,
            spaceBetween:0,
            centeredSlides: true,
            autoplay: 8000,
            loop : true,
            autoplayDisableOnInteraction: false

        });
    };

//------------------场地地图-----------------------------
    $scope.map={

        //设置btn的选中状态
        btnid:"fuzhou",
        cityId:"2",

        loading:function(){
            loadinggif();
        },

        initmap:function(){

            if($.session.get("cityid")!=undefined){
                this.cityId=$.session.get("cityid");
            }

            $(".citycanclick").css({"background-color":"#f5f3f0","color":"#575757"});
            $('#city'+$scope.map.cityId).css({"background-color":"#3d516b","color":"white"})

            $.session.set("cityid",$scope.map.cityId);

            $http({
                url: '/serviceCity/city',
                method: "get",
                params: {cityId:$scope.map.cityId}
            }).success(function(cityLocation){
                $http({
                    url: '/drivingSchool/city',
                    method: "get",
                    params: {cityId:$scope.map.cityId}
                }).success(function(response){
                    $.showMap({
                        content:'drivingMap',
                        zoom: 12,
                        owner: {lng: cityLocation.returnObject.longitude, lat: cityLocation.returnObject.latitude},//当前点坐标
                        //owner: {lng: 118.124687, lat: 24.520327},
                        schoolLocations: response
                    });
                    //渲染驾校列表
                    $scope.school.loadschoolinfo($.session.get("cityid"));
                })
            })


        },

        switch:function(path){
            if(path==""||path==undefined||path==null){
               path="1";
            }
            $.session.set("cityid",path);
            $scope.map.cityId=path;
            $scope.map.initmap();

        }
    };


//--------------------驾校列表及详情-----------------------------

    $scope.school={

        schoolinfo:[],

        loadschoolinfo:function(cityid){

            $http({
                url: "/drivingSchool/detailByCityId",
                method: "get",
                params: {"cityId":cityid}
            }).success(function (response) {
                var tmpschool=[];
                if(response){
                    var resultlist=response.returnObject;
                    for(inum in resultlist){
                        tmpschool.push({
                            "cityId":resultlist[inum].cityId,
                            "cityName":resultlist[inum].cityName,
                            "schoolId":resultlist[inum].schoolId,
                            "schoolName":resultlist[inum].schoolName,
                            "schoolAddress":resultlist[inum].schoolAddress.length>12?"地址:"+resultlist[inum].schoolAddress.substr(0,10)+"...":"地址:"+resultlist[inum].schoolAddress,
                            "schoolLatitude":resultlist[inum].schoolLatitude,
                            "schoolLongtitude":resultlist[inum].schoolLongtitude,
                            "schoolDetail":resultlist[inum].schoolDetail,
                            "schoolImgUrl":resultlist[inum].schoolImgUrl,
                            "schoolAvator":resultlist[inum].schoolAvator,
                            "summary":"驾校简介:"+resultlist[inum].schoolDetail.substr(0,30)+"......"
                        })
                    }
                    $scope.school.schoolinfo=tmpschool;
                    //将所有信息放在localstory
                    var schoolstr=JSON.stringify(tmpschool);
                    sessionStorage.setItem("allschoolinfo",schoolstr);

                    $("body").hideLoading();
                }else{
                    $("body").hideLoading();
                }
            })
        }
    };

    $scope.schooldetail={

        sdetail:{},
        schooldetail:"",

        getdetail:function(){
            var schoolid=$.session.get('selectschoolid');
            $http({
                url: "/drivingSchool/detailBySchoolId",
                method: "get",
                params: {"schoolId":$routeParams.schoolid}
            }).success(function (response) {
                if(response){
                    $scope.schooldetail.sdetail= response.returnObject;
                    $scope.schooldetail.schooldetail=$sce.trustAsHtml(response.returnObject.schoolDetail.replace(/\n/g,"<br/>"));

                }
            }).error(function(){

            })
        }
    };



    //---------------------------js实现一些动画效果---------------------------------

    $scope.index_effect={

        shade:function(){
            initswiper();
            initshadow();
            coorpershops("xiamen");
        },

        mouseenter:function(param){
            mouseenter(param);
        },

        mouseleave:function(param){
            mouseleave(param);
        },

        changeshaopinfo:function(param){
            coorpershops(param);
        }
    }
})
