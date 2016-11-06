/**
 * Created by gaoying on 15/10/26.
 */

function coorpershops(param){
    if(param==2){
        $("#xiamenbtn").css({"background-color":"#01e2ff"});

        $("#fuzhoubtn").css({"background-color":"#161d26"});
        $("#shanghai").css({"background-color":"#161d26"});


        $(".shopinfo-left").empty();
        $(".shopinfo-left").html(
            "<p>e驾陪集美大学城店<br>地址：集美区孙板南路英埭头公交站<br>" +
            "门店电话：15359281961 吴经理</p> "

        );

        $(".shopinfo-right").empty();
        $(".shopinfo-right").html(
            " <p>e驾陪枋湖店<br>" +
            "地址：湖里区枋湖车管所C座办证大厅斜对面<br>" +
            "门店电话：15359281961 吴经理</p>"
        );



    }else if(param==1){

        $("#fuzhoubtn").css({"background-color":"#01e2ff"});
        $("#xiamenbtn").css({"background-color":"#161d26"});
        $("#shanghai").css({"background-color":"#161d26"});

        $(".shopinfo-left").empty();
        $(".shopinfo-left").html(
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
            " 门店电话：15659992258 谢经理</p>"

        );

        $(".shopinfo-right").empty();
        $(".shopinfo-right").html(
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

    }else if(param==4){

        $("#shanghai").css({"background-color":"#01e2ff"});
        $("#xiamenbtn").css({"background-color":"#161d26"});
        $("#fuzhoubtn").css({"background-color":"#161d26"});

        $(".shopinfo-left").empty();
        $(".shopinfo-left").html(
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
            "门店电话：15359281961 吴经理</p>"

        );

        $(".shopinfo-right").empty();
        $(".shopinfo-right").html(

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

    }
}


function initshadow(){
    var width=$(document).width();
    var imgheight=(width*(8/12)*(5/6)/3-30)*(200/320);
    //var imgheight=$("#shadowfirst").height()/2

    $(".shadowl").each(function(){
        $(this).css({"height":imgheight}).css({"overflow":"hidden"})
    })

    $(".shadowr").each(function(){
        $(this).css({"height":imgheight}).css({"overflow":"hidden"})
    })

    $(".shadowup").each(function(){
        $(this).css({"height":imgheight})
    })

    for(var i=1;i<7;i++){
        $("#shadowup"+i).transition({translate: [0,(-1)*imgheight]},800);
    }
}

function loadinggif(){
    $("body").showLoading();
    $(".loading-indicator").css({"top":"200px;"});
}

function mouseenter(param){
    var imgheight=$(".shadow").get(0).offsetHeight;
    $("#shadowup"+param).transition({translate: [0,imgheight]},800);
}
function mouseleave(param){
    var imgheight=$(".shadow").get(0).offsetHeight;
    $("#shadowup"+param).transition({translate: [0,(-1)*imgheight/2]},800);
}