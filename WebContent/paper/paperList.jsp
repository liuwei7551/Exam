<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
	function deletePaper(paperId){
		if(confirm("确定要删除这条信息吗？")){
			$.post("paper!delete",{paperId:paperId},
					function(result){
						var result=eval('('+result+')');
						if(result.success){
							alert("删除成功！");
							window.location.href="${pageContext.request.contextPath}/paper!paperList";
						}else{
							alert("删除失败");
						}
					}
				);
		}
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="data_list">
		<div class="data_info">
			<p>试卷管理</p>
		</div>
		<div>
			<button style="float: right;margin-bottom: 8px;" class="btn btn-mini btn-primary" type="button" onclick="javascript:window.location='paper!preSave'">添加考生信息</button>
		</div>
		<div class="data_content">
			<table class="table table-hover table-bordered" align="center">
				<tr>
					<th>试卷序号</th>
					<th>试卷名称</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach var="paper" items="${paperList }" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>${paper.paperName}</td>
						<td><fmt:formatDate value="${paper.joinDate }" type="date" pattern="yyyy-MM-dd"/></td>
						<td>
							<button type="button" class="btn btn-info btn-sm" onclick="javascript:window.location='paper!preSave?paperId=${paper.id}'">修改</button>&nbsp;&nbsp;
							<button type="button" class="btn btn-danger btn-sm" onclick="deletePaper('${paper.id}')">删除</button>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div>
			<div class="pagination pagination-centered">
				<ul>
					${pageCode }
				</ul>
			</div>
		</div>
	</div>
</body>
</html>