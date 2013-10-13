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
	#example-editor {
		height: 300px;
		border: 1px solid #CCC;
		border-radius: 0;
		width: 800px;
	}
	.example-details {
		margin-top: 15px;
	}
	</style>
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
				
				<form method="post" action="${linkTo[QueryController].save}">
					<p>
						<label for="query-name">You can start with one of our <a href="#examples">examples</a>: </label> <br />
						<select id="query-examples">
							<option>Select a example</option>
							<c:forEach var="example" items="${examples}">
								<option value="${example.query}">
									${example.name}
								</option>
							</c:forEach>
						</select>
					</p>
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
			<c:if test="${not empty examples}">
				<metricminer:box title="Examples" boxId="examples">
					<c:set var="firstExample" value="${examples.get(0)}" />
					<select id="query-examples-details">
						<c:forEach var="example" items="${examples}">
							<option data-query="${example.query}" data-description="${example.description}">
								${example.name}
							</option>
						</c:forEach>
					</select>
					<div class="example-details">
						<h2>${firstExample.name}</h2>
						<p class="description">${firstExample.description}</p>
						<textarea id="example-editor">${firstExample.query}</textarea>
					</div>
				</metricminer:box>
			</c:if>
		</div>						<!-- wrapper ends -->
	</div>		<!-- #hld ends -->
	<c:import url="../import/footer.jsp" />
	<c:import url="../import/javascripts.jsp" />
	<script src="<c:url value='/js/codemirror/lib/codemirror.js' />"></script>
	<script src="<c:url value='/js/codemirror/mode/sql/sql.js' />"></script>
	<script>
	var editor = CodeMirror.fromTextArea(document.getElementById("sqlQuery"), {
		mode: "text/x-mariadb",
		lineNumbers: true
	});
	var exampleEditor = CodeMirror.fromTextArea(document.getElementById("example-editor"), {
		mode: "text/x-mariadb",
		lineNumbers: true
	});
	$(function() {
		$("#placeholder").html("");
		$("#query-examples").change(function() {
			var selected = $("#query-examples option:selected");
			var name = selected.text().trim();
			if (name != "Select a example") {
				$("#query-name").val(name);
				editor.setValue(selected.val());
			}
		});
		$("#query-examples-details").change(function() {
			var selected = $("#query-examples-details option:selected");
			var name = selected.text().trim();
			var query = selected.data("query");
			var description = selected.data("description");
			$(".example-details h2").text(name);
			$(".example-details .description").text(description);
			exampleEditor.setValue(query);
		});
	});
	</script>
</body>
</html>

