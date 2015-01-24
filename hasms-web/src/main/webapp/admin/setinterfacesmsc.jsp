<%@page import="com.ecomm.pl4sms.persistence.dao.SmscproxyManager"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Smscproxy"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Smscproxyproperty"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.SmscproxypropertyManager"%>
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
	if(document.getElementById("interfacename").value== "" || document.getElementById("kannelname").value== ""){
		//document.getElementById("error").style.display='block';
		
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
					<h3 class="heading_text">Create Interface SMSC proxy</h3>
					<form action="/payless4sms/SetSmscInterfaceProperty" id="form"
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
								<td>Smsc :</td>
								<td><select id="smscid" name="smscid" class="required">
								<% 
																SmscproxyManager mnom=new SmscproxyManager();
								List mnolist=mnom.getList();      
							        if(mnolist!=null || mnolist.size()!=0)
								{
									for(Iterator<Smscproxy> it1=mnolist.iterator();it1.hasNext();)
									{
										Smscproxy mno= (Smscproxy)it1.next();
									out.print("<option value="+mno.getPksmscproxyid()+">"+mno.getName()+"</option>");
									}
								}
								
								%>
								</select></td>
							</tr>
							<tr>
							<td>Interface  Name</td>
								<td><input type="text" name="interfacename" id ="interfacename"
									class="input required" /> <small style="color:gray;">*</small></td>
							</tr>
							<tr>
								<td>Kannel SMSC Name</td>
								<td><input type="text" name="kannelname" id="kannelname"
									class="input required" /> <small style="color:gray;">*</small></td>
							</tr>
							<tr>
								<td>Interface Type</td>
								<td><select id="interfacetype" name="interfacetype"
									class="required">
										
										<option value="SMPP">SMPP</option>
										<option value="HTTP">HTTP</option>
										<option value="FTP">FTP</option>
										<option value="webservice">webservice</option>
								</select>
								</td>
							</tr>
							  <tr>
							<td>
							Reply path :: </td>
							<td><select id="reply" name="reply"
									class="required">
										
										<option value="yes">Yes</option>
										<option value="no">No</option>
								</select>
								</td>
                             <tr>
								<tr>
								<td>Delivery Receipts</td>
								<td><select id="deliveryreceipt" name="deliveryreceipt"
									class="required">
										
										<option value="yes">Yes</option>
										<option value="no">No</option>
								</select>
								</td>
							</tr>

							<tr>
								<td>Allowed speed</td>
								<td><input type="text" name="speed" id="speed"
									class="required" /> SMS/sec</td>
							</tr>
							<tr>
								<td>Concatenation</td>
								<td><select id="concatenation" name="concatenation"
									class="required">
										
										<option value="yes">Yes</option>
										<option value="no">No</option>
								</select>
								</td>
							</tr>
							
							<tr>
								<td>Alphanumeric senderid</td>
								<td><select id="alphasourceadd" name="alphasourceadd"
									class="required">
										
										<option value="yes">Yes</option>
										<option value="no">No</option>
								</select>
								</td>
							</tr>
							
							<%
							
							MnoManager mnom1=new MnoManager();
							List list=mnom1.getList();
						    Iterator it=list.iterator();
						    while(it.hasNext()){
						        Mno mno=(Mno)it.next();
						        out.println("<tr><td></td><td>Details for "+mno.getName()+"</td></tr>");
						        out.println("<tr>");
						    	out.println("<td>"+mno.getName()+" sms cost</td>");
						    	out.println("<td><input type='text' name='"+mno.getName().trim()+"' value='0' /></td>");
						    	 out.println("</tr>");
						    	 out.println("<tr>");
							     out.println("<td>"+mno.getName()+" reply cost</td>");
							     out.println("<td><input type='text' name='r"+mno.getName().trim()+"' value='0'/></td>");
							     out.println("</tr>");
							     out.println("<tr>");
							     out.println("<td>"+mno.getName()+" Source Address</td>");
							     out.println("<td><input type='text' value='n/a' name='sa"+mno.getName().trim()+"' /></td>");
							     out.println("</tr>");
						    }
						   
							%>
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