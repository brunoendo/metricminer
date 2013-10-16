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
#details {
	display: inline-block;
	width: 100%;
}
.detail-box {
	vertical-align: top;
	display: inline-block;
	width: 48%;
}
.googlechart {
	width: 100%;
	height: 300px;
}
#charts {
	width: 100%;
	padding-top: 15px;
	margin: auto;
}
</style>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = google.visualization.arrayToDataTable();

        var options = {
          title: 'Commits of the last six months'
        };

        var chart = new google.visualization.LineChart(document.getElementById('commit_chart'));
        chart.draw(data, options);
      }
    </script>

<title>Metric Miner</title>
</head>

<body>
	<div id="hld">
		<div class="wrapper">
			<!-- wrapper begins -->
			<c:import url="../import/header.jsp" />
			<metricminer:box title="${project.name}">
				<c:if test="${empty project.totalCommits }">
					<h2>We have not finished processing this project yet!</h2>
				</c:if>
				<c:if test="${! empty project.totalCommits}">
					<div id="details">
						<div class="detail-box">
							<table class="mm-table">
								<tr>
									<th>Name</th>
									<td>${project.name} <input type="hidden" name="id"
										id="projectId" value="${project.id}" /></td>
								</tr>
		
								<tr>
									<th>Repo's path</th>
									<td>${project.scmUrl}</td>
								</tr>
								
								<tr>
									<th>Total commits</th>
									<td>${project.totalCommits}</td>
								</tr>
								
								<tr>
									<th>Total commiters</th>
									<td>${project.totalCommiters}</td>
								</tr>
								
								<tr>
									<th>First commit</th>
									<td><fmt:formatDate value="${project.firstCommit.date.time}" pattern="yyyy/MM/dd"/></td>
								</tr>
								
								<tr>
									<th>Last commit</th>
									<td><fmt:formatDate value="${project.lastCommit.date.time}" pattern="yyyy/MM/dd"/></td>
								</tr>
		
								<tr>
									<th>Tags</th>
									<td><input type="text" class="tags" name="tags" id="tags"
										value="${tags}" style="display: none;" /></td>
								</tr>
								
							</table>
						</div>
						<div class="detail-box">
							<h2>Top committers</h2>
							<table class="mm-table">
								<c:forEach items="${commiters}" var="a" varStatus="status">
									<tr>
										<td>#${status.index+1}</td>
										<td>${a.author.name} (${a.commits})</td>
										<td>${a.author.email}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<div id="charts">
					    <div id="commit_chart" class="googlechart"></div>
					    <div id="fileCount_chart" class="googlechart"></div>
				    </div>
			    </c:if>
			</metricminer:box>

			<div class="block">
				<div class="block_head">
					<div class="bheadl"></div>
					<div class="bheadr"></div>
					<h2>Scheduled Tasks</h2>
				</div>
				<!-- .block_head ends -->

				<div class="block_content">
					<table>
						<tr>
							<th></th>
							<th>Name</th>
							<th>Task Status</th>
							<th>Submited at</th>
							<th>Finished at</th>
						</tr>
						<c:forEach items="${project.tasks}" var="task">
							<tr>
								<td>
									#${task.position}
								</td>
								<td>
									${task.name}
								</td>
								<td class="${fn:toLowerCase(task.status)} task-status">
									${task.status}
								</td>
								<td>
									<fmt:formatDate value="${task.submitDate.time}" 
										pattern="yyyy/MM/dd - HH:mm:ss"/>
								</td>
								<td>
									<fmt:formatDate value="${task.endDate.time}" 
										pattern="yyyy/MM/dd - HH:mm:ss"/>
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div class="bendl"></div>
				<div class="bendr"></div>
			</div>
			<!-- .block ends -->
		</div>
		<!-- wrapper ends -->
	</div>
	<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
	<script src='<c:url value="/js/project/detail.js"/>'></script>
</body>
</html>