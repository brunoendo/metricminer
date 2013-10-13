var editor = CodeMirror.fromTextArea(document.getElementById("sqlQuery"), {
		mode: "text/x-mariadb",
		lineNumbers: true
	});
	$(function() {
		$("#placeholder").html("");
		
		var fieldsIndex = 0;
		
		$(".wizard-step a").click(function(e) {
			var link = $(this);
			var nextstepId = link.data("nextstep");
			$(this).parent().parent().parent().hide();
			$("#"+nextstepId).fadeIn(600);
			return false;
		});
		
		$("#commit-query").click(function(e) {
			var fields = "SELECT commit.commitId, commit.date";
			editor.setValue(fields + " FROM Commit commit");
			fieldsIndex = fields.length;
			return false;
		});
		
		$(".step2").change(function(e){
			var checkbox = $(this);
			checkbox.attr("disabled", "true");
			var query = editor.getValue();
			var fields = query.substring(0, fieldsIndex);
			var joins = query.substring(fieldsIndex, query.lenght);
			fields += ", " + checkbox.data("columns");
			fieldsIndex = fields.length;
			
			var joincolumn = checkbox.data("joincolumn");
			var extrajoin = checkbox.data("extrajoin");
			var commitjoincolumn = checkbox.data("commitjoincolumn");
			var entity = checkbox.data("entity");
			var alias = checkbox.data("alias");
			
			var join = " \nJOIN " + entity + " " + alias + " ON commit." + joincolumn + "=" + alias + "." + commitjoincolumn;
			if (extrajoin) {
				join += " \n" + extrajoin;
			}
			
			query = fields + joins + join;
			editor.setValue(query);
		});
		
		
	});
