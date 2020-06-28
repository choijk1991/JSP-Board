<%@page import="object.ArticleVO"%>
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
	ArticleVO article = (ArticleVO) request.getAttribute("article");	
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
			$("#imgPreview").html("<img src='"+e.target.result+"'>'");
		}
		reader.readAsDataURL(input.files[0]);
	}
	
	function backToList(object)
	{
		object.action = "${contextPath}/board/article.do?no=${article.number}";
		object.submit();
	}
</script>
</head>
<body>
	<form name="frmArticle" method="post" action="${contextPath}/board/update.do?no=${article.number}" enctype="multipart/form-data">
		<table border="0" align="center" width="80%">
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">WRITER</td>
				<td><input type="text" value="${article.writerID}" name="txtWriterID" id="txtWriterID" disabled/></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">SUBJECT</td>
				<td><input type="text" value="${article.title}" name="txtTitle" id="txtTitle"/></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">CONTENT</td>
				<td><textarea rows="20" cols="60" name="txtContent" id="txtContent">${article.content}</textarea></td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">IMAGE</td>
				<td>
				<input type="hidden" name="hidOriFileName" value="${article.imagePath}"/>
				<div id="imgPreview">
				<core:if test="${not empty article.imagePath && article.imagePath != 'null'}">	
					<img src="${contextPath}/board/download.do?no=${article.number}&fileImage=${article.imagePath}">
				</core:if>
				</div>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">FILE</td>
				<td><input type="file" name="fileImage" id="fileImage" onChange = "readUrl(this);"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="MODIFY"/>
					<input type="button" value="LIST" onClick="backToList(this.form);"/>
				</td>
			</tr>
		
		</table>
	</form>
</body>
</html>