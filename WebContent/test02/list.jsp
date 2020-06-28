<%@page import="object.PageInfo"%>
<%@page import="object.ArticleVO"%>
<%@ page 
	language="java" 
	contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"
    isELIgnored="false"
%>
<%@ taglib
	prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"
%>
<%@ taglib
	prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("UTF-8");
	PageInfo info = (PageInfo) request.getAttribute("pageInfo");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<style>
		.class1 
		{
			text-decoration : none;
		}
		.class2
		{
			text-align : center;
			font-size : 30px;
		}
	</style>
</head>
<body>
	<table align="center" border="1" width="80%">
		<tr height="10" align="center" bgcolor="lightgreen">
			<td>NO</td>
			<td>SUBJECT</td>
			<td>WRITER</td>
			<td>DATE</td>
		</tr>
		<c:choose>
			<c:when test="${list == null}">
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b><span style="font-size:9pt;">NO ARTICLES FOUND</span></b>
						</p>
					</td>
				</tr>
			</c:when>
			<c:when test="${list != null}">
				<c:forEach var="article" items="${list}" varStatus="viewNo">
					<tr align="center">
						<td width="5%">${viewNo.count}</td>						
						<td align="left" width="35%">
							<span style="padding-right:10px"></span>
							<c:choose>
								<c:when test="${article.depth > 0}">
									<c:forEach begin="1" end="${article.depth}" step="1">
										<span style="padding-left:10px"></span>
									</c:forEach>
									<span style="font-size:12px;">[RE]</span>
									<a class="class1" href="${contextPath}/board/article.do?no=${article.number}">${article.title}</a>
								</c:when>
								<c:otherwise>
									<a class="class1" href="${contextPath}/board/article.do?no=${article.number}">${article.title}</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td width="10%">${article.writerID}</td>
						<td width="10%"><fmt:formatDate value="${article.creationDate}"/></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<div align="center">
		<c:if test="<%=info.checkPreviousSection() %>">
		 <a href="${contextPath}/board/list.do?pageNo=<%=info.getSectionFirstPage()-1%>">PREV</a> 
		</c:if>
		<%
			for(int i = info.getSectionFirstPage(); i<= info.getSectionLastPage(); i++)
			{
				if (i == info.getPageNo())
				{
					%> <b><%= i %></b> <%
					continue;
				}
				%> <a href="${contextPath}/board/list.do?pageNo=<%=i%>"><%= i %></a> <%			
			}
		%>
		<c:if test="<%=info.checkNextSection() %>">
		 <a href="${contextPath}/board/list.do?pageNo=<%=info.getSectionLastPage()+1%>">NEXT</a> 
		</c:if>
	</div>	
	<a class="class1" href="${contextPath}/board/write.do"><p class="class2">WRITE</p></a>
</body>
</html>