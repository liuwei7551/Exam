<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
	function checkForm(){
		var paperName=$("#paperName").val();
		if(paperName==null || paperName==""){
			alert("试卷名称不能为空！");
			return false;
		}
		return true;
	}
</script>
<body>
	<div class="data_list">
		<div class="data_info">
			<p>${title}</p>
		</div>
		<div class="data_content">
			<form action="question!save" method="post" onsubmit="return checkForm()">
				<input type="hidden" name="questionId" value="${question.id }">
				<input type="hidden" name="question.joinTime" value="${question.joinTime}">
				<table width="90%" align="center">
					<tr>
						<td><label>考试题目：</label></td>
						<td>
							<input type="text" id="subject" value="${question.subject }" name="question.subject" class="input-xxlarge"/>
						</td>
					</tr>
					<tr>
						<td><label>题目类型：</label></td>
						<td>
							<select id="type" name="question.type" >
								<option>请选择题目类型...</option>
								<option value="1" ${question.type=="1"? "selected" : "" }>单选题</option>
								<option value="2" ${question.type=="2"? "selected" : "" }>多选题</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label>所属试卷：</label></td>
						<td>
							<select id="paperName" name="question.paper.id" >
								<option val="">请选择所属试卷类型...</option>
								<c:forEach var="paper" items="${paperList }">
									<option value="${paper.id }" ${paper.id==question.paper.id? "selected" : "" }>${paper.paperName}</option>
								</c:forEach>
							</select>					
						</td>
					</tr>
					<tr>
						<td><label>选项一：</label></td>
						<td>
							<input type="text" id="optionA" value="${question.optionA }" name="question.optionA"/>
						</td>
					</tr>
					<tr>
						<td><label>选项二：</label></td>
						<td>
							<input type="text" id="optionB" value="${question.optionB }" name="question.optionB"/>
						</td>
					</tr>
					<tr>
						<td><label>选项三：</label></td>
						<td>
							<input type="text" id="optionC" value="${question.optionC }" name="question.optionC"/>
						</td>
					</tr>
					<tr>
						<td><label>选项四：</label></td>
						<td>
							<input type="text" id="optionD" value="${question.optionD }" name="question.optionD"/>
						</td>
					</tr>
					<tr>
						<td><label>题目答案：</label></td>
						<td>
							<input type="text" id="answer" value="${question.answer }" name="question.answer"/>&nbsp;&nbsp;(多选题用逗号隔开，如"A,D")
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn btn-primary" type="submit">保存</button>
							<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
						</td>
					</tr>
				</table>
			</form>
		</div>	
	</div>
</body>
</html>