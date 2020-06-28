<%@ page 
	language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%@ taglib 
	prefix = "fmt"
	uri ="http://java.sun.com/jsp/jstl/fmt"
%>
<%@ taglib
	prefix = "c"
	uri = "http://java.sun.com/jsp/jstl/core"
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="${contextPath}/member/update.do?txtUserID=${member.id}">
		<h1 style="text-align:center">MEMBER REGISTERATION</h1>
		<table align="center">
			<tr>
				<td width="200"><p align="right">ID</p></td>
				<td width="400"><input type="text" name="txtUserID" value="${member.id}" disabled></td>
			</tr>
			<tr>
				<td><p align="right">PASSWORD</p></td>
				<td><input type="password" name="txtUserPW" value="${member.password}"></td>
			</tr>
			<tr>
				<td><p align="right">NAME</p></td>
				<td><input type="text" name="txtName" value="${member.name}"></td>
			</tr>
			<tr>
				<td><p align="right">E-MAIL</p></td>
				<td><input type="text" name="txtEmail" value="${member.email}"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="JOIN">
					<input type="reset" value="RESET">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>