<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
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
	//         
	jQuery(document).ready(function($) {
		$('#flexme1').flexigrid();
		$("#datepicker1").datepicker();
		$("#datepicker2").datepicker();
	});
	//
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
	<%
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("//payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
	%>
	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="2">
				<!-- header content --> <%@ include file="/header.jsp"%>
			</td>

		</tr>
		<tr>
			<td colspan="2">
				<!-- header content --> <%@ include file="/menu.jsp"%>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/navigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->
				<table width="70%" align="center">

					<tr>
						<td>

							<form action="/payless4sms/WriteTOExcel" method="post"
								id="formid">
								<input type="hidden" name="reportType" value="simplereport" /> <input
									type="submit" value="ExportToExcel" />
							</form></td>
					</tr>
					<tr>
						<td>
							<form action="/payless4sms/SimpleReportRedirection"
								method="post">
								<!-- id="formid" name="form" onSubmit="return checkDayMonth();"> -->
								<table>
									<tr>
										<%
											Calendar d = new GregorianCalendar();
											String date = (d.get(Calendar.MONTH) + 1) + "/"
													+ d.get(Calendar.DATE) + "/" + d.get(Calendar.YEAR);
										%>
										<td>From: <input name="fromDate" id="datepicker1"
											value="<%=date%>" /> <%
 	HttpSession httpsession = request.getSession(false);
 	long managerid = (Long) httpsession.getAttribute("clientmanagerId");
 %> <input type="hidden" name="managerid" value=<%=managerid%>>
										</td>
									</tr>
									<tr>
										<td>To:&nbsp;&nbsp;&nbsp; <input name="toDate"
											id="datepicker2" value="<%=date%>" /></td>
									</tr>
									<tr>
										<td>Select record per page::</td>
										<td><select id="recordperpage" name="recordperpage">
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="40">40</option>
												<option value="50">50</option>
										</select>
										</td>
									</tr>
									<tr>
										<td><input type="submit" value="getreport" id="getreport" />
										</td>
									</tr>
								</table>
							</form></td>
					</tr>
					<tr>
						<td>
							<%
								String fromsmreport = (String) request.getSession(false)
										.getAttribute("fromsmreport");
								String tosmreport = (String) request.getSession(false)
										.getAttribute("tomsmreport");

								if (fromsmreport != null && tosmreport != null) {
									int pageNumber = 1;
									int recordperpage = (Integer) request.getSession(false)
											.getAttribute("recordperpage");
									int count=0;
									
									if (request.getParameter("page") != null) {
										pageNumber = Integer.parseInt(request.getParameter("page"));
										count = recordperpage * (pageNumber - 1);
									}

									long clientmanagerId = (Long) request.getSession(false)
											.getAttribute("clientmanagerId");

									List<Sms> smsList = new SmsManager().getSmsList("status", "11",
											clientmanagerId, pageNumber, fromsmreport, tosmreport,
											recordperpage);
									if ((smsList != null && smsList.size() != 0) || pageNumber > 1) {

										out.print("<table border=\"1\" id='flexme1' width='100%'>");

										out.print("<tr><th>sr.no.</th><th>destination</th><th>status</th><tr>");
										if (smsList.size() == 0) {
											out.println("<font size='2'><b> Record not found. </b> </font>");
										}
										Iterator<Sms> it = smsList.iterator();
										Helper hp = new Helper();
										while (it.hasNext()) {
											Sms sms = (Sms) it.next();
											String st = hp.getSmsStatus(sms.getSmsid(), "c");
											count++;
											out.print("<tr><td>" + count + "</td><td>"
													+ sms.getDestination()
													+ "</td><td><font color='#5DAC0E'>" + st
													+ "</font></td><tr>");

										}
										out.print("</table>");
										if (pageNumber > 1) {
											out.println("<a href='/payless4sms/reports/simplereport.jsp?page="
													+ (pageNumber - 1)
													+ "'>Previous</a>  &nbsp;&nbsp; |");
										}
										out.println("<a href='/payless4sms/reports/simplereport.jsp?page="
												+ (pageNumber + 1) + "'>Next</a>");

									} else {
										out.println("<font size='2'><b> Record not found. </b> </font>");
									}

								}
							%>
						</td>
					</tr>

				</table> <br /> <br /> <!-- End page content in right pane --></td>
		</tr>
		<tr>
		</tr>
	</table>
</body>
</html>


