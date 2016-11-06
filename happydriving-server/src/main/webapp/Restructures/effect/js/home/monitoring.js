/**
 * Created by gaoying on 15/10/26.
 */
//    百度推广监测代码,仅线上使用
window.onload=function() {
    var width = window.innerWidth;
    if (width >= 960) {
        //pc端
        var _hmt = _hmt || [];
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?a370292296b0aa8feeff747dc0febd1d";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);

    } else {
        //移动端
        var _hmt = _hmt || [];
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?f84d4a48c041f4485282b32954de2a74";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    };
}