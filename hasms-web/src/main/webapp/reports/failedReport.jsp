
<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@page import="java.util.Date"%>
  <%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
        <%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>
   
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
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
<%  if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
	response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
	return;
}
%>
	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<tr>
			<td colspan="2">
				<!-- header content -->
				<%@ include file="/header.jsp" %>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- header content -->
				<%@ include file="/menu.jsp" %>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/navigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top">
				<form action="/payless4sms/WriteTOExcel" method="post"
					id="formid">
					<input type="hidden" name="reportType" value="failedreport"/>
					<input type="submit"  value="ExportToExcel"/>
				</form>
				
				 <table border='1' id='flexme1' width='100%'>
		<%
			long clientmanagerId=(Long)request.getSession(false).getAttribute("clientmanagerId");
			
			List<Sms> smsList= new SmsManager().getSmsList("status","m16",clientmanagerId);
			request.getSession(false).setAttribute("reportlist",smsList);
			if(smsList!=null||smsList.size()!=0){
				
			Iterator<Sms> it = smsList.iterator();
			boolean flag = false;
			out.print("<tr><th>UserId</th><th>destination</th><th>Reason</th><th>Timedate</th><th>SmsId</th><th>Message</th><tr>");
			while (it.hasNext()) {
				Sms sms = (Sms) it.next();
				String substring=sms.getMessage().substring(0,20);
				out.print("<tr><td>"+ sms.getClientmanager().getSysuser().getUserid() + "</td><td>"
						+ sms.getDestination() +"</td><td>" +new Helper().getStatusMessage(sms.getStatus())+ "</td><td>" +new Helper().myTimeIn(sms.getDatetime())
						+ "</td><td>" + sms.getSmsid() + "</td><td>"
						+ substring + " ...</td></tr>");
			}
			}
		
		%>
	</table>
	</td>
		</tr>
		<tr>		
		</tr>
	</table>
</body>
</html>