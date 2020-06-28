<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ taglib
	prefix="format"
	uri="http://java.sun.com/jsp/jstl/fmt"
%>
<%@ taglib
	prefix="core"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<core:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readUrl(input)
	{
		if (!input.files) return;
		if (!input.files[0]) return;
		
		var reader = new FileReader();
		
		reader.onload = function (e)
		{
			$("#preview").attr("src", e.target.result);
		}
		reader.readAsDataURL(input.files[0]);
	}
	
	function backToList(object)
	{
		object.action = "${contextPath}/board/list.do";
		object.submit();
	}
</script>
</head>
<body>
	<h1 style="text-align:center">NEW POST</h1>
	<form name="articleForm" method="post" action="${contextPath}/board/insert.do" enctype="multipart/form-data">
		<table border="0" align="center">
			<tr>
				<td align="right">SUBJECT</td>
				<td colspan="2"><input type="text" size="67" maxlength="500" name="txtTitle"/></td>
			</tr>
			<tr>
				<td align="right" valign="top">CONTENT</td>
				<td colspan="2"><textarea name="txtContent" rows="10" cols="65" maxlength="4000"></textarea></td>
			</tr>
			<tr>
				<td align="right" >IMAGE FILE</td>
				<td><input type="file" name="fileImage" onchange="readUrl(this);"/></td>
				<td><img id="preview" src="#" width="200" height="200"/></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td colspan="2">
					<input type="submit" value="WRITE"/>
					<input type="button" value="LIST" onClick="backToList(this.form);"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>