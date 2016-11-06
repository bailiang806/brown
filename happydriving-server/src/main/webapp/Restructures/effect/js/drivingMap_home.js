(function ($) {
    $.extend({
        /*
         * <script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=Ef022XHtDeWPaFSKKCHWqOgm&v=1.0"></script>
         *$.showMap({
         *	content:'mapBox',//容器id
         *	zoom:10,//放缩比例
         *	owner:{lng:116.124, lat:39.315},//当前点坐标
         *	other:data.returnObject//其他点坐标
         * });
         *
         */

        showMap: function (options) {
            var opts = $.extend({
                content: null,
                zoom: 12,
                owner: null,
                other: null
            }, options);

            // 百度地图API功能
            var map = new BMap.Map(opts.content);
            var centerPoint = new BMap.Point(opts.owner.lng, opts.owner.lat);
            map.centerAndZoom(centerPoint, opts.zoom);		 //设置地图的中心点和坐标


            var navCtl = {type: BMAP_NAVIGATION_CONTROL_LARGE};
            map.addControl(new BMap.NavigationControl(navCtl));


            //map.addControl(new BMap.ZoomControl());  //添加地图缩放控件

            // 创建标注对象并添加到地图
            //var myIcon = new BMap.Icon("http://api.map.baidu.com/mapCard/img/location.gif",
            //    new BMap.Size(14, 23), {
            //        anchor: new BMap.Size(7, 25),
            //    });
            //var onnerMarker = new BMap.Marker(centerPoint, {icon: myIcon});
            //map.addOverlay(onnerMarker);

            //用于显示教练详细信息
            //if (opts.other != null) {
            //    var myGeo = new BMap.Geocoder();
            //    $.each(opts.other, function (m, n) {
            //        myGeo.getPoint(n.provinceName + n.cityName + n.countyName + n.townName + n.detailAddress, function (point) {
            //            if (point) {
            //                map.addOverlay(new BMap.Marker(point));
            //                var marker = new BMap.Marker(point); // 创建点
            //                map.addOverlay(marker);
            //                marker.addEventListener("click", function () {
            //                    window.location.href = "/jsp/coachintroduction.jsp?coachId=" + n.coachId;
            //                });
            //            }
            //        }, n.provinceName);
            //    });
            //}

            //其他点坐标
            var schoolLocations = options.schoolLocations.returnObject;

            $.each(schoolLocations, function (m, drivingSchool) {
                var point = new BMap.Point(drivingSchool.longitude, drivingSchool.latitude);
                //var marker = new BMap.Marker(point); // 创建点
                //marker.setAnimation(BMAP_ANIMATION_BOUNCE);
                //var label = new BMap.Label("<a style='color:#ffffff;text-decoration:none' target='_blank' href='#'>" + drivingSchool.schoolName + "</a>",

                var label = new BMap.Label("<a style='color:#ffffff;text-decoration:none'>" + drivingSchool.schoolName + "</a>",
                    {
                        // offset: new BMap.Size(10, 60),
                        position: point
                    });
                var width = drivingSchool.schoolName.length;
                label.setStyle({
                    maxWidth:"none",//给label设置样式，任意的CSS都是可以的
                    fontSize: "12px",               //字号
                    border: "0px solid #000000",                    //边
                    height: "18px",                //高度
                    width: width * 15 + "px",                 //宽
                    textAlign: "center",            //文字水平居中显示
                    //lineHeight:"60px",            //行高，文字垂直居中显示
                    //background:"url(http://cdn1.iconfinder.com/data/icons/CrystalClear/128x128/actions/gohome.png)",    //背景图片，这是房产标注的关键！
                    backgroundColor: 'red',
                    cursor: "pointer",
                    padding: "2px",
                    borderRadius: "14px 14px 14px 0px",
                    overflowx:"visible"
                });
                //marker.setLabel(label);
                label.setTitle(drivingSchool.address)
                map.addOverlay(label);

                //label.addEventListener('click',function(){
                //    this.location.href="#schooldetail/" + drivingSchool.id;
                //})

                //var opts = {
                //    width: 50,    // 信息窗口宽度
                //    height: 18,     // 信息窗口高度
                //    //title: "驾校名称", // 信息窗口标题
                //    enableAutoPan: false //自动平移
                //};
                //var content = drivingSchool.schoolName;
                //var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                //marker.addEventListener("click", function () {
                //    map.openInfoWindow(infoWindow, point); //开启信息窗口
                //});

                //var myLabel = new BMap.Label("<a style='color:red;text-decoration:none' target='_blank' href='http://dev.baidu.com/wiki/static/index.htm'>"+drivingSchool.schoolName+"</a>",     //为lable填写内容
                //    {offset:new BMap.Size(-60,-60),                  //label的偏移量，为了让label的中心显示在点上
                //        position:point});                                //label的位置
                ////myLabel.setStyle({                                   //给label设置样式，任意的CSS都是可以的
                ////    fontSize:"14px",               //字号
                ////    border:"0",                    //边
                ////    height:"20px",                //高度
                ////    width:"20px",                 //宽
                ////    textAlign:"center",            //文字水平居中显示
                ////    //lineHeight:"60px",            //行高，文字垂直居中显示
                ////    //background:"url(http://cdn1.iconfinder.com/data/icons/CrystalClear/128x128/actions/gohome.png)",    //背景图片，这是房产标注的关键！
                ////    background: 'color:red',
                ////    cursor:"pointer"
                ////});
                //myLabel.setStyle({
                //    color : "#fff",
                //    fontSize : "16px",
                //    backgroundColor :"0.05",
                //    border :"0",
                //    fontWeight :"bold"
                //});
                //myLabel.setTitle(drivingSchool.address);               //为label添加鼠标提示
                //map.addOverlay(myLabel);

                //map.openInfoWindow(infoWindow, point); //开启信息窗口
            });
        }
    });
})(jQuery);
