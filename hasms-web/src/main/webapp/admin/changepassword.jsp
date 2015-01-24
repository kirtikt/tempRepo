<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	    <%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sendinginterface"%>
	     <%@page import="com.ecomm.pl4sms.persistence.dao.SendingInterfaceManager"%>
	   <%@page import="org.hibernate.Session" %>
	   <%@page import="java.util.List" %>
	   <%@page import="java.util.Iterator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
  


<style type="text/css">

label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
p { clear: both; }
.submit { margin-left: 12em; }

</style>


<script type="text/javascript">
$(document).ready(function()
{
  $("#form").validate();

}
);
function validatepassword(){
	
	if(document.getElementById("password").value==document.getElementById("repassword").value){
		document.getElementById("pwderror").innerHTML="";
		}
	else{
		document.getElementById("repassword").value="";
		document.getElementById("pwderror").innerHTML="password not match";
		}
}
function requiredFiled(){
	if( document.getElementById("password").value=="" || document.getElementById("repassword").value==""){
		
		
			document.getElementById("err").innerHTML="Please enter mandatory fields";
			
		return false;
		}
	document.getElementById("err").innerHTML="";
	return true;
}
</script>
</head>
<body>
	<%
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
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
				<!-- header content --> <%@ include file="/adminmenu.jsp"%>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/adminnavigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->
				<div id="login-content">
					<h3 class="heading_text">Client Registration</h3>
					<form action="/payless4sms/ChangePswd" id="form" method="post" onsubmit="return requiredFiled();">
					
						<%
						ClientmanagersManager manager = new ClientmanagersManager();
						Long pkerManager=Long.parseLong(request.getParameter("pKeyManager"));
						
						Clientmanager cm=manager.findById(pkerManager);
						%>
						<table id="login-table">
						<tr><% if(request.getAttribute("errorMessage")!=null){
					    	out.println("<td style='color: #CE3939'>"+request.getAttribute("errorMessage")+"</td>");
					   		
						}%>
						
						</tr>
						<tr>
						<td></td><td>
						 <div id="err" style="color: rgb(206, 57, 57);">
						</div>
						</td>
						</tr>
							<tr><td></td><td> Change password for <%= cm.getSysuser().getUserid()%></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><input type="password" name="password" id="password" class="required"/>
								<small style="color: gray;"><sup>*</sup></small>
								</td>
							</tr>
							<tr>
								<td>Reenter Password</td>
								<td><input type="password" name="repassword" id="repassword"  onchange="validatepassword()" class="required"/>
								<small style="color: gray;"><sup>*</sup></small>
								<div id="pwderror" style="color: rgb(206, 57, 57);" ></div>
								</td>
							</tr>
							<tr>
								<td colspan="2" align="right" style="padding-top: 10px;">
								<input type="hidden" id="pkeyManagerid"
									name="pkeyManagerid" class="required" value="<%=pkerManager %>"/>
								
								<input
									type="submit" id="login_submit" name="login_submit"
									value="Change Password" /></td>
							</tr>
						</table>
					</form>
				</div></td>
		</tr>
		<tr>
			
		</tr>
	</table>

</body>
</html>