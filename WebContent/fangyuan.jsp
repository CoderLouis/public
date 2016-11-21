<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<table class="easyui-datagrid" data-options="url:'servlet/Xuexiao?action=search&id=<%=request.getParameter("id") %>&schoolName=<%=request.getParameter("schoolName")%>', fit:true, border:false" style="padding:5px">
	<thead>
		<tr>	
			<th data-options="field:'ck', checkbox:true"></th>
			<th data-options="field:'title',formatter:formatTitle">名称</th>
			<th data-options="field:'address'">地址</th>
			<th data-options="field:'floor'">楼层</th>
			<th data-options="field:'totalPrice', sortable:'true'">总价</th>
			<th data-options="field:'unitPrice'">单价</th>
		</tr>
	</thead>
</table>
<script>
	function formatTitle(val, row, index){
		return "<a href='" + row.link + "' target='_blank'>" + row.title + "</a>";
	}
	
</script>