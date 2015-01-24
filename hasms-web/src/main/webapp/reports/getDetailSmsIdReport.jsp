<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@page import="java.util.Date"%>
      <%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
    <%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>
       
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator,java.text.DateFormat"%>
<%@ page import="org.hibernate.Session" %>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
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
				<!-- Page content in right pane -->

	<table border='1' id='flexme1' width='100%'>
		<%

		List<Sms> smsList= new SmsManager().getSmsList("smsid",request.getParameter("smsid"));
			
			Iterator it = smsList.iterator();
			boolean flag = false;
			out.print("<tr><th>destination</th><th>Status</th><th>SmsId</th><tr>");
			Helper hp=new Helper();
			while (it.hasNext()) {
				Sms sms = (Sms) it.next();
				if(!("22".equals(sms.getStatus().trim())) && !("33".equals(sms.getStatus().trim())) && !("4".equals(sms.getStatus().trim())) && !("m8".equals(sms.getStatus().trim()))){
					String date=hp.myTimeIn(sms.getDatetime()).toString();
					String status=	hp.getClientStatusMessage(sms.getStatus(),date);
					
				out.print("<tr><td>"
						+ sms.getDestination()
						+ "</td><td>" + status
						+ "</td><td>" + sms.getSmsid() + "</td><td><tr>");
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