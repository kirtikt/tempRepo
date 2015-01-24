<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title><%@ include file="/title.jsp"%></title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
 <script type="text/javascript">jQuery.noConflict();</script>
 <link rel="stylesheet" type="text/css" href="/payless4sms/css/flexigrid.css" media="all" />
  <script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>
 <script type="text/javascript">
                //<![CDATA[
                jQuery(document).ready(function($) {
                        $('#flexme1').flexigrid();
                });
                //]]>
                </script>
</head>
<body>
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
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/adminnavigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top" align="center" width="80%">
				<!-- Page content in right pane -->
				<table border='1' id='flexme1' width='100%'> <tr><td>
				 Number of message In Queue </td> <td> <c:out
					value="${queueControl.getMessageCount()}" /> </td></tr>
				<tr><td>DeliveringCount_: </td> <td><c:out
					value="${queueControl.getDeliveringCount()}" /> </td></tr> <tr><td>Messages
				Added_ </td> <td> <c:out value="${queueControl.getMessagesAdded()}" /> </td></tr><br>
		<!--  		Messages Added_ <c:out value="${queueControl.getScheduledCount()}" />
				<br>Counter History _: <c:out
					value="${queueControl.listMessageCounterHistory()}" /> <br> --> 
				<tr><td>Number of Expire Mesage </td><td><c:out
					value="${queueControl.expireMessages('')}" /> </td></tr> <tr><td> Number of
				Removed Mesage </td> <td> <c:out value="${queueControl.removeMessages('')}" />
				</td></tr></table>
				</td>
		</tr>
		<tr>

		</tr>
	</table>
</body>
</html>


