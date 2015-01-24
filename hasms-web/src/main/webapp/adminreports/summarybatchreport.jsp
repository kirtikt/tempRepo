<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<title><%@ include file="/title.jsp"%></title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
</script>
<link rel="stylesheet" type="text/css"
	href="/payless4sms/css/flexigrid.css" media="all" />
<script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>
</head>

<script type="text/javascript">
	
</script>
<style type="text/css">
/*demo page css*/
body {
	font: 75.5% "Trebuchet MS", sans-serif;
}

.demoHeaders {
	margin-top: 2em;
}

#dialog_link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog_link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

ul#icons {
	margin: 0;
	padding: 0;
}

ul#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

ul#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}
</style>

<body>
	<jsp:useBean id="haId" scope="request"
		class="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Holdingaccount" />
	<c:if
		test='${empty sessionScope.isActive || sessionScope.isActive != true}'>
		<c:redirect
			url="/payless4sms/adminLogin.jsp?errorMessage=please login" />
	</c:if>
	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="2">
				<!-- header content --> <%@ include file="/header.jsp"%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- header content --> <%@ include file="/adminmenu.jsp"%>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/adminnavigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->
				<table width="70%" align="center">
					<c:if test="${not empty requestScope.haId}">
						<tr style="font-size: 14px">
							<td><b>Batch Summary</b></td>
						</tr>
						<c:if test="${not empty requestScope.haId}">
							<tr>
								<td>
									<table border='1' cellpadding="2" id='flexme1' width='100%'>
										<tr>
											<td>Batch Id</td>
											<td>${haId.pkeyholdingaccountid}</td>
										</tr>
										<tr>
											<td>Client Name</td>
											<td>${haId.clientmanager.sysuser.username}</td>
										</tr>
										<tr>
											<td>Date & Time</td>
											<td>${haId.timedate}</td>
										</tr>
										<tr>
											<td>SMSs loaded</td>
											<td>${haId.countofcellno}</td>
										</tr>
										<tr>
											<td>SMSs delivered to mobile network</td>
											<td>${haId.smssentcount}</td>
										</tr>
										<tr>
											<td>Credits Used</td>	
											<td>${haId.smssentcount * haId.smsrate}</td>
										</tr>
										<tr>
											<td>Message</td>
											<td>${requestScope.message}</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
						<c:if test="${empty requestScope.haId}">
							<tr>
								<td>No Records Found</td>
							</tr>
						</c:if>
					</c:if>
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
	</table>
</body>
</html>