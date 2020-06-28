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
</head>
<body>
	<table border="0" align="center" width="80%">
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">SUBJECT</td>
			<td><input type="text" value="${article.title}" name="txtTitle" id="txtTitle" disabled /></td>
		</tr>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">CONTENT</td>
			<td><textarea rows="20" cols="60" name="txtContent" id="txtContent" disabled>${article.content}</textarea></td>
		</tr>
		<core:if test="${not empty article.imagePath && article.imagePath != 'null'}">
			<tr>
				<td width="20%" align="center" bgcolor="#FF9933">IMAGE</td>
				<td>
					<input type="hidden" name="originalImageName" value="${article.imagePath}"/>
					<img src="${contextPath}/board/download.do?no=${article.number}&fileImage=${article.imagePath}" id="imgPreview">
				</td>
			</tr>
		</core:if>
		<tr>
			<td width="20%" align="center" bgcolor="#FF9933">DATE</td>
			<td><input type="text" value="${article.creationDate}" name="txtDate" id="txtDate" disabled /></td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<a href="${contextPath}/board/modify.do?no=${article.number}">MODIFY</a> 
				<a href="${contextPath}/board/delete.do?no=${article.number}">DELETE</a> 
				<a href="${contextPath}/board/reply.do?no=${article.number}">REPLY</a>
			</td>
		</tr>
	</table>
	</body>
</html>