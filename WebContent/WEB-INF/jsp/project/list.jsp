<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="metricminer" %>
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
			
			<metricminer:box title="Search a project">
			<form action="<c:url value="/projects/search" />" method="get" class="project-search">
				<input type="search" name="criteria" id="criteria" value="${param.criteria}" />
				<input type="submit" value="Search" id="search" />
			</form>
			
			</metricminer:box>
			
			<div class="block">
				<div class="block_head">
					<div class="bheadl"></div>
					<div class="bheadr"></div>
					<h2>Projects</h2>
				</div>
				
				<div class="block_content">
				
					<c:if test="${added}">
					<div class="message success"><p>Your project will be processed as soon as possible!</p></div>
					</c:if>
					
					<table cellpadding="0" cellspacing="0" width="100%" class="tablesorter zebra">
						<thead>
							<tr>
								<th>Name</th>
								<th>Repo's URL</th>
								<th>Tags</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${projects}" var="project">
								<tr>
									<td><a href="<c:url value="/project/${project.id}" />">${project.name}</a></td>
									<td>${project.scmUrl}</td>
									<td>
										<c:forEach items="${project.tags}" var="tag">
											${tag.name}&nbsp;
										</c:forEach>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					<div class="pagination right">
						<c:if test="${currentPage != 1}">
							<a href="<c:url value="/projects/${currentPage - 1}"/>">«</a>
						</c:if>
						<c:forEach var="i" begin="1" end="${totalPages}" step="1" varStatus ="status">
							<a href="<c:url value="/projects/${i}" />">${i}</a>
						</c:forEach>
						<c:if test="${currentPage < totalPages}">
							<a href="<c:url value="/projects/${currentPage + 1}"/>">»</a>
						</c:if>
					</div>
				</div>		<!-- .block_content ends -->
				
				<div class="bendl"></div>
				<div class="bendr"></div>
			</div>		<!-- .block ends -->
			
		</div>						<!-- wrapper ends -->
		
	</div>		<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
	<script>
		$(".tablesorter").tablesorter();
	</script>
</body>
</html>