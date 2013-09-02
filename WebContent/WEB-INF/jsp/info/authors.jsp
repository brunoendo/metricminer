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
			
			<metricminer:box title="Authors">
				<p>
					MetricMiner was created and developed by master and PhD students in the
					Departament of Computer Science, at University of São Paulo.				
				</p>
				
				<p>
					You can find its developers in the list below.
				</p>
				
				<ul>
					<li>Francisco Sokol (francisco.sokol at usp.br)</li>
					<li>Mauricio Aniche (aniche at ime.usp.br)</li>
					<li>Marco Aurélio Gerosa (gerosa at ime.usp.br)</li>
				</ul>
			</metricminer:box>
			
		</div>
		<!-- wrapper ends -->
	</div>
	<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>

</html>