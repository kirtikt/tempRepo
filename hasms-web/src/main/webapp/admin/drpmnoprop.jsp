<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Mno"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.MnoManager"%>
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
	if(document.getElementById("reply").value==0 || document.getElementById("alphasourceadd").value==0 ||document.getElementById("concatenation").value==0 || document.getElementById("interfacetype").value==0 || document.getElementById("name").value==""||document.getElementById("sourceadd").value=="" || document.getElementById("deliveryreceipt").value==0 || document.getElementById("smscost").value=="" || document.getElementById("replycost").value=="" ||document.getElementById("kannelname").value==""){
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
function isyes(){
	if(document.getElementById("reply").value=="yes"){
		document.getElementById("divsourceadd").style.display = "block";
	}
else{
	document.getElementById("divsourceadd").style.display ="none" ;
}
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
					<h3 class="heading_text">Create MNO property for smsc </h3>
					<form action="/payless4sms/SetMnoSmscProperty" id="form"
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
								<td>Sending Interface</td>
								<td><select id="sendinginterface" name="sendinginterface" class="required">
								<% 

								List<Sendinginterface> interfaceList=new SendingInterfaceManager().getSendingInterface();
								if(interfaceList!=null || interfaceList.size()!=0)
								{
									for(Iterator<Sendinginterface> it=interfaceList.iterator();it.hasNext();)
									{
									Sendinginterface object= it.next();
									out.print("<option value="+object.getPksendinginterface()+">"+object.getInterfacename()+"</option>");
									}
								}
								
								%>
								</select></td>
							</tr>
							<tr>
								<td>Mno :</td>
								<td><select id="mno" name="mno" class="required">
								<% 
								
								MnoManager mnom=new MnoManager();
								List mnolist=mnom.getList();      
							        if(mnolist!=null || mnolist.size()!=0)
								{
									for(Iterator<Mno> it1=mnolist.iterator();it1.hasNext();)
									{
										Mno mno= (Mno)it1.next();
									out.print("<option value="+mno.getPkmnoid()+">"+mno.getName()+"</option>");
									}
								}
							
								%>
								</select></td>
							</tr>
							<tr>
								<td>SMS Cost</td>
								<td><input type="text" name="smscost" id="smscost"
									class="input required" /></td>
							</tr>
							<tr>
								<td>Reply Cost</td>
								<td><input type="text" name="replycost" id="replycost"
									class="input required" /></td>
							</tr>
							
							
                                                       <tr>
							<td>
							
							Source Address :: </td><td><input type="text" name="sourceadd" id="sourceadd"
									class="required" value="n/a" />
						
							</td>
							</tr>
							<tr>
								<td colspan="2" align="right" style="padding-top: 10px;"><input
									type="submit" id="login_submit" name="login_submit"
									value="Set" /></td>
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