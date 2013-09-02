<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib prefix="mm" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<c:import url="../import/head.jsp" />
	<title>Metric Miner</title>
</head>

<body>
	<div id="hld">
		<div class="wrapper">		<!-- wrapper begins -->
		<c:import url="../import/header.jsp" />


		<mm:box title="My Queries">

			<table class="mm-table">
				<tr>
					<th width="40%">Name</th>
					<th width="30%">Author</th>
					<th width="30%">Date</th>
				</tr>
				<c:forEach items="${mine}" var="query">
					<tr>
						<td><a href="<c:url value="/query/${query.id}"/>">${query.name}</a></td>
						<td>${query.author.name}</td>
						<td><fmt:formatDate value="${query.submitDate.time}" pattern="yyyy/MM/dd - HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
		</mm:box>

		<mm:box title="All Queries">

			<table class="mm-table">
				<tr>
					<th width="40%">Name</th>
					<th width="30%">Author</th>
					<th width="30%">Date</th>
				</tr>
				<c:forEach items="${others}" var="query">
					<tr>
						<td><a href="<c:url value="/query/${query.id}"/>">${query.name}</a></td>
						<td>${query.author.name}</td>
						<td><fmt:formatDate value="${query.submitDate.time}" pattern="yyyy/MM/dd - HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
			
			<div class="pagination right">
				<c:if test="${currentPage != 1}">
					<a href="<c:url value="/queries/${currentPage - 1}"/>">«</a>
				</c:if>
				<c:forEach var="i" begin="1" end="${totalPages}" step="1" varStatus ="status">
					<a href="<c:url value="/queries/${i}" />">${i}</a>
				</c:forEach>
				<c:if test="${currentPage < totalPages}">
					<a href="<c:url value="/queries/${currentPage + 1}"/>">»</a>
				</c:if>
			</div>

		</mm:box>

		</div>
		
	</div>
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>
</html>

