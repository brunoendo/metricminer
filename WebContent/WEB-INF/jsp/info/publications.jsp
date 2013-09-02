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
			
			<metricminer:box title="Publications about MetricMiner">
			
				<ul>
				
					<li>
					<span class="publication-authors">Sokol, Francisco; Aniche, Mauricio; Gerosa, Marco</span>. MetricMiner: Supporting Researchers in
					Mining Software Repositories. 13th IEEE International Working Conference on Source Code Analysis and Manipulation, 2013.
					</li>
					
					<li>
					<span class="publication-authors">Sokol, Francisco</span>. 
					MetricMiner: Uma ferramenta de apoio à mineração de repositório de código. 
					Trabalho de conclusão de curso. 2012.
					</li>
				</ul>
			
			</metricminer:box>

			<metricminer:box title="Publications that have made use of MetricMiner">
			
				<ul>
					<li>
						<span class="publication-authors">Sokol, Francisco; Aniche, Mauricio; Gerosa, Marco</span>.
						Does the Act of Refactoring Really Make Code Simpler? A Preliminary Study.
						4o Workshop Brasileiro de Métodos Ágeis, 2013.
					</li>

					<li>
						<span class="publication-authors">Aniche, Mauricio; Gerosa, Marco</span>.
						What Do The Asserts in a Unit Test Tell Us About Code Quality? A Study on Open Source and Industrial Projects.
						17th European Conference on Software Maintenance and Reengineering, 2013.
					</li>

					<li>
						<span class="publication-authors">Aniche, Mauricio; Gerosa, Marco</span>.
						How the Practice of TDD Influences Class Design in Object-Oriented Systems: Patterns of Unit Tests Feedback.
						SBES: Simpósio Brasileiro de Engenharia de Software, 2012.
					</li>
				
				</ul>
			</metricminer:box>
			
			<metricminer:box title="How to Cite MetricMiner">
				Under construction.
			</metricminer:box>
			
			
		</div>
		<!-- wrapper ends -->
	</div>
	<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
</body>

</html>