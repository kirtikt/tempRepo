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
			<td valign="top" align="center"  width="80%">
				<!-- Page content in right pane -->
			
			
			
				<h3>Welcome in admin page.</h3>
				   <b>Client Management</b>
				    <menu>
                     <li><a href="/payless4sms/admin/clientRegistration.jsp">Add Client.</a> </li>
                      <li> <a href="/payless4sms/admin/showClients.jsp">Show Clients</a></li>
                      <li> <a href="/payless4sms/admin/ClientsList.jsp">Manage clients</a></li>
                       <li> <a href="/payless4sms/admin/ClientsList.jsp">Add Credit</a></li>
                       
                       
                        <!--  <li> <a href="/payless4sms/admin/setClientSmsCost.jsp">Add sms cost for client</a></li> -->
				     </menu>
				 <b>SMSC Proxy Management</b>
				   <menu>
					<li><a href="/payless4sms/admin/setsmscproperty.jsp">Add SMSC Proxy</a></li>
					<!--<li><a href="/payless4sms/admin/drpmnoprop.jsp">Set MNO  property for smsc proxy</a></li>-->
					<li><a href="/payless4sms/admin/setinterfacesmsc.jsp">Add Interface SMSC Proxy</a></li>
						<li><a href="/payless4sms/admin/smscproxy.jsp">Manage Smsc proxy</a></li>
					 <li> <a href="/payless4sms/admin/smscproxyinterface.jsp">Manage Interface SMSC Proxy</a></li> 
					</menu> 
				 	<b>MNO Management</b>
				   <menu>
				    <li><a href="/payless4sms/admin/setmno.jsp">Add Mno</a></li>
					<li><a href="/payless4sms/admin/setmnoproperty.jsp">Add interface to mno</a></li>
					<li><a href="/payless4sms/admin/mno.jsp">Manage mno</a></li>
					<li><a href="/payless4sms/admin/interfacemno.jsp">Manage Interface mno</a></li>
					
					</menu>
				  <!--End page content in right pane -->
				  <b>JVM Memory Monitor</b>
				  <menu>
				    <li><a href="/payless4sms/jvmMemoryMonitor.jsp" target="_new">JVM Memory Monitor</a></li>
					</menu>
					
					<b>Queue Browser</b>
				  <menu>
				    <li><a href="/payless4sms/JmsExample">Queue Browser</a></li>
					</menu>
				
				
				</td>
		</tr>
		<tr>
			
		</tr>
	</table>
</body>
</html>