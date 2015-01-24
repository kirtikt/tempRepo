<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
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
<script type="text/javascript">      
	jQuery(document)
			.ready(
					function($) {
						$('#flexme1').flexigrid();
						$('#d1').show();
						$('#d2').hide();
						$("#datepicker1").datepicker();
						$("#datepicker2").datepicker();
						$("input:radio[name=day]")
								.click(
										function() {
											var value = $(this).val();

											if (value == "month") {

												$('#d1').hide();
												$('#d2').show();

												$("#datepicker3")
														.datepicker(
																{
																	dateFormat : 'mm/dd/yy',
																	changeMonth : true,
																	changeYear : true,
																	showButtonPanel : true,

																	onClose : function(
																			dateText,
																			inst) {
																		var month = $(
																				"#ui-datepicker-div .ui-datepicker-month :selected")
																				.val();
																		var year = $(
																				"#ui-datepicker-div .ui-datepicker-year :selected")
																				.val();
																		$(this)
																				.val(
																						$.datepicker
																								.formatDate(
																										'mm/dd/yy',
																										new Date(
																												year,
																												month,
																												1)));
																	}
																});
												$("#datepicker3")
														.focus(
																function() {
																	$(
																			".ui-datepicker-calendar")
																			.hide();
																	$(
																			"#ui-datepicker-div")
																			.position(
																					{
																						my : "center top",
																						at : "center bottom",
																						of : $(this)
																					});
																});
												$("#datepicker4")
														.datepicker(
																{
																	dateFormat : 'mm/dd/yy',
																	changeMonth : true,
																	changeYear : true,
																	showButtonPanel : true,

																	onClose : function(
																			dateText,
																			inst) {
																		var month = $(
																				"#ui-datepicker-div .ui-datepicker-month :selected")
																				.val();
																		var year = $(
																				"#ui-datepicker-div .ui-datepicker-year :selected")
																				.val();
																		$(this)
																				.val(
																						$.datepicker
																								.formatDate(
																										'mm/dd/yy',
																										new Date(
																												year,
																												month,
																												1)));
																	}
																});
												$("#datepicker4")
														.focus(
																function() {
																	$(
																			".ui-datepicker-calendar")
																			.hide();
																	$(
																			"#ui-datepicker-div")
																			.position(
																					{
																						my : "center top",
																						at : "center bottom",
																						of : $(this)
																					});
																});
											} else {
												$('#d1').show();
												$('#d2').hide();
											}
										});
						jQuery
						.ajax({
							type : "POST",
							url : "/payless4sms/ClientList",
							async : "false",
							jsonEncode : true,
							success : function(k) {
								//alert(k);
								var options = '<option value="">--Select Client--</option>';
								for ( var key in k) {
									if (k.hasOwnProperty(key)) {
										//alert(key + " -> " + k[key]);
										options += '<option value="' + key
			+ '">'
												+ k[key] + '</option>';
									}
								}

								$("#manageriddrop").html(options);
							},
							error : function(errorThrown) {
							}
						});
					});
</script>
<script type="text/javascript">
	function radio_button_checker() {
		var radio_choice = false;
		for (counter = 0; counter < document.radioform.day.length; counter++) {
			if (document.radioform.day[counter].checked)
				radio_choice = true;
		}
		if (!radio_choice) {
			alert("Please select a day or month.")
			return (false);
		}
		return (true);
	}
	function changeHiddenInput(objDropDown) {
		var managername = objDropDown.options[objDropDown.selectedIndex].text;
		document.getElementById("managername").value = managername;
	}
	function setValue() {
		document.getElementById("isDownloaded").value = "true";
	}
</script>

<title><%@ include file="/title.jsp"%></title>
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
						<c:if test="${not empty requestScope.map}">
 							<form action="/payless4sms/DownloadReportAPIByMap" method="post"
								id="formid">
								 <input type="submit" value="Download" />
							</form>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>
							<form action="/payless4sms/DetailedSummaryReport" method="post"
								id="radioform" onsubmit="return radio_button_checker();"
								name="radioform">
								<div>
									Select Client ::<select name="managerid" id="manageriddrop" onchange="changeHiddenInput(this)">
									</select> <input type="hidden" id="managername" name="managername" />
								</div>
								<%
									Calendar d = new GregorianCalendar();
									String date = (d.get(Calendar.MONTH) + 1) + "/"
											+ d.get(Calendar.DATE) + "/" + d.get(Calendar.YEAR);
								%>
								<div id="d1" style="display: none;">
									From: <input name="fromDate" id="datepicker1"
										value="<%if (request.getAttribute("fromDate") != null) {%><%=request.getAttribute("fromDate")%><%} else {%><%=date%><%}%>" />
									<br> To:&nbsp;&nbsp;&nbsp; <input name="toDate"
										id="datepicker2"
										value="<%if (request.getAttribute("toDate") != null) {%><%=request.getAttribute("toDate")%><%} else {%><%=date%><%}%>" />
									<br>
								</div>
								<div id="d2" style="display: none;">
									<input type="hidden" name="isDownloaded" id="isDownloaded"
										value="false" /> From: <input name="fromDate1"
										id="datepicker3"
										value="<%if (request.getAttribute("fromDate") != null) {%><%=request.getAttribute("fromDate")%><%} else {%><%=date%><%}%>" /><br>

									To:&nbsp;&nbsp;&nbsp; <input name="toDate1" id="datepicker4"
										value="<%if (request.getAttribute("toDate") != null) {%><%=request.getAttribute("toDate")%><%} else {%><%=date%><%}%>" />
									<br>
								</div>
								<input type="radio" name="day" value="day"
									<%if ("day".equalsIgnoreCase((String) request.getAttribute("type"))) {%>
									<%="checked"%> <%}%>> Day <input type="radio"
									name="day" value="month"
									<%if ("month".equalsIgnoreCase((String) request.getAttribute("type"))) {%>
									<%="checked"%> <%}%>> Month <br> <input
									type="hidden" name="managerid" /> <input type="submit"
									value="View" id="view" />
								
							</form>
						</td>
					</tr>
					<tr>
						<c:if test="${not empty requestScope.fromDate}">
							<tr style="font-size: 16px">
								<td><b>Search Results</b></td>
							</tr>
							<tr style="font-size: 14px">
								<td><b>Report from ${requestScope.fromDate} to
										${requestScope.toDate}</b></td>
							</tr>
							<c:if test="${not empty requestScope.map}">
								<tr>
									<td>
										<table border='1' id='flexme1' width='100%'>
											<tr>
												<th>Number</th>
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