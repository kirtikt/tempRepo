<%@page import="com.ecomm.pl4sms.persistence.dao.MnodirectpropertyManager"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Mnodirectproperty"%>
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
					<h3 class="heading_text">Mno Interface</h3>
				
						<table id='flexme1' style="width:1000px">
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
							<% 
							
							
							MnodirectpropertyManager mnom=new MnodirectpropertyManager();
							List mnolist=mnom.getList();      
						        if(mnolist!=null && mnolist.size()!=0)
							{
						        	int count=1;
						        	out.print("<tr><td>No.</td><td> MNO Interface name </td><td></td><td></td></tr>");
								for(Iterator<Mnodirectproperty> it1=mnolist.iterator();it1.hasNext();)
								{
									
									out.print("<tr>");
									Mnodirectproperty mno= (Mnodirectproperty)it1.next();
								    out.print("<td>"+ count +"</td>");
								    out.print("<td>"+mno.getName()+"</td>");
								    out.print(" <td> <a href='/payless4sms/admin/editinterfacemno.jsp?mnopropid="+mno.getPkmnodpropid()+"'> edit </a></td> ");
								    out.print(" <td> <a href='/payless4sms/admin/deletemnointerface.jsp?mnopropid="+mno.getPkmnodpropid()+"'> delete </a> </td>");
									out.print("</tr>");
									count++;
								}
							}
						        else{
						    out.print("<tr><td>Record not found.</td></tr>");   }
						
							%>        
							
						</table>
					
				</div></td>
		</tr>
		<tr>

		</tr>
	</table>

</body>
</html>