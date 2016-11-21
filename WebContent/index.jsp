<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学区房查询</title>
<link rel="stylesheet" type="text/css" href="jquery/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="jquery/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="jquery/themes/color.css" />
<link rel="stylesheet" type="text/css" href="main.css" />

<script src="jquery/jquery.min.js"></script>
<script src="jquery/jquery.easyui.min.js"></script>
<script src="jquery/jquery.formatDateTime.min.js"></script>


<style type="text/css">
#north{
	background-color:white;
	padding-left:10px;
}
#south{
	text-align:right;
	padding:5px;
}
li{
	line-height:20px;
}
.important{
	color:blue;
	text-decoration:underline;
}
h2{
	line-height:10px;
}
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div id="north" data-options="region:'north'" style="height:45px">
			<h2>北京学区房查询</h2>
		</div>
		
		<div id="center" data-options="region:'center'">
			<div id="tabs" class="easyui-tabs" data-options="fit:true, border:false">
				<div title="房源查询" href="school.jsp">
				</div>
			</div>
		</div>
		<div id="south" data-options="region:'south'" style="height:32px">
			<a href="https://github.com/CoderLouis" target="_blank">查看作者的GitHub信息</a>
		</div>
	</div>
	
	
	
<script>
	function openLink(link, title){
		if(!$("#tabs").tabs("exists", title)){
	    	$("#tabs").tabs("add",{
	    		title:title,
	    		closable:true,
	    		href:link
	    	});
    	}
    	else{
    		$("#tabs").tabs("select", title);
    	}
	}
	
</script>
</body>
</html>