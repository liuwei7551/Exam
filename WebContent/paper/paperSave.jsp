<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			<form action="paper!save" method="post" onsubmit="return checkForm()">
			<input  type="hidden" name="paperId" value="${paper.id }">
			<input  type="hidden" name="paper.joinDate" value="${paper.joinDate }">
				<table width="40%" align="center">
					<tr>
						<td><label>试卷名称：</label></td>
						<td><input type="text" id="paperName" value="${paper.paperName }" name="paper.paperName"></td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2">
							<button type="submit" class="btn btn-primary">提交</button>
							<button type="button" class="btn btn-primary" onclick="javascript:history.back()">返回</button>
						</td>
					</tr>
				</table>
			</form>
		</div>	
	</div>
</body>
</html>