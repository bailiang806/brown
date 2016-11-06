<%@ page language="java" pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta charset="utf-8">
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
<script type="text/javascript">
    $(function(){
        wx.config({
            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId : '${appId}',      //公众号名称，由商户传入
            timestamp: ${timestamp},    //时间戳，自1970年以来的秒数
            nonceStr : '${nonceStr}',     //随机串
            signature: '${signature}',// 必填，签名，见附录1
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

                    window.location.href="/index_wechat.html"
                }
            });
        });
    })
</script>
</body>
</html>
