<%@page import="com.ecomm.pl4sms.persistence.dao.SendingInterfaceManager"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sendinginterface"%>
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
			<td valign="top" align="center">
				<!-- Page content in right pane -->
			<table id="login-table" border="1"  width=100%>
							<%
							
							List<Sendinginterface> interfaceList=new SendingInterfaceManager().getSendingInterface();
							Iterator it=interfaceList.iterator();
							out.print("<tr><td><b>Smsc name</b></td><td><b>operation</b></td><tr>");
								while (it.hasNext()) {
									
									Sendinginterface sr = (Sendinginterface) it.next();
									out.print("<tr><td>"
											+ sr.getInterfacename()
											+"</td><td><a href='/payless4sms/admin/editsmscproperty.jsp?pKeyManager="+sr.getPksendinginterface()+"'>Edit</a></td></tr>");
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