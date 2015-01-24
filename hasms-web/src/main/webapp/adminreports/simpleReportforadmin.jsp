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
<script type="text/javascript" src="jquery-latest.js"></script> 
<script type="text/javascript" src="tablesorter.js"></script>
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
			<td valign="top">
				<!-- Page content in right pane -->

				<table width="70%" align="center">
				 <tr>
					<td>
				
				<form action="/payless4sms/WriteTOExcel" method="post"
					id="formid">
					<input type="hidden" name="reportType" value="simplereportforadmin"/>
					<input type="submit"  value="ExportToExcel"/>
				</form>
				</td>
				</tr>
					<tr>
						<td>
						<% 
						out.print("<table border=\"1\"  width=100% id='flexme1'>");
						
						List<Sms> smsList= new SmsManager().getSmsList("status","11");
						if(smsList!=null||smsList.size()!=0){
							out.print("<tr><th>User Id</th><th>destination</th><th>Sending Interface</th><th>Timedate</th><th>status</th><tr>");
						Iterator<Sms> it=smsList.iterator();
						Helper hp=new Helper();
						while(it.hasNext())	{
							Sms sms=(Sms)it.next();
							String st=hp.getSmsStatus(sms.getSmsid(),"a");
							out.print("<tr><td>"+sms.getClientmanager().getSysuser().getUserid()+"</td><td>"+sms.getDestination()+"</td><td>"+sms.getSendinginterface().getKannelname()+"</td><td>"+new Helper().myTimeIn(sms.getDatetime())+"</td><td><font color='#5DAC0E'>"+st+"</font></td><tr>");
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

