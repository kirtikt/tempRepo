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
	jQuery(document)
			.ready(
					function($) {

						$('#flexme1').flexigrid();
						$("#datepicker1").datepicker();
						$("#datepicker1").datepicker("setDate", new Date());
						$("#datepicker2").datepicker();
						$("#datepicker2").datepicker("setDate", new Date());

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

	function changeHiddenInput(objDropDown) {
		var managername = objDropDown.options[objDropDown.selectedIndex].text;
		document.getElementById("managername").value = managername;
	}

	function hideDiv() {
		document.getElementById("clientOp").style.visibility = "hidden";
	}

	function showDiv() {
		document.getElementById("clientOp").style.visibility = "visible";
	}

	function sortList() {
		document.getElementById("sortColumn").value = "System Speed";
		document.form2.fromDate.value = document.form1.fromDate.value;
		document.form2.toDate.value = document.form1.toDate.value;
		if (document.getElementById("sortD").checked) {
			document.getElementById("sortType").value = "desc";
		} else {
			document.getElementById("sortType").value = "asc";
		}
	}
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
					<tr>
						<td>
							<form name="form1" action="/payless4sms/SpeedReport"
								method="post">
								<!-- id="formid" name="form" onSubmit="return checkDayMonth();"> -->
								<table>
									<tr>
										<td><input type="radio" name="client" id="client"
											value="0" checked onClick="hideDiv()" />All Clients</td>
										<td><input type="radio" name="client" id="client"
											value="1" onClick="showDiv()" />Single Client</td>
									</tr>

									<tr>

										<td colspan='2'>
											<div id=clientOp style="visibility: hidden;">
												Select Client : <select name="managerid" id="manageriddrop"
													onchange="changeHiddenInput(this)">
												</select> <input type="hidden" id="managername" name="managername" />
											</div>
										</td>
									</tr>

									<tr>
										<td colspan="2">Select Date</td>
									</tr>
									<tr>
										<td>From :</td>
										<td><input name="fromDate" id="datepicker1"
											value="<c:out value="${param.fromDate}"/>" /></td>
									</tr>
									<tr>
										<td>To :</td>
										<td><input name="toDate" id="datepicker2"
											value="<c:out value="${param.toDate}" />" /></td>
									</tr>
									<tr>
										<td colspan=2 align="center"><input type="submit"
											value="Get Batches" id="getreport" /></td>
									</tr>
								</table>
							</form>
						</td>
					</tr>

					<c:if test="${not empty requestScope.fromDate}">
						<tr style="font-size: 16px">
							<td><b>Search Results</b></td>
						</tr>
						<tr style="font-size: 14px">
							<td><b>Batches sent from ${requestScope.fromDate} to
									${requestScope.toDate}</b></td>
						</tr>
						<c:if test="${not empty requestScope.map}">
							<tr>
								<td colspan="2">
									<form name="form2" action="/payless4sms/SortMap"
										method="post">
										<input type="hidden" id="sortColumn" name="sortColumn" /> <input
											type="hidden" id="sortType" name="sortType" /> <input
											type="hidden" id="fromDate" name="fromDate" /> <input
											type="hidden" id="toDate" name="toDate" /> <input
											type="radio" name="sortOp" id="sortA" value="0"
											onClick="sortList()"
											<c:if test="${'asc' eq requestScope.sortType}"> checked </c:if> />Sort
										by System Speed Asc <input type="radio" name="sortOp"
											id="sortD" value="1" onClick="sortList()"
											<c:if test="${'desc' eq requestScope.sortType}"> checked </c:if> />Sort
										by Final Speed Desc <input type="submit" value="Sort" />
									</form>
								</td>
							</tr>
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
													<!-- The string 'Batch Id' is the name of a header in the map -->
													<td><c:if test="${'Batch Id' eq innerMap.key}">
															<a
																href="/payless4sms/SummaryBatchReport?batchId=${innerMap.value}">
																<c:out value="${innerMap.value}" />
															</a>
														</c:if> <c:if test="${'Batch Id' ne innerMap.key}">
															<c:out value="${innerMap.value}" />
														</c:if> <c:if test="${empty innerMap.value}">
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
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
	</table>
</body>
</html>