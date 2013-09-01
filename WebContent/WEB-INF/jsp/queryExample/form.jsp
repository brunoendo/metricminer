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
</head>

<body>
	<div id="hld">
		<div class="wrapper">		<!-- wrapper begins -->
			<c:import url="../import/header.jsp" />
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
				<form method="post" action="${linkTo[QueryExampleController].save}">
					<p>
						<label for="query.name">Example name: </label> <br />
						<input type="text" class="text small" name="example.name" />
					</p>
					<p>
						<label for="query-name">Description: </label> <br />
						<textarea id="query-name" class="text small" name="example.description"></textarea>
					</p>
					<p>
						<label for="sqlQuery">SQL Query: </label> <br />
						<div id="sql-query">
							<span id="placeholder">loading editor...</span>
							<textarea name="example.query" id="sqlQuery"></textarea>
						</div>
					</p>
					<p>
						<input type="submit" class="submit long" value="Save" />
					</p>
				</form>
			</metricminer:box>
		</div>						<!-- wrapper ends -->
	</div>		<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
	<script src="<c:url value='/js/codemirror/lib/codemirror.js' />"></script>
	<script src="<c:url value='/js/codemirror/mode/sql/sql.js' />"></script>
	<script>
	$(function() {
	  $("#placeholder").html("");
	});
	  var editor = CodeMirror.fromTextArea(document.getElementById("sqlQuery"), {
	    mode: "text/x-mariadb",
	    lineNumbers: true,
	  });
	</script>
</body>
</html>

