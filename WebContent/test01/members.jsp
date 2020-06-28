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
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.class1 
	{
		font-size : 40px;
		text-align : center;
	}
	
	.class2
	{
		font-size : 20px;
		text-align : center;
	}
</style>
</head>
<body>
	<p class="class1">MEMBER INFORMATIONS</p>
	<table align="center" border="1">
		<tr align="center" bgcolor="lightgreen">
			<td width="7%"><b>ID</b></td>
			<td width="7%"><b>PW</b></td>
			<td width="7%"><b>NAME</b></td>
			<td width="7%"><b>E-MAIL</b></td>
			<td width="7%"><b>JOIN DATE</b></td>
			<td width="7%"><b>DELETE</b></td>
			<td width="7%"><b>MODIFY</b></td>
		</tr>
		
		<c:choose>
			<c:when test="${list == null}">
				<tr>
					<td colspan="5">NO MEMBER FOUND</td>
				</tr>
			</c:when>
			<c:when test="${list != null}">
				<c:forEach var="member" items="${list}">
					<tr align="center">
						<td>${member.id}</td>
						<td>${member.password}</td>
						<td>${member.name}</td>
						<td>${member.email}</td>
						<td>${member.joinDate}</td>
						<td><a href="/pro17/member/delete.do?userID=${member.id}">DELETE</a></td>
						<td><a href="/pro17/member/modify.do?userID=${member.id}">MODIFY</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<p class="class2"><a href="/pro17/member/register.do">JOIN AS MEMBER</a></p>
</body>
</html>