<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />

<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
</script>
<link rel="stylesheet" type="text/css"
	href="/payless4sms/css/flexigrid.css" media="all" />
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>

<script type="text/javascript">
	function checkDayMonth() {
		var fromDate1 = document.getElementById("datepicker1").value;
		var fromDate = fromDate1.split("/")[1];
		var fromMonth = fromDate1.split("/")[0];
		var fromYear = fromDate1.split("/")[2];
		//alert("From date"+fromDate);
		var toDate1 = document.getElementById("datepicker2").value;
		var toDate = toDate1.split("/")[1];
		var toMonth = toDate1.split("/")[0];
		var toYear = toDate1.split("/")[2];
		len = document.form.day.length;
		for ( var i = 0; i < len; i++) {
			if (document.form.day[i].checked) {
				chosen = document.form.day[i].value;
			}
		}
		if (chosen == "month") {
			if (!(toYear == fromYear)) {
				alert("please select same year");
				return false;
			}
			return true;
		} else {
			if (!(toMonth == fromMonth)) {
				alert("please select same month");
				return false;
			}
			return true;
		}
	}
</script>
<script type="text/javascript">
	//         
	jQuery(document)
			.ready(
					function($) {
						$('#flexme1').flexigrid();
						$("#datepicker1").datepicker();
						$("#datepicker1").datepicker("setDate", new Date());
						$("#datepicker2").datepicker();
						$("#datepicker2").datepicker("setDate", new Date());

						$('#btnLogin').click(function() {
							$('#loader').show();
							$('#btnLogin').hide();
						});

						jQuery
								.ajax({
									type : "POST",
									url : "/payless4sms/SendingInterfaceList",
									async : "false",
									jsonEncode : true,
									success : function(k) {
										//alert(k);
										var options = '<option value="">--Select Sending Interface--</option>';
										for ( var key in k) {
											if (k.hasOwnProperty(key)) {
												//alert(key + " -> " + k[key]);
												options += '<option value="' + key
 			+ '">'
														+ k[key] + '</option>';
											}
										}

										$("#sendinginterfaceid").html(options);
									},
									error : function(errorThrown) {
									}
								});

					});
	//   

	function changeHiddenInput(objDropDown) {
		var smscname = objDropDown.options[objDropDown.selectedIndex].text;
		document.getElementById("smscname").value = smscname;
	}
</script>

<style type="text/css" media="screen">
<!--
DIV#loader {
	height: 20px;
	overflow: hidden;
}

DIV#loader.loading {
	background: url(/payless4sms/images/spinner.gif) no-repeat center
		center;
}
-->
</style>

<style type="text/css">
/*demo page css*/
body {
	font: 62.5% "Trebuchet MS", sans-serif;
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
<title><%@ include file="/title.jsp"%></title>
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
			<td valign="top">
				<table width="70%" align="center">
					<tr>
						<td>
							<form action="/payless4sms/SendingInterfaceSummaryReport"
								method="post" id="formid" name="form"
								onSubmit="return checkDayMonth();">
								<table>
									<tr>
										<td>Select Sending Interface :</td>
										<td><select name="sendinginterfaceid" id="sendinginterfaceid" onchange="changeHiddenInput(this)">
										</select> <input type="hidden" id="smscname" name="smscname" /></td>
									</tr>
									<tr>
										<td>From :</td>
										<td><input name="fromDate" id="datepicker1"
											<c:if test="${not empty requestScope.fromDate}">
											value = ${requestScope.fromDate}
											</c:if>
											/>
										</td>
									</tr>
									<tr>
										<td>To:</td>
										<td><input name="toDate" id="datepicker2"
											<c:if test="${not empty requestScope.toDate}">
											value = ${requestScope.toDate}
											</c:if>
											/>
										</td>
									</tr>
									<tr>
										<td colspan="3"><input type="radio" name="day"
											value="day"> Day <input type="radio" name="day"
											value="month" checked="checked"> Month</td>
									</tr>
									<tr>
										<td colspan="3"><input type="submit" value="View"
											id="btnLogin" style="display: block;" />
											<div style="display: none;" id="loader" class="loading"></div></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
					<tr>
						<c:if test="${not empty requestScope.fromDate}">
							<tr style="font-size: 16px">
								<td><b>Search Results</b></td>
							</tr>
							<tr style="font-size: 14px">
								<td><b>Sending Interface : ${param.smscname} Report
										from ${requestScope.fromDate} to ${requestScope.toDate}</b></td>
							</tr>
							<c:if test="${not empty requestScope.map}">
								<tr>
									<td>
										<table border='1' id='flexme1' width='100%'>
											<tr>
												<th>Record</th>
												<c:forEach items="${requestScope.map}" var="outerMap"
													varStatus="status" begin="0" end="0">
													<c:forEach items="${outerMap.value}" var="innerMap">
														<th><c:out value="${innerMap.key}" /></th>
													</c:forEach>
												</c:forEach>
											</tr>
											<c:forEach items="${requestScope.map}" var="outerMap"
												varStatus="status">
												<tr>
													<td><c:out value="${outerMap.key}" /></td>
													<c:forEach items="${outerMap.value}" var="innerMap">
														<td><c:out value="${innerMap.value}" /> <c:if
																test="${empty innerMap.value}">
																<c:out value="0" />
															</c:if></td>
													</c:forEach>
												</tr>
											</c:forEach>
										</table>
									</td>
								</tr>
							</c:if>
							<c:if test="${empty requestScope.map}">
								<tr>
									<td>No Records Found</td>
								</tr>
							</c:if>
						</c:if>
					</tr>
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
	</table>
</body>
</html>