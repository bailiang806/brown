<html>
<head>
    <style type="text/css">
        table.gridtable {
            font-family: verdana, arial, sans-serif;
            font-size: 11px;
            color: #333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
        }

        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #dedede;
        }

        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<table class="gridtable" border="1">
    <thead>
    <tr>
        <th width="200px">城市</th>
        <th width="200px">本月接触人数</th>
        <th width="200px">本月报名人数</th>
        <th width="200px">今日接触人数</th>
        <th width="200px">今日报名人数</th>
        <th width="200px">本月退款人数</th>
    </tr>
    </thead>
    <tbody>
    <#list mailInfo as cityReport>
    <tr>
        <td>${cityReport.cityName}</td>
        <td>${cityReport.monthContactCount}</td>
        <td>${cityReport.monthSignupCount}</td>
        <td>${cityReport.todayContactCount}</td>
        <td>${cityReport.todaySignupCount}</td>
        <td>${cityReport.monthGivenupCount}</td>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>