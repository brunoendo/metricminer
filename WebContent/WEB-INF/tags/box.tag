<%@ attribute name="title" type="java.lang.String" %>
<%@ attribute name="extraClasses" type="java.lang.String" %>

<div class="block ${extraClasses}">
	<div class="block_head">
		<div class="bheadl"></div>
		<div class="bheadr"></div>
		<h2>${title}</h2>
	</div>
	<!-- .block_head ends -->

	<div class="block_content">
		<jsp:doBody />
	</div>
	<div class="bendl"></div>
	<div class="bendr"></div>
</div>
