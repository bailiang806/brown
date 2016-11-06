/**
 * Created by gaoying on 15/10/26.
 */
function keyup(input){
    var in_lenup=$("#inputup").val().length;
    if(in_lenup>=11){
        $("#btn1").css("background","#8FC320");
        $("#inputup").css("color","#8ABFC7");

    }
    else{
        $("#btn1").css("background","#E1EFF0");
        $("#inputup").css("color","");
    }
    var in_lendown=$("#inputdown").val().length;
    if(in_lendown>=6){
        $("#btn2").css("background","#8FC320");
        $("#inputdown").css("color","#8ABFC7")
    }
    else{
        $("#btn2").css("background","#E1EFF0");
        $("#inputdown").css("color","")
    }
}

function initcoorperinfo(locationid){
    if(locationid=='1'){
        $(".coorpershops").html(
            "<p>e驾陪乐购店<br>地址：浦上大道中茵加洲花城五号楼一号店<br>" +
            "门店电话：0591-83055351 吴经理</p> " +

            "<p>e驾陪马尾店<br>" +
            "地址：马尾沿山市场永辉超市旁星发路83号<br>" +
            " 门店电话：0591-88264518 谢经理</p>" +

            "<p>e驾陪贵安店<br>" +
            " 地址：潘渡乡，商专学校里面<br>" +
            " 门店电话：18120909121 王经理</p>"+

            "<p>e驾陪马尾亭江店<br>" +
            " 地址：马尾亭头福建对外经济贸易职业技术学院门口对面二楼<br>" +
            " 门店电话：15659992258 谢经理</p>"+

            " <p>e驾陪柳桥店<br>" +
            "地址：鼓楼区杨桥中路255-4号柳桥公交站后<br>" +
            "门店电话：0591-87667971 李经理</p>" +

            "<p> e驾陪晋安店<br>" +
            "地址：福飞南路149号-23（灰炉头公交站后面<br>" +
            "六意超市旁边）<br>" +
            "门店电话：0591-87900313 陈经理</p>" +

            " <p> e驾陪新师大店<br>" +
            "地址：闽侯县大学城师大西门正对面二楼一号门店<br>" +
            "门店电话：18659300064 巫经理</p>"
        );
    }else if(locationid=='2'){
        $(".coorpershops").html(
            "<p>e驾陪集美大学城店<br>地址：集美区孙板南路英埭头公交站<br>" +
            "门店电话：15359281961 吴经理</p> "+

            " <p>e驾陪枋湖店<br>" +
            "地址：湖里区枋湖车管所C座办证大厅斜对面<br>" +
            "门店电话：15359281961 吴经理</p>"
        );
    }else if(locationid=='4'){
        $(".coorpershops").html(
            "<p>e驾陪佘山店<br>" +
            "地址：松江区佘山旅游度假区林湖路<br>" +
            "门店电话：15359281961 吴经理</p> " +

            "<p>e驾陪张江店<br>" +
            "地址：浦东新区张江镇江东路<br>" +
            "门店电话：15359281961 吴经理</p>" +

            "<p>e驾陪三甲港店<br>" +
            "地址：浦东新区华夏东路<br>" +
            "门店电话：15359281961 吴经理</p>"+

            "<p>e驾陪灵石店<br>" +
            "地址：闸北区广中西路<br>" +
            "门店电话：15359281961 吴经理</p>"+


            "<p>e驾陪西站店<br>" +
            "地址：普陀区桃浦路<br>" +
            "门店电话：15359281961 吴经理</p>" +

            "<p>e驾陪虹桥店<br>" +
            "地址：闵行区申虹路<br>" +
            "门店电话：15359281961 吴经理</p>" +

            "<p>e驾陪龙华店<br>" +
            "地址：徐汇区丰谷路<br>" +
            "门店电话：15359281961 吴经理</p>"+

            "<p>e驾陪华理店<br>" +
            "地址：徐汇区沪闵路<br>" +
            "门店电话：15359281961 吴经理</p>"
        );

    }else{
        $(".coorpershops").css({"margin-top":"70px","text-align":"center"})
        $(".coorpershops").html(
            "<p>抱歉,您所在的城市没有合作门店信息!</p>"
        )
    }
}

function changetab(param){
    $(".tabbtn img").each(
        function (index) {
            $(this).attr("src", "/Restructures/imgs/weixinimgs/icon0" + (index + 1) + "_black.jpg");
        }
    );
    $(".tabbtn span").each(
        function (index) {
            $(this).css({"color": "#515357"});
        }
    )
    $("#tabbtn" + param + " img").attr("src", "/Restructures/imgs/weixinimgs/icon0" + param + "_blue.jpg");
    $("#tabbtn" + param + " span").css({"color": "#00e2ff"})
}

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

function getLocationId(cityname){

    if(cityname=="上海市"||cityname=="上海"){
        return 4;

    }else if(cityname=="厦门市"||cityname=="厦门"){
        return 2

    }else if(cityname=="福州市"||cityname=="福州"){
       return 1

    }else{
       return 4
    }
}