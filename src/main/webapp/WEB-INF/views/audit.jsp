<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Analytics Audit Tool</title>
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script> -->
	<script src="<c:url value="/resources/js/jquery-1.7.2.min.js"/>" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ajaxAudit.js"/>"></script>
</head>
<body>
	<c:url value="/get-analytics" var="getAnalytics"></c:url>
	<select id="accountSelector" name="account" onchange="getAnalytics(this);" data-url="${getAnalytics}">
		<option selected="selected" value="default">Select Account</option>		
		<c:forEach items="${accountList}" var="account">
			<option value="${account.accountName}" disabled>${account.accountName}</option>			
			<c:forEach items="${account.properties}" var="property">
				<option value="${property.propertyName}" disabled>${property.propertyName}</option>
				<c:forEach items="${property.views}" var="view">
					<option value="${account.accountId},${property.propertyId},${view.viewId}">${view.viewName}</option>
				</c:forEach>
			</c:forEach>
		    
		</c:forEach>
	</select>
	
	<div id="analytics">
	
	</div>
</body>
</html>