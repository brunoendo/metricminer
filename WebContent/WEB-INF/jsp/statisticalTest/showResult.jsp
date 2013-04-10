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
					<h2>Statistical Test</h2>
				</div>		<!-- .block_head ends -->
				
				<div class="block_content">
					<table class="results">
						<tr>
							<th>Name</th>
							<td>${result.name}</td>
						</tr>
						<tr>
							<th>Author</th>
							<td>${result.author.name}</td>
						</tr>
						<tr>
							<th>First set</th>
							<td>${result.q1.query.name} (by ${result.q1.query.author.name})</td>
						</tr>
						<tr>
							<th>Second set</th>
							<td>${result.q2.query.name} (by ${result.q2.query.author.name})</td>
						</tr>
						<tr>
							<th>Statistical test</th>
							<td>${result.test.name}</td>
						</tr>
						<tr>
							<th>Date</th>
							<td><fmt:formatDate value="${result.date.time}" pattern="MM/dd/yyyy HH:mm" /></td>
						</tr>
					</table>
					<pre>${result.output}</pre>
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

