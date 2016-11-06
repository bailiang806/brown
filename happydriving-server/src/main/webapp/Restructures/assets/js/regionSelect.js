(function($){
    $.extend({
        /*
         * desc 城市四级联动初始化方法
         * param $box——城市下拉框父级元素id
         * param callBack—— 回调函数，在初始化和切换个选项是触发此方法
         */
        cityInit : function(options){
            var opts = $.extend(true,{
                $box : $('#cityList'),
                data:{
                    province : null,
                    city : null,
                    county : null,
                    town : null
                },
                callBack: function(){}
            },options);

            if(!isEmpty(opts.data)){
                opts.data.province = (opts.data.province == '0' || opts.data.province == '-1' || opts.data.province == undefined || opts.data.province == 'null' || opts.data.province == null)?'':opts.data.province;
                opts.data.city = (opts.data.city == '0' || opts.data.city == '-1' || opts.data.city == undefined || opts.data.city == 'null' || opts.data.city == null)?'':opts.data.city;
                opts.data.county = (opts.data.county == '0' || opts.data.county == '-1' || opts.data.county == undefined || opts.data.county == 'null' || opts.data.county == null)?'':opts.data.county;
                opts.data.town = (opts.data.town == '0' || opts.data.town == '-1' || opts.data.town == undefined || opts.data.town == 'null'  || opts.data.town == null)?'':opts.data.town;
            }

            var $city = opts.$box;
            //获取省份列表
            $.ajax({
                type:"GET",
                url: '/region/province',
                success:function(data){
                    var provinceData = [{id:'-1',text:'选择省份'}];
                    var defaultVal = opts.data.province == '' ? provinceData[0].id : opts.data.province;
                    $.each(data.returnObject, function(m,n) {
                        provinceData.push({id: n.id,text: n.name});
                    });
                    $('#province',$city).show().select2({
                        data:provinceData,
                        minimumResultsForSearch: -1
                    }).off('change').on('change',function(){
                        var val = $(this).val();
                        getCity(val);
                    }).select2('val', defaultVal);
                    getCity(defaultVal);
                    opts.data.province = '';
                }
            });
            //获取城市列表
            function getCity(val){
                var cityData = [{id:'-1',text:'选择城市'}];
                var defaultVal = opts.data.city == '' ? cityData[0].id : opts.data.city;
                if(val != '-1'){
                    $.ajax({
                        type:"GET",
                        url: '/region/city',
                        data:{
                            provinceId:val
                        },
                        success:function(data){
                            $.each(data.returnObject, function(m,n) {
                                cityData.push({id: n.id,text: n.name});
                            });
                            $('#city',$city).show().select2({
                                data:cityData,
                                minimumResultsForSearch: -1
                            }).off('change').on('change',function(){
                                var val = $(this).val();
                                getcounty(val);
                            }).select2('val', defaultVal);
                            getcounty(defaultVal);
                            opts.data.city = '';
                        }
                    });
                }else{
                    $('#city',$city).show().select2({
                        data:cityData,
                        minimumResultsForSearch: -1
                    }).off('change').on('change',function(){
                        var val = $(this).val();
                        getcounty(val);
                    }).select2('val', defaultVal);
                    getcounty(defaultVal);
                    opts.data.city = '';
                }
            }
            //获取城镇列表
            function getcounty(val){
                var countyData = [{id:'-1',text:'选择城镇'}];
                var defaultVal = opts.data.county == '' ? countyData[0].id : opts.data.county;
                if(val != '-1'){
                    $.ajax({
                        type:"GET",
                        url: '/region/county',
                        data:{
                            cityId:val
                        },
                        success:function(data){
                            $.each(data.returnObject, function(m,n) {
                                countyData.push({id: n.id,text: n.name});
                            });
                            $('#county',$city).show().select2({
                                data:countyData,
                                minimumResultsForSearch: -1
                            }).off('change').on('change',function(){
                                var val = $(this).val();
                                getTown(val);
                            }).select2('val', defaultVal);
                            getTown(defaultVal);
                            opts.data.county = '';
                        }
                    });
                }else{
                    $('#county',$city).show().select2({
                        data:countyData,
                        minimumResultsForSearch: -1
                    }).off('change').on('change',function(){
                        var val = $(this).val();
                        getTown(val);
                    }).select2('val', defaultVal);
                    getTown(defaultVal);
                    opts.data.county = '';
                }
            }
            //获取乡村列表
            function getTown(val){
                var townData = [{id:'-1',text:'选择乡村'}];
                var defaultVal = opts.data.town == '' ? townData[0].id : opts.data.town;
                if(val != '-1'){
                    $.ajax({
                        type:"GET",
                        url: '/region/town',
                        data:{
                            countyId:val
                        },
                        success:function(data){
                            $.each(data.returnObject, function(m,n) {
                                townData.push({id: n.id,text: n.name});
                            });
                            $('#town',$city).show().select2({
                                data:townData,
                                minimumResultsForSearch: -1
                            }).off('change').on('change',function(){
                                callBack();
                            }).select2('val', defaultVal);
                            callBack();
                            opts.data.town = '';
                        }
                    });
                }else{
                    $('#town',$city).show().select2({
                        data:townData,
                        minimumResultsForSearch: -1
                    }).off('change').on('change',function(){
                        callBack();
                    }).select2('val', defaultVal);
                    callBack();
                    opts.data.town = '';
                }

            }
            //回调方法
            function callBack(){
                opts.callBack({
                    province:$('#province',$city).val() || null,
                    city:$('#city',$city).val() || null,
                    county:$('#county',$city).val() || null,
                    town:$('#town',$city).val() || null
                });
            }

            //对象判空
            function isEmpty(obj){
                for (var name in obj){
                    return false;
                }
                return true;
            };

        }
    });
})(jQuery);
