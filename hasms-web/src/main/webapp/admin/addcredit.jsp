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
<script type="text/javascript">
function requiredFiled(){
	if(document.getElementById("credit").value=="" ){
		//document.getElementById("error").style.display='block';
		
			document.getElementById("err").innerHTML="Please enter credits";
			
		return false;
		}
	document.getElementById("err").innerHTML="";
	return true;
}
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
			
						
					<div id="login-content">
					<h3 class="heading_text">Add credits</h3>
						<%
							Long pkerManager=Long.parseLong(request.getParameter("pKeyManager"));
							
							ClientmanagersManager manager = new ClientmanagersManager();
							Clientmanager client=(Clientmanager)manager.findById(pkerManager);
							
							Double creditsAvailable=client.getCreditaccount().getAvailablecredit();
							out.println("Availablecredit for  <b>"+client.getSysuser().getUserid()+"</b> :: "+creditsAvailable);
						
							%>
					<form action="/payless4sms/AddCredit" id="form" method="post" onsubmit="return requiredFiled();">
					<table id="login-table">
					<tr>
						<td></td><td>
						 <div id="err" style="color: rgb(206, 57, 57);">
						</div>
						</td>
						</tr>
					<tr>
					
								<td style="width: 150px;">Credits</td>
								<td style="width: 210px;">
								<input type="hidden" id="pkeyManagerid"
									name="pkeyManagerid" class="required" value="<%=pkerManager %>"/>
								<input type="text" id="credit"
									name="credit" class="required"/></td>
					</tr>
					<tr>
								<td colspan="2" align="right" style="padding-top: 10px;"><input
									type="submit" id="login_submit" name="login_submit"
									value="Add Credit" /></td>
							</tr>
					</table>
					</form>
					</div>
			
				 <!-- End page content in right pane -->
		   </td>
		</tr>
		<tr>
			
		</tr>
	</table>
</body>
</html>