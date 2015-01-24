<%@page import="org.hibernate.Session"%>

<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.SysuserManager,com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Iterator"%>
<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp"%></title>
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	function showDiv() {
		document.getElementById("reportdiv").style.display = "block";
	}
</script>
</head>
<body>
<%  if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
	response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
	return;
}
%>
	<table border="1px" cellpadding="0" cell&nbsp;spacing="0" width="100%">
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
				<form action="/payless4sms/CreditPerClient" method="post"
					id="formid">
					<table>
						<tr>
							<td colspan="3">
								<%
									HttpSession httpsession = request.getSession(false);
									if (httpsession != null) {
										long clientmanagerid = (Long) httpsession.getAttribute("clientmanagerId");
										//	System.out.println("userid:"+userid);
										if(clientmanagerid!=0){
											ClientmanagersManager clientmanager = new ClientmanagersManager();
											
											Clientmanager client=clientmanager.findById(clientmanagerid);
										out.println("<table  border='1' align='center'>");
										out.println("<tr><td><b>userid</b></td><td><b>credits</b></td></tr>");
										out.println("<tr><td>" + client.getSysuser().getUsername()+ "</td><td>"
												+ client.getCreditaccount().getAvailablecredit() + "</td></tr>");
										out.println("</table>");
										
										client = null;
										clientmanager=null;
										}
									}
								%>
							</td>
						</tr>
						<%-- <tr>
							<td colspan="3"></td>
						</tr>
						<tr>
							<td><select name="month">
									<option value='0'>Select Month</option>
									<option value='1'>January</option>
									<option value='2'>February</option>
									<option value='3'>March</option>
									<option value='4'>April</option>
									<option value='5'>May</option>
									<option value='6'>June</option>
									<option value='7'>July</option>
									<option value='8'>August</option>
									<option value='9'>September</option>
									<option value='10'>October</option>
									<option value='11'>November</option>
									<option value='12'>December</option>
							</select>
							</td>
							<td><select name="date">
									<option value='0'>Select Date</option>
									<%
										for (int i = 1; i < 32; i++) {
											out.println("<option value=" + i + ">" + i + "</option>");
										}
									%>
							</select>
							</td>
							<td><input type="submit" value="Go" id="getreport" /></td>
						</tr>
						<tr >
							<td colspan="3">
								<%
									HttpSession httpsession1 = request.getSession(false);
									String userid = null;
									if (httpsession1 != null) {
										userid = (String) httpsession.getAttribute("userid");
									}

									List<Simplereport> simpleReportList = (List<Simplereport>) request.getAttribute("simpleReport");
									out.print("<table border = \"1\">");
									if (simpleReportList != null && simpleReportList.size()!=0) {
										Calendar cal=((Calendar)request.getAttribute("from"));
										Calendar cal2=((Calendar)request.getAttribute("to"));
										if(cal!=null && cal2!=null){
										out.println("<tr> Total Records found from :"+cal.getTime()+"to"+cal2.getTime()+" are :"+simpleReportList.size()+"</tr>");
										}
										out.print("<tr><th>destination</th><th>source</th><th>Timedate</th><th>SmsId</th><th>Message</th><th>status</th><tr>");
										Iterator<Simplereport> it = simpleReportList.iterator();
										System.out.println("proessreques sms report list size:"
												+ simpleReportList.size());
										while (it.hasNext()) {
											Simplereport sr = (Simplereport) it.next();
											out.print("<tr><td>" + sr.getDestination() + "</td><td>"
													+ sr.getSource() + "</td><td>"
													+ new Date(Long.parseLong(sr.getTimedate()))
													+ "</td><td><a href='/payless4sms/reports/getDetailSmsIdReport.jsp?smsid="+sr.getSmsid()+"'>" + sr.getSmsid() + "</a></td><td>"
													+ sr.getMessage() + "</td><td><font color='#5DAC0E'>Received by Recipient</font></td><tr>");
											sr = null;
										}				
									}
									else{
										if(request.getAttribute("simpleReport")!=null)
										out.println("No Record found");
									}
									out.print("</table>");
								%>
							</td>
						</tr> --%>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>