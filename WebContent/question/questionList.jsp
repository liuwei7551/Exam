<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript">
function checkForm(){
	var subject=$("#subject").val();
	var type=$("#type").val();
	var paperName=$("#paperName").val();
	var joinTime=$("#joinTime").val();
	var optionA=$("#optionA").val();
	var optionB=$("#optionB").val();
	var optionC=$("#optionC").val();
	var optionD=$("#optionD").val();
	var answer=$("#answer").val();
	if(subject==null || subject==""){
		$("#error").html("考试题目不能为空！");
		return false;
	}
	if(type==null || type==""){
		$("#error").html("请选择题目类型！");
		return false;
	}
	if(paperName==null || paperName==""){
		$("#error").html("请选择试卷！");
		return false;
	}
	if(joinTime==null || joinTime==""){
		$("#error").html("加入时间不能为空！");
		return false;
	}
	if(optionA==null || optionA==""){
		$("#error").html("选项一不能为空！");
		return false;
	}
	if(optionB==null || optionB==""){
		$("#error").html("选项二不能为空！");
		return false;
	}
	if(optionC==null || optionC==""){
		$("#error").html("选项三不能为空！");
		return false;
	}
	if(optionD==null || optionD==""){
		$("#error").html("选项四不能为空！");
		return false;
	}
	if(answer==null || answer==""){
		$("#error").html("答案不能为空！");
		return false;
	}
	return true;
}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="data_list">
		<div class="data_info">
			<p>题目管理</p>
		</div>
		<div class="search_content">
			<form action="${pageContext.request.contextPath}/question!list" method="post">
			<table align="center">
				<tr>
					<td><label>题目 ：</label></td>
					<td><input type="text" id="s_subject" name="s_question.subject" value="${s_question.subject }"/></td>
					<td>&nbsp;</td>
					<td><button class="btn btn-primary" style="margin-bottom: 8px" type="submit">查询</button></td>
				</tr>
			</table>
		</form>
			<button style="float: right;margin-bottom: 8px;" class="btn btn-mini btn-primary" type="button" onclick="javascript:window.location='question!preSave'">添加问题</button>
		</div>
		<div class="data_content">
			<table class="table table-hover table-bordered" align="center" varStatus="status">
				<tr>
					<th>序号</th>
					<th>考试题目</th>
					<th>加入时间</th>
					<th>题目类型</th>
					<th>所属试卷</th>
					<th>操作</th>
				</tr>
				<c:forEach var="question" items="${questionList }">
					<tr>
						<td>${status.index+1}</td>
						<td>${question.subject}</td>
						<td><fmt:formatDate value="${question.joinTime }" type="date" pattern="yyyy-MM-dd"/></td>
						<td>
							<c:choose>
								<c:when test="${question.type==1 }">
									单选题
								</c:when>
								<c:otherwise>
									多选题
								</c:otherwise>
							</c:choose>
						</td>
						<td>${question.paper.paperName}</td>
						<td>
							<button type="button" class="btn btn-info btn-mini" onclick="javascript:window.location='question!questionShow?questionId=${question.id}'">详细内容</button>&nbsp;&nbsp;
							<button type="button" class="btn btn-info btn-mini" onclick="javascript:window.location='question!preSave?questionId=${question.id}'">修改 </button>&nbsp;&nbsp;
							<button type="button" class="btn btn-danger btn-mini" onclick="deleteQuestion('${question.id}')">删除</button>
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