(function($){
    $.extend({
        sendMessage:function(options){
            var opts = $.extend({
                button:null,
                tel:null,
                success:function(){},
                error:function(){},
                time:60,
            },options);
            var InterValObj;

            if(opts.tel=='' || opts.tel==null) {
                alert('请填写电话号码！')
            }else if(!/^1[3|4|5|8|7]\d{9}$/.test(opts.tel)){
                alert('请填写正确格式的电话号码！')
            }else{
                $.ajax({
                    type: "post",
                    url: '/user/smsValidate',
                    data: {"phone": opts.tel},
                    success: function (data) {
                        if (data.result != true) {
                            alert(data.message);
                        } else {
                            alert('发送成功');
                            startTime();
                        }
                    },
                    error: function () {
                        alert('出错！请检查相关填写！');
                    }
                });
            }

            function startTime(){
                var curCount = opts.time;
                opts.button.attr("disabled","");
                InterValObj = setInterval(function(){
                    if(curCount==0){
                        stopTime();
                    }else{
                        curCount--;
                        opts.button.val("请在" + curCount + "秒内输入");
                    }
                },1000);
            }

            function stopTime(){
                window.clearInterval(InterValObj);
                opts.button.removeAttr("disabled");
                opts.button.val("重新发送");
            }
        }
    })
})(jQuery)