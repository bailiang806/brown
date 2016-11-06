<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
  <meta charset="utf-8">
  <title>微信支付</title>
  <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
  <meta name="format-detection" content="telephone=no">
  <meta name="format-detection" content="email=no">
  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="0">
  <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="../css/style.css">
  <!--<link href="css/style.css" rel="stylesheet" type="text/css"/>-->
  <script src="../js/jquery-1.10.2.min.js" type="text/javascript"></script>
  <script src="../js/bootstrap.min.js" type="text/javascript"></script>
  <script src="../js/jquery.cxselect.min.js" type="text/javascript"></script>
  <!--<script src="js/driving.js" type="text/javascript"></script>-->
  <script src="../js/driving.js" type="text/javascript"></script>
</head>
<body>
<div class="container"></div>
<script>
  $(function(){

    $('.container').html('<div class="tips" style="text-align: center; margin: 50px auto;color:#3e516a;font-size:15px;">正在调用微信支付，请稍后……</div>')
    callPay();
    function onBridgeReady(){
      $('.container').empty();
      WeixinJSBridge.invoke(
              'getBrandWCPayRequest', {
                "appId" : '${appId}',           //公众号名称，由商户传入
                "timeStamp": '${timeStamp}',    //时间戳，自1970年以来的秒数
                "nonceStr" : '${nonceStr}',     //随机串
                "package" : '${package1}',      //预支付ID参数
                "signType" : '${signType}',     //微信签名方式:
                "paySign" : '${paySign}'        //微信签名
              },
              function(res){
                if(res.err_msg == "get_brand_wcpay_request:ok" ) {
                  alert("支付成功");
                  window.location.href="http://ejiapei.com/index_wechat.html";
                }else if(res.err_msg == "get_brand_wcpay_request:cancel" ){
                  alert("支付过程中用户取消！");
                  window.history.go(-1);
                }else{
                  alert('支付失败');
                  window.history.go(-1);
                }
              }
      );
    }
    function callPay(){
      if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
          document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
          document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
          document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
      }else{
        onBridgeReady();
      }
    }
  })
</script>
</body>
</html>
