<%@page import="org.hibernate.Session"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>

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
				<%@ include file="/adminmenu.jsp" %>
		</tr>
		<tr>
		<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/adminnavigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top" align="center" width="80%">
				<!-- Page content in right pane -->
			<table id="flexme1" border="1"  width=100%>
							<%
							
							ClientmanagersManager manager = new ClientmanagersManager();
							
							List<Clientmanager> cltList=manager.getList();
								Iterator<Clientmanager> it = cltList.iterator();
								boolean flag = false;
								int count = 0;
								out.print("<tr><td><b>Client id</b></td><td><b>Available Credits</b></td><td><b>Left SMS's</b></td><td><b>Sending Interface</b></td><td><b>Holding Account Detail</b></td><tr>");
								while (it.hasNext()) {
									count++;
									Clientmanager sr = (Clientmanager) it.next();
									Long id=sr.getCreatedby();
									Clientmanager createdBy=manager.findById(id);
									int leftSms = (int) (sr.getCreditaccount().getAvailablecredit() / sr.getLocalsmscost());
									 //<td>"+ createdBy.getSysuser().getRole().getRolename()+"("+createdBy.getSysuser().getUserid()+")</td>
									out.print("<tr><td>"
											+ sr.getSysuser().getUserid()
											+ "</td><td>"
											+ sr.getCreditaccount().getAvailablecredit()
											+ "</td><td>"
											+ leftSms
											+ "</td><td>"
											+ sr.getSendinginterface().getKannelname()
											+"</td><td><a href='/payless4sms/adminreports/holdingreportperclient.jsp?pKeyManager="+sr.getPkclientmanagerid()+"'>Holding Account Detail</a>  |  <a href='/payless4sms/adminreports/creditreport.jsp?pKeyManager="+sr.getPkclientmanagerid()+"&pKeyManagerName="+sr.getSysuser().getUserid()+"'>Click for credit report</a> </td></tr>");
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