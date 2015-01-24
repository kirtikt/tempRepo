<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sendinginterface"%>
<%@page
	import="com.ecomm.pl4sms.persistence.dao.SendingInterfaceManager"%>
<%@page import="org.hibernate.Session"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp"%></title>
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">



<style type="text/css">
label.error {
	float: none;
	color: red;
	padding-left: .5em;
	vertical-align: top;
}

p {
	clear: both;
}

.submit {
	margin-left: 12em;
}
</style>


<script type="text/javascript">
$(document).ready(function()
{
  $("#form").validate();

}
);

function requiredvalidation(){
	if(document.getElementById("reply").value==0 || document.getElementById("alphasourceadd").value==0 ||document.getElementById("concatenation").value==0 || document.getElementById("interfacetype").value==0 || document.getElementById("name").value==""||document.getElementById("sourceadd").value=="" || document.getElementById("deliveryreceipt").value==0 || document.getElementById("smscost").value=="" || document.getElementById("replycost").value=="" ||document.getElementById("prefixes").value==""){
		//document.getElementById("error").style.display='block';
		
			document.getElementById("err").innerHTML="Please enter all fields";
			
		return false;
		}
	 var pattern= /^((\d+(\.\d*)?)|((\d*\.)?\d+))$/ ;
     var smscost=document.getElementById("smscost").value
     var replycost=document.getElementById("replycost").value=="" 
     if(!(smscost.match(pattern))){
       document.getElementById("err").innerHTML="please enter valid sms cost";
       return false;
      }
     if(!(replycost.match(pattern))){
        document.getElementById("err").innerHTML="please enter valid reply cost";
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
				<!-- header content --> <%@ include file="/header.jsp"%>
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
					<h3 class="heading_text">Mno Registration</h3>
					<form action="/payless4sms/SetMno" id="form"
						method="post" onsubmit="return requiredvalidation();">
						<table id="login-table">
							<tr>
								<%
									if (request.getAttribute("errorMessage") != null) {
										out.println("<td style='color: #CE3939'>"
												+ request.getAttribute("errorMessage") + "</td>");
									}
								%>
							</tr>
							<tr>
								<td></td>
								<td>
									<div id="err" style="color: rgb(206, 57, 57);"></div>
								</td>
							</tr>
							<tr>
								<td style="width: 150px;">Name</td>
								<td style="width: 210px;"><input type="text" id="name"
									name="name" class="required" /><small style="color:gray;">*</small></td>
							</tr>
							<tr>
								<td style="width: 150px;">prefixes</td>
								<td style="width: 210px;"><small style=color:gray> Enter comma separated prefixes e.g 084,2784 </small> 
									<textarea id="prefixes" cols="16" rows="10"
									name="prefixes" onfocus="cleartext();" ></textarea> 
							</td>
							</tr>
							<tr><td></td><td> <b> Billing contact details </b></td><tr>
							<tr>
								<td style="width: 150px;">Land line no</td>
								<td style="width: 210px;">
								<input id="blandno" cols="16" rows="5"
									name="blandno" /></td>
							</tr>
						
						<tr>
								<td style="width: 150px;"> Mobile number.</td>
								<td style="width: 210px;"><input type="text" id="bmobno"
									name="bmobno" class="required" /></td>
							</tr>
							
							<tr>
								<td style="width: 150px;"> Email address </td>
								<td style="width: 210px;"><input type="text" id="bemailid"
									name="bemailid" class="required" /></td>
							</tr>
							
							<tr><td></td><td> <b> Admin contact details </b></td><tr>
							<tr>
								<td style="width: 150px;">Land line no</td>
								<td style="width: 210px;">
								<input id="alandno" cols="16" rows="5"
									name="alandno" /></td>
							</tr>
						
						   <tr>
								<td style="width: 150px;"> Mobile number. </td>
								<td style="width: 210px;"><input type="text" id="amobno"
									name="amobno" class="required" /></td>
							</tr>
							
							<tr>
								<td style="width: 150px;"> Email address </td>
								<td style="width: 210px;"><input type="text" id="aemailid"
									name="aemailid" class="required" /></td>
							</tr>
							
							<tr>
								<td style="width: 150px;"> Physical address </td>
								<td style="width: 210px;"><textarea id="aphyadd" cols="16" rows="5"
									name="aphyadd" ></textarea></td>
							</tr>
							
							
							<tr><td></td><td> <b> Technical contact details for first level support </b></td><tr>
							<tr>
								<td style="width: 150px;">Land line no</td>
								<td style="width: 210px;">
								<input id="tflandno" cols="16" rows="5"
									name="tflandno" /></td>
							</tr>
						
						   <tr>
								<td style="width: 150px;"> Mobile number.</td>
								<td style="width: 210px;"><input type="text" id="tfmobno"
									name="tfmobno" class="required" /></td>
							</tr>
							
							<tr>
								<td style="width: 150px;"> Email address </td>
								<td style="width: 210px;"><input type="text" id="tfmailid"
									name="tfemailid" class="required" /></td>
							</tr>
							
								<tr><td></td><td> <b> Technical contact details for second level support  </b></td><tr>
							<tr>
								<td style="width: 150px;">Land line no</td>
								<td style="width: 210px;">
								<input id="tflandno" cols="16" rows="5"
									name="tslandno" /></td>
							</tr>
						
						   <tr>
								<td style="width: 150px;"> Mobile number.</td>
								<td style="width: 210px;"><input type="text" id="tfmobno"
									name="tsmobno" class="required" /></td>
							</tr>
							
							<tr>
								<td style="width: 150px;"> Email address </td>
								<td style="width: 210px;"><input type="text" id="tsemailid"
									name="tsemailid" class="required" /></td>
							</tr>
							
							
							
							<tr>
								<td colspan="2" align="right" style="padding-top: 10px;"><input
									type="submit" id="login_submit" name="login_submit"
									value="Register" /></td>
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