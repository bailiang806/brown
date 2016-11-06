/**
 * Created by gaoying on 15/10/13.
 */
//饼图-------------

function initpie01(){

    require.config({
        paths:{
            'echarts' : 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/pie' : 'http://echarts.baidu.com/build/echarts'
        }
    });

    var echarts=require('echarts');

    var myChart = echarts.init(document.getElementById('datapie01'));

    option = {
        color:["#8ac1c8","#daeff2"],
        series : [
            {
                name:'上课情况',
                type:'pie',
                radius : ['96%', '100%'],
                itemStyle : {
                    normal : {
                        label : {
                            show:false,
                            position : 'center',
                            formatter: "0\n已上课时",
                            textStyle : {
                                fontSize : '18',
                                fontWeight : 'bold'
                            }
                        },
                        labelLine : {
                            show : false
                        }
                    },
                    emphasis : {
                        label : {
                            show : false,
                            position : 'center',
                            textStyle : {
                                fontSize : '20',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data:[
                    {value:0, name:'已上课时'},
                    {value:10, name:'未上课时'}
                ]
            }
        ]
    };

// 为echarts对象加载数据
    myChart.setOption(option);

//-----------------

};

function initpie02(){

    require.config({
        paths:{
            'echarts' : 'http://echarts.baidu.com/build/echarts',
            'echarts/chart/pie' : 'http://echarts.baidu.com/build/echarts'
        }
    });

    var myChart = echarts.init(document.getElementById('datapie02'));

    option = {
        color:["#8ac1c8","#daeff2"],
        series : [
            {
                name:'上课情况',
                type:'pie',
                radius :['96%', '100%'],
                itemStyle : {
                    normal : {
                        label : {
                            show:false,
                            position : 'center',
                            formatter: "2\n正在备考科目",
                            textStyle : {
                                fontSize : '18',
                                fontWeight : 'bold'
                            }
                        },
                        labelLine : {
                            show : false
                        }
                    },
                    emphasis : {
                        label : {
                            show : false,
                            position : 'center',
                            textStyle : {
                                fontSize : '20',
                                fontWeight : 'bold'
                            }
                        }
                    }
                },
                data:[
                    {value:1, name:'已上课时'},
                    {value:1, name:'未上课时'}
                ]
            }
        ]
    };

// 为echarts对象加载数据
    myChart.setOption(option);

//-----------------

}



