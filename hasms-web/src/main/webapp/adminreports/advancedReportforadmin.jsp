<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
    <%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>
   
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session" %>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
<title><%@ include file="/title.jsp" %></title>

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
	response.sendRedirect("//payless4sms/adminLogin.jsp?errorMessage=please login");
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
				<%@ include file="/adminmenu.jsp" %>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/adminnavigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top"  width="80%">
				<!-- Page content in right pane -->

				<table width="70%" align="center">
				 <tr>
					<td>
				
			
				</td>
				</tr>
					<tr>
						<td>
						<% 
						out.print("<table border=\"1\" id='flexme1' width='100%'>");
						List<Sms> smsList= new SmsManager().getSmsList("status","11");
						if(smsList!=null||smsList.size()!=0){
							out.print("<tr><th>UserId</th><th>destination</th><th>Sending Interface</th><th>source</th><th>Timedate</th><th>SmsId</th><th>Message</th><tr>");
						Iterator<Sms> it=smsList.iterator();
						
						while(it.hasNext())	{
							Sms sms=(Sms)it.next();
							String substring=sms.getMessage().substring(0,20);
							out.print("<tr><td>"+sms.getClientmanager().getSysuser().getUsername()+"</td><td>"+sms.getDestination()+"</td><td>"+sms.getSendinginterface().getKannelname()+"</td><td>"+sms.getSource()+"</td><td>"+new Helper().myTimeIn(sms.getDatetime())+"</td><td><a href='/payless4sms/adminreports/DetailSmsIdReport.jsp?smsid="+sms.getSmsid()+"'>"+sms.getSmsid()+"</a></td><td>"+substring+"..."+"</td><tr>");
						}	
						}
						out.print("</table>");
						%>
						</td>
					</tr>
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
		<tr>
				
		</tr>
	</table>
</body>
</html>

