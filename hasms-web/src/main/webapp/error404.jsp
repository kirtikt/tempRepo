<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>

<%@ page isErrorPage = "true"%>
<%@ page import = "java.io.*" %>


<body>
<%  if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
	%>
	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
	<tr>
			<td colspan="2">
				<!-- header content -->
				<%@ include file="/header.jsp" %>
			</td>
		</tr>
		<tr>
		</tr>
		<tr>
			<td valign="top">
				<!-- Page content in right pane -->

				<table width="70%" align="center">
					<tr>
						<td><b>Exception occur</b><br> 
       					<font color="red">
       					
       					<% if(exception!=null){
       					exception.toString(); }
       					%>
       					</font></td>
					</tr>
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
		<tr>		
		</tr>
	</table>
	
<% }else{
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
				
				<!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->

				<table width="70%" align="center">
					<tr>
						<td><b>Exception occur</b><br> 
       					<font color="red"><% if(exception!=null){
       					exception.toString(); }
       					%></font></td>
					</tr>
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
		<tr>		
		</tr>
	</table><%} %>
</body>
</html>