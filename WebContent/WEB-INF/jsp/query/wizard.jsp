<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="metricminer" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<link rel="stylesheet" href="<c:url value='/js/codemirror/lib/codemirror.css' />" />
	<c:import url="../import/head.jsp" />
	<title>Metric Miner</title>
	<style>
	.hidden {
		display: none;
	}
	.example-details {
		margin-top: 15px;
	}
	#wizard .block_content {
		height: 150px;
	}
	</style>
</head>

<body>
	<div id="hld">
		<div class="wrapper">		<!-- wrapper begins -->
			<c:import url="../import/header.jsp" />
			
			<metricminer:box title="Wizard" boxId="wizard">
				<div class="wizard-step" id="step1">
					What kind of data you want to extract?
					<ul>
						<li>
							<a href="#" id="commit-query" data-nextstep="step2-commit">
								Commit data
							</a>
						</li>
						<li>
							<a href="#">
								Code metrics data
							</a>
						</li>
					</ul>
				</div>
				<div class="hidden" class="wizard-step" id="step2-commit">
					What entity do you want to relate?
					<ul>
						<li>
							<label>
								<input type="checkbox" class="step2" data-columns="cm.message" 
									data-entity="CommitMessage" data-alias="cm" data-joincolumn="message_id"
									data-commitjoincolumn="id"/>
									Commit message
							</label>
						</li>
						<li>
							<input type="checkbox" class="step2" data-columns="author.secret_name, author.secret_email" 
								data-entity="Author" data-alias="author" data-joincolumn="author_id"
								data-commitjoincolumn="id" />
							Author	
						</li>
						<li>
							<input type="checkbox" class="step2" data-columns="modification.kind as modification_kind, artifact.name, artifact.kind as artifact_kind" 
								data-entity="Modification" data-alias="modification" data-joincolumn="id"
								data-commitjoincolumn="commit_id" data-extrajoin="JOIN Artifact artifact ON modification.artifact_id=artifact.id"/>
								Modified artifacts
						</li>
					</ul>
				</div>
			</metricminer:box>
			
			<metricminer:box title="Execute SQL Query">
				<c:if test="${!empty errors}">
					<div class="message errormsg">
						<p>
							<c:forEach var="error" items="${errors}">
						    	${error.category} - ${error.message}<br />
							</c:forEach>
						</p>
					</div>
				</c:if>
				
				<form method="post" action="${linkTo[QueryController].save}">
					<p>
						<label for="query-name">Query name: </label> <br />
						<input id="query-name" type="text" value="${query.name}" class="text small" name="query.name" />
					</p>
					<p>
						<label for="sqlQuery">SQL Query: </label> <br />
						<div id="sql-query">
							<span id="placeholder">loading editor...</span>
							<textarea name="query.sqlQuery" id="sqlQuery"></textarea>
						</div>
					</p>
					<p class="note">
						You can see the database schema <a target="_blank" href="<c:url value='/images/erd.png' />">here</a>.
					</p>
					<p class="note">
						Please, do not get the source code. We are trying to convince industry to put 
						their projects here. So, never try to get their code. Be honest!
					</p>
					<p>
						<input type="submit" class="submit long" value="Save and execute" />
					</p>
				</form>
			</metricminer:box>
		</div>						<!-- wrapper ends -->
	</div>		<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
	<script src="<c:url value='/js/codemirror/lib/codemirror.js' />"></script>
	<script src="<c:url value='/js/codemirror/mode/sql/sql.js' />"></script>
	<script src="<c:url value='/js/wizard.js' />"></script>
</body>
</html>

