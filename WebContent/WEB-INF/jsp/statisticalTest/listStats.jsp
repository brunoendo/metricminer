<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
			<div class="block">
				<div class="block_head">
					<div class="bheadl"></div>
					<div class="bheadr"></div>
					<h2>My Statistical Tests</h2>
				</div>		<!-- .block_head ends -->
				
				<div class="block_content">
				
					<c:if test="${added}">
					<div class="message success"><p>Your statistical test will be executed as soon as possible!</p></div>
					</c:if>
					
					<table class="results">
						<tr>
							<th>Name</th>
							<th>1st set</th>
							<th>2nd set</th>
							<th>Statistical test</th>
							<th>Date</th>
							<th></th>
						</tr>
						<c:forEach items="${results}" var="result">
							<tr>
								<td>${result.name}</td>
								<td>${result.q1.query.name}</td>
								<td>${result.q2.query.name}</td>
								<td>${result.test.name}</td>
								<td><fmt:formatDate value="${result.date.time}" pattern="MM/dd/yyyy HH:mm" /></td>
								<td><a href="<c:url value="/stats/results/${result.id}"/>">see results</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>		<!-- .block_content ends -->
				<div class="bendl"></div>
				<div class="bendr"></div>
			</div>		<!-- .block ends -->
		</div>						<!-- wrapper ends -->
	</div>		<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>
</html>

