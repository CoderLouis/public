<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="easyui-panel" style="padding:2px" data-options="fit:true, border:false">
	<div class="easyui-layout" data-options="fit:true">
		<div class="easyui-panel" data-options="region:'west', split:true" style="width:320px">
			<table id="schoolDatagrid" class="easyui-datagrid" data-options="url:'servlet/Xuexiao?action=list', singleSelect:true, onDblClickRow:search, border:false, toolbar:'#schoolToolbar'">
				<thead>
					<tr>
						<th data-options="field:'name'">学校</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="easyui-panel" data-options="border:false, region:'center'">
			<div id="schoolTabs" class="easyui-tabs" data-options="fit:true">
				
			</div>
		</div>
	</div>	
	
	<div id="schoolToolbar" style="padding:5px">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-add'" title="添加" onclick="add()"></a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-edit'" title="编辑"></a>
		<a class="easyui-linkbutton" data-options="iconCls:'icon-remove'" title="删除"></a>
	</div>
	
	<div id="schoolDialog" class="easyui-dialog" data-options="closed:true" style="width:500px;height:350px;padding:10px" title="学校信息">
		<form id="schoolForm">
			<div style="margin-bottom:5px">
				<input class="easyui-textbox" id="schoolName" style="width:100%" data-options="label:'学校名称:',required:true"></input>
			</div>
			<div style="margin-bottom:5px">
				<input class="easyui-textbox" id="chuzhong" style="width:100%;height:100px" data-options="label:'对应初中:', multiline:'true'"></input>
			</div>
			<div style="margin-bottom:5px">
				<input class="easyui-textbox" id="xiaoqu" style="width:100%;height:100px;" data-options="label:'小区:', required:true,multiline:'true'"></input>
			</div>
			<div style="text-align:left">
				<a class="easyui-linkbutton wi-linkbutton" onclick="addSchool()">添加</a>
			</div>
		</form>
	</div>
</div>

<script>
function search(index, row){
	var schoolName = row.name;
	
	if($("#schoolTabs").tabs("exists", schoolName)){
		$("#schoolTabs").tabs("select", schoolName);
	}
	else{
		var link = "fangyuan.jsp?id="+ row.id+"&schoolName=" + schoolName;
		$("#schoolTabs").tabs("add",{
			title:schoolName,
			href:link,
			closable:true
		});
	}
}

function add(){
	$("#schoolDialog").dialog({closed:false});
}

function addSchool(){
	$.messager.progress({title:"处理中...", text:""});
	$.post("servlet/Xuexiao",{
		action:"add",
		schoolName:$("#schoolName").textbox("getValue"),
		chuzhong:$("#chuzhong").textbox("getValue"),
		xiaoqu:$("#xiaoqu").textbox("getValue")
	}, function(response){
		$("#schoolDialog").dialog({closed:true});
		$("#schoolDatagrid").datagrid("reload");
		$("#schoolForm").form("reset");
	}).error(function(response){
		$.messager.alert("错误", response.responseText, "error");
	}).always(function(){
		$.messager.progress("close");
	});
}
</script>