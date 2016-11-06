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
        <th width="200px">学员姓名</th>
        <th width="200px">联系方式</th>
        <th width="200px">推荐时间</th>
        <th width="200px">意向城市</th>
        <th width="200px">推荐人</th>
        <th width="200px">推荐人手机号</th>
    </tr>
    </thead>
    <tbody>
    <#list mailInfo as potentialStudent>
    <tr>
        <td>${potentialStudent.name}</td>
        <td>${potentialStudent.phone}</td>
        <td>${potentialStudent.timestamp}</td>
        <td>${potentialStudent.city}</td>
        <td>${potentialStudent.referrerName}</td>
        <td>${potentialStudent.referrerPhone}</td>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>