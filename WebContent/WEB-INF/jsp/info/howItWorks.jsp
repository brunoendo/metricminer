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
			
			<metricminer:box title="How it works?">
			<p>
				MetricMiner is a tool that aims to support researchers when mining software
				repository data. As a researcher, MetricMiner enables you to create code metrics,
				execute them in many software repositories, extract data, and even run statistical
				tests. Currently, MetricMiner contains all Apache software projects, plus many
				popular code metrics.
			</p>
			
			<p>
				In Figure below, we show the basic flow in MetricMiner. MetricMiner was developed in Java, 
				and can be deployed in any Java web container. Currently, the application is running in 
				Apache Tomcat. The tool also uses MySQL to store the data.
				
			</p>
			
			<div class="center">
				<img class="center" src="<c:url value="/images/info/flow.png"/>" />
			</div>
			
			<p>
			
				Internally, the tool uses a queue to organize the tasks it needs to execute. 
				Tasks represent the steps that MetricMiner takes to extract information from a 
				project: repository cloning, extract repository metadata, run code metrics, 
				and run a statistical test. The task system is extensible. In Figure below, 
				we show an UML diagram that represents the tasks. If one needs to create a new 
				task, s/he basically needs to create a concrete implementation of the interface.
				
			</p>
			
			<div class="center">
				<img  src="<c:url value="/images/info/uml-runner.png"/>" />
			</div>
				
			<p>
			
				Analogously, new code metrics can be inserted into MetricMiner. 
				Researchers only need to implement a set of interfaces, and the metric 
				will be ready to be executed in all source code. The interface is simple. 
				There are three methods that need to be implemented: one that calculates the 
				metric based on the source code, one that returns one (or many) results
				based on the calculation, and one that returns if that metric should be 
				executed for that file (Java metrics should run only in *.java files, for example).
				In Figure below, we exemplify it.
				
			</p>

			<div class="center">			
				<img class="center" src="<c:url value="/images/info/uml_metric.png"/>" />
			</div>
			
			</metricminer:box>
			
			<metricminer:box title="How should I use it?">
				<p>
					The first step is to create a query that extracts some information about projects
					that are already inside MetricMiner. To do that, go to <i>Data Extraction</i> menu,
					and create a query. There you can find a few examples or even the database diagram
					that explains our tables. After that, you can download the query in CSV format, and
					analyse it.
				</p>
				
				<p>
					You can also use MetricMiner to execute an statistical test. All you have to do
					is to go to <i>Statistical Test</i> menu, choose two datasets and the test. Then,
					MetricMiner will call R tool and save the results for you.
				</p>
				
				<p>
					If you need a specific project, you may add it using the <i>Projects</i> menu. There,
					all you have to do is to point a git address. You can also create a new code metric. To do
					that, you need to fork our project in Github and write the drivers for your metric. 
					The process is well-explained in this page. You also can always get in touch with us.
				</p>
			
			</metricminer:box>
			
			<metricminer:box title="How to Contribute">
				<p>
					MetricMiner is open source and freely available on Github. The repository
					can be found at <a href="https://github.com/metricminer-msr/metricminer">https://github.com/metricminer-msr/metricminer</a>.
					We will be very happy to accept your pull request and re-deploy the application with your new code.
				</p>
				
				<p>
					Throughout the code, you can see many example on how to implement metrics. As an example,
					the <a href="https://github.com/metricminer-msr/metricminer/tree/master/src/main/java/org/metricminer/tasks/metric/cc">cyclomatic complexity</a> metric. 
				</p>
				
				<p>
					If you have any doubts or ideas to share, join our 
					<a href="https://groups.google.com/forum/?hl=pt#!forum/metricminer">mailing list</a>.
				</p>
			
			</metricminer:box>
			

			
		</div>
		<!-- wrapper ends -->
	</div>
	<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>

</html>