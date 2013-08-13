jQuery.fn.exists = function(){return this.length>0;};


//$(function() {
//	var topCommitersChart = $("#top-committers");
//	if (topCommitersChart.size() == 1) {
//		alert("asdasd");
//		google.load("visualization", "1", {
//			packages : [ "corechart" ]
//		});
//		google.setOnLoadCallback(drawChart);
//		function drawChart() {
//			var data = google.visualization.arrayToDataTable([
//					[ 'Task', 'Hours per Day' ], [ 'Work', 11 ], [ 'Eat', 2 ],
//					[ 'Commute', 2 ], [ 'Watch TV', 2 ], [ 'Sleep', 7 ] ]);
//
//			var options = {
//				title : 'My Daily Activities'
//			};
//
//			var chart = new google.visualization.PieChart(topCommitersChart);
//			chart.draw(data, options);
//		}
//	}
//});

function pieChart(element, data, title) {
	var gdata = google.visualization.arrayToDataTable(data);
	var options = {
		title : title
	};
	var chart = new google.visualization.PieChart(element);
	chart.draw(gdata, options);
}

function commitersChart() {
	if ($('#top-committers').exists()) {
		$.ajax({
			url : CONTEXT_ROOT + "/commits/byAuthor",
			dataType: "json",
			success : function(json) {
				var el = document.getElementById('top-committers');
				pieChart(el, json.data, 'Most active committers');
			}
		});
	}
}

function googleChartsReady() {
	$(function() {
		commitersChart();
	});
}

google.load("visualization", "1", {
	packages : [ "corechart" ]
});
google.setOnLoadCallback(googleChartsReady);
