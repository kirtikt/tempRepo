<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>

<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Holdingaccount"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="com.ecomm.ultimatesms.messaging.commons.MyComparable"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session" %>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collections"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<%  if(request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
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
		</tr>
		<tr>
		<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/navigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top" align="center"  width="80%">
				<!-- Page content in right pane -->
			<table  border="1"  width=100% id='flexme1'>
							<%
							ClientmanagersManager manager = new ClientmanagersManager();
							//Long pkerManager=Long.parseLong(request.getParameter("pKeyManager"));

							Long pkerManager=(Long) request.getSession(false).getAttribute("clientmanagerId");
							Clientmanager client=manager.findById(pkerManager);
							Set<Holdingaccount> holdingAccountList1=client.getHoldingaccounts();
							List holdingAccountList=new ArrayList<Holdingaccount>(holdingAccountList1);
							Collections.sort(holdingAccountList,new MyComparable());	
								
							Iterator<Holdingaccount> it = holdingAccountList.iterator();
								boolean flag = false;
								int count = 0;
								out.print("<b>Detailed report of holding account for client "+client.getSysuser().getUserid()+"</b>");
								out.print("<tr><td><b>Client id</b></td><td><b>Deposited Credits</b></td><td><b>Credits left</b></td><td><b>Message Count</b></td><td><b>Date Time</b></td><td><b>Message sent count</b></td><td><b>Message reached count</b></td><td><b>Sms ID</b></td><tr>");
								while (it.hasNext()) {
									count++;
									Holdingaccount ha = (Holdingaccount) it.next();
									out.print("<tr><td>"
											+ client.getSysuser().getUserid()
											+ "</td><td>"
											+ ha.getCreditsdeposited()
											+ "</td><td>"
											+ha.getCreditsleft()
											+ "</td><td>"
											+ha.getSmscount()
											+ "</td><td>"
											+new Helper().myTimeIn(ha.getTimedate())
											+ "</td><td>"
											+ha.getCountofcellno()
											+ "</td><td>"
											+ha.getSmssentcount()
											+ "</td><td><a href='/payless4sms/reports/clientsmsdetailreport.jsp?pKeyHolding="+ha.getPkeyholdingaccountid()+"'>Click for detailed report for sms</a></td><td>"
											);
								}
							%>
			</table>
				 <!-- End page content in right pane -->
		   </td>
		</tr>
		<tr>
			
		</tr>
	</table>
</body>
</html>