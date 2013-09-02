<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
					<h2>Execute statiscal test</h2>
				</div>		<!-- .block_head ends -->
				
				
				<div class="block_content">
				
					<p>
					MetricMiner enables you to execute statistical tests and compare two different
					groups. In order to do it, you need to choose two datasets that have been already
					created and executed. These datasets should only contain a single column.
					</p>
					
					<p>
						If you have a doubt chosing a statistical test, 
						<a target="_blank" href="http://www.graphpad.com/support/faqid/1790/">this page</a> can help.
					
					</p>
				
					<form method="post" action="${linkTo[StatisticalTestController].addStatisticalTestExecution}">
					
						<p>
							<label for="name">Name</label> <br />
							<input type="text" name="name" class="text small" />
						</p>
						<p>
							<label for="firstQueryResultId">First dataset</label> <br />
							<select name="firstQueryResultId" class="stat-dataset-select">
								<c:forEach var="result" items="${results}">
									<option value=${result.id}>${result.query.name} (${result.id})</option>
								</c:forEach>
							</select>
							<span class="note">*This should be a single column dataset</span>
						</p>
						<p>
							<label for="secondQueryResultId">Second dataset</label> <br />
							<select name="secondQueryResultId" class="stat-dataset-select">
								<c:forEach var="result" items="${results}">
									<option value=${result.id}>${result.query.name} (${result.id})</option>
								</c:forEach>
							</select>
							<span class="note">*This should to be a single column dataset</span>
						</p>
						<p>
							<label for="statisticalTestId">Statistical test</label> <br />
							<select name="statisticalTestId" class="stat-dataset-select">
								<c:forEach var="test" items="${tests}">
									<option value=${test.id}>${test.name}</option>
								</c:forEach>
							</select>
						</p>
					
						<p>
							<input type="submit" class="submit long" value="Save and execute" />
						</p>
						
					</form>
					
					
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

