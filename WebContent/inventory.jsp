<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Shop Inventory Management</title>
		
		<style type="text/css">
			<%@ include file="css/styles2.css" %>"
		</style>
	</head>
	<body>
		<div>
			<h1>Shop Inventory Management</h1>
			<div class="header">
				<a href="${pageContext.request.contextPath}/" class="header-button">VIEW ALL</a>
				<a href="${pageContext.request.contextPath}/add" class="header-button">ADD A PRODUCT</a>
			</div>
		
			<div class="search-functions">
				<form action="search">
					<label>Search Bar:<input type="text" name="search_bar" class="search-bar"/>
					<input type="submit" value="Search" name="sumbit" class="button"/></label> 
					<br/>
					<label>
						<label><input class="radio" type="radio" name="search_attribute" value="id">&nbsp; ID &nbsp; &nbsp; </label>
						<label><input class="radio" type="radio" name="search_attribute" value="name">&nbsp; Name &nbsp; &nbsp; </label>
						<label><input class="radio" type="radio" name="search_attribute" value="description">&nbsp; Description &nbsp; &nbsp; </label>
						<label><input class="radio" type="radio" name="search_attribute" value="unit_price">&nbsp; Unit Price </label>
					</label>
				</form>
			</div>
			
			<div class="filter-functions">
				<form action="filter">
					<label>Filter From:
					<select name="filter_bar" class="filter-bar">
								<c:forEach begin="0" end="100" varStatus="loop">
									<option value="${loop.index}">${loop.index}</option>
								</c:forEach>
					</select>
					<input type="submit" value="Filter" name="sumbit" class="button"/></label> 
					<br/>
					<label>
						<label>&nbsp; &nbsp; &nbsp; &nbsp; <input class="radio" type="radio" name="filter_attribute" value="price">&nbsp; Unit Price &nbsp; &nbsp; </label>
						<label><input class="radio" type="radio" name="filter_attribute" value="available">&nbsp; Available </label>
					</label>
					<label>
						<label>&nbsp; &nbsp; <input class="radio" type="radio" name="filter_type" value="greater_than">&nbsp; Greater Than &nbsp; &nbsp; </label>
						<label><input class="radio" type="radio" name="filter_type" value="less_than">&nbsp; Less Than &nbsp; </label>
					</label>
				</form>
			</div>
			
			<br>
			<br>
		</div>
		<div>
			<table border="1">
				<tr>
					<th>
						ID &nbsp;
						<a href="${pageContext.request.contextPath}/?action=Iup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Idown" class="button">▼</a>
					</th>
					<th>
						Product &nbsp;
						<a href="${pageContext.request.contextPath}/?action=Nup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Ndown" class="button">▼</a>
					</th>
					<th>
						Description &nbsp;
						<a href="${pageContext.request.contextPath}/?action=Dup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Ddown" class="button">▼</a>
					</th>
					<th>
						Available &nbsp;
						<a href="${pageContext.request.contextPath}/?action=Aup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Adown" class="button">▼</a>
					</th>
					<th>
						Unit Price &nbsp;
						<a href="${pageContext.request.contextPath}/?action=Uup" class="button">▲</a>
						<a href="${pageContext.request.contextPath}/?action=Udown" class="button">▼</a>
					</th>
					<th>Action</th>
				</tr>
				<c:forEach var="product" items="${products}">
					<tr>
						<td><c:out value="${product.id}" /></td>
						<td><c:out value="${product.name}" /></td>	
						<td><c:out value="${product.description}" /></td>	
						<td><c:out value="${product.available}" /></td>	
						<td><fmt:formatNumber value = "${product.unitPrice}" type = "currency"/></td>
						
						<td>
							<div>
								<a href="${pageContext.request.contextPath}/update?action=purchase&id=${product.id}" class="button">PURCHASE</a>
								<a href="${pageContext.request.contextPath}/edit?id=${product.id}" class="button">EDIT</a>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</body>
</html>