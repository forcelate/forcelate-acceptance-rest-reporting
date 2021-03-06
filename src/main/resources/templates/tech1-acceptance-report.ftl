<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <meta http-equiv="Content-Security-Policy" content="default-src 'self' 'unsafe-inline'">
    <title>Acceptance Report</title>
</head>
<body style="margin: 8px">
    <p style="font-size:16px;">
  	    <u>Acceptance Report</u>
    </p>
    <p>
  	    <span>Generated on: <b>${.now?string["yyyy-MM-dd HH:mm"]}</b>;</span>
    </p>
    <p>
        <span>Git branch: <b>${report.git.branch}</b>;</span>
        <span>Git commit id: <b>${report.git.commitId}</b>;</span>
        <span>Git commit time: <b>${report.git.commitTime}</b>;</span>
    </p>
    <p>
        <span>BaseURL: <b>${report.environment.baseURI}</b>;</span>
        <span>Port: <b>${report.environment.port?string("#.###")}</b>;</span>
        <span>BasePath: <b>${report.environment.basePath}</b>;</span>
    </p>
    <#list report.count?keys as key>
        <#if key == 'Server Down'>
            <span style="color:red">${key}: <b>${report.count?values[key_index]}</b>;</span>
        <#elseif key == 'Failure'>
            <span style="color:red">${key}: <b>${report.count?values[key_index]}</b>;</span>
        <#elseif key == 'Unexpected'>
            <span style="color:red">${key}: <b>${report.count?values[key_index]}</b>;</span>
        <#elseif key == 'Empty'>
            <span style="color:blue">${key}: <b>${report.count?values[key_index]}</b>;</span>
        <#elseif key == 'Missing'>
            <span style="color:black">${key}: <b>${report.count?values[key_index]}</b>;</span>
        <#elseif key == 'Success'>
            <span style="color:green">${key}: <b>${report.count?values[key_index]}</b>;</span>
        </#if>
    </#list>
    <#if !report.mappingsAvailability>
        <p>
            <span style="color:red">
                <u>
                    Warning: Mappings endpoint is unavailable. Report is not precise.
                </u>
            </span>
        </p>
    </#if>
    <p></p>
    <table class="table table-bordered" border="1" style="width:100%;">
        <thead class="thead-light">
            <tr class="text-center">
                <th scope="col" width="20%">Endpoint</th>
                <th scope="col" width="20%" align="center">HTTP Type</th>
                <th scope="col" align="center">Description, Message, Status</th>
            </tr>
        </thead>
        <#list report.executions as execution>
            <tr>
                <th width="20%">${execution.mapping.endpoint}</th>
                <td width="20%" align="center">${execution.mapping.httpType}</td>
                <td>
                    <table border="1" style="width:100%;">
                        <#list execution.calls as call>
                            <tr>
                                <td width="30%" align="center">${call.description}</td>
                                <td width="40%" align="center">${call.message}</td>
                                <#if call.status == 'Success'>
                                    <td width="30%" align="center" class="table-success" style="color:green">
                                        <b>${call.status}</b>
                                    </td>
                                <#elseif call.status == 'Missing'>
                                     <td width="30%" align="center" class="table-secondary" style="color:black">
                                         <b>${call.status}</b>
                                     </td>
                                <#elseif call.status == 'Empty'>
                                     <td width="30%" align="center" class="table-info" style="color:blue">
                                         <b>${call.status}</b>
                                     </td>
                                <#elseif call.status == 'Failure'>
                                     <td width="30%" align="center" class="table-danger" style="color:red">
                                         <b>${call.status}</b>
                                     </td>
                                <#elseif call.status == 'Unexpected'>
                                     <td width="30%" align="center" class="table-danger" style="color:red">
                                         <b>${call.status}</b>
                                     </td>
                                <#elseif call.status == 'Server Down'>
                                     <td width="30%" align="center" class="table-danger" style="color:red">
                                         <b>${call.status}</b>
                                     </td>
                                </#if>
                            </tr>
                        </#list>
                    </table>
                </td>
            </tr>
        </#list>
    </table>
</body>
</html>
