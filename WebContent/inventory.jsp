<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>UCVTS Library</title>
		
		<style type="text/css">
			<%@ include file="css/styles.css" %>"
		</style>
	</head>
	<body>
		<div>
			<h1>Inventory Management</h1>
			
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">ADD A BOOK</a>
			</div>
		</div>
		<div>
			<table border="1">
				<tr>
					<td>
						Title
						<a href="${pageContext.request.contextPath}/?action=Tup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Tdown" class="button">▼</a>
					</td>
					<td>
						Author
						<a href="${pageContext.request.contextPath}/?action=Aup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Adown" class="button">▼</a>
					</td>
					<td>
						Copies
						<a href="${pageContext.request.contextPath}/?action=Cup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Cdown" class="button">▼</a>
					</td>
					<td>
						Available
						<a href="${pageContext.request.contextPath}/?action=Vup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Vdown" class="button">▼</a>
					</td>
					<td>Action</td>
				</tr>
				<c:forEach var="book" items="${books}">
					<tr>
						<td><c:out value="${book.title}" /></td>	
						<td><c:out value="${book.author}" /></td>	
						<td><c:out value="${book.copies}" /></td>	
						<td><c:out value="${book.available}" /></td>	
						<td>
							<div>
								<a href="${pageContext.request.contextPath}/update?action=rent&id=${book.id}" class="button">RENT</a>
								<a href="${pageContext.request.contextPath}/update?action=return&id=${book.id}" class="button">RETURN</a>
								<a href="${pageContext.request.contextPath}/edit?id=${book.id}" class="button">EDIT</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>