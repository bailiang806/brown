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
<#list mailInfo as coachAndTrainingTasks>
    ${coachAndTrainingTasks.coachName}<br/>
    <#list coachAndTrainingTasks.trainingTasks as trainingTask>
        ${trainingTask.trainingDate}
        ${trainingTask.trainingCourse}
        ${trainingTask.trainingTime}
        ${trainingTask.trainingSite}
        <table class="gridtable" border="1">
            <thead>
            <tr>
                <td>学生姓名</td>
                <td>手机号</td>
            </tr>
            </thead>
            <tbody>
            <#list trainingTask.studentInfos as studentInfo>
            <tr>
             <td> ${studentInfo.name}</td>
             <td> ${studentInfo.phone}</td>
            </tr>
            </#list>
           </tbody>
        </table>
        <br/>
    </#list>
    <br/>
</#list>
</body>
</html>