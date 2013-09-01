<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="metricminer" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>


<c:import url="../import/head.jsp" />

<style type="text/css">
</style>
<title>Metric Miner</title>
</head>

<body>
	<div id="hld">
		<div class="wrapper">
			<!-- wrapper begins -->
			<c:import url="../import/header.jsp" />
			<metricminer:box extraClasses="small left" title="Projects">
				<div class="projects-summary">
					<table>
						<tr>
							<th>Total projects</th> 
							<td>${totalProjects}</td>
						</tr>
						<tr>
							<th>Total committers</th> 
							<td>${totalAuthors}</td>
						</tr>
						<tr>
							<th>Total commits processed</th> 
							<td>${totalCommits}</td>
						</tr>
						<tr>
							<th>Total artifacts processed</th> 
							<td>${totalArtifacts}</td>
						</tr>
					</table>
					<h2>Last projects added</h2>
					<table>
						<tr>
							<th>Name</th>
						</tr>
						<c:forEach items="${newProjects}" var="project">
							<tr>
								<td><a href="<c:url value="/project/${project.id}"/>">${project.name}</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</metricminer:box>
			
			<metricminer:box extraClasses="small right" title="Charts">
				<div id="top-committers" style="width: 550px; height: 400px;">loading chart...</div>
			</metricminer:box>
			
		</div>
		<!-- wrapper ends -->
	</div>
	<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>

</html>