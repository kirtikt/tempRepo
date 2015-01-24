<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>

<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
<title><%@ include file="/title.jsp"%></title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">
	jQuery.noConflict();
</script>
<link rel="stylesheet" type="text/css"
	href="/payless4sms/css/flexigrid.css" media="all" />
<script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>

</head>

<script type="text/javascript">
	//         
	jQuery(document).ready(function($) {
		$('#flexme1').flexigrid();
		$("#datepicker1").datepicker();
		$("#datepicker2").datepicker();
	});
	//
	
	function submitform(pkhol,type ){
	
		document.getElementById("pkHoldingAccount").value=pkhol;
		document.getElementById("type").value=type;
		document.resendform.submit();
		}
	
</script>
<style type="text/css">
/*demo page css*/
body {
	font: 75.5% "Trebuchet MS", sans-serif;
	
}

.demoHeaders {
	margin-top: 2em;
}

#dialog_link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog_link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

ul#icons {
	margin: 0;
	padding: 0;
}

ul#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

ul#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}
</style>

<body>
	<%
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("//payless4sms/adminLogin.jsp?errorMessage=please login");
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
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/adminnavigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->
				<table width="70%" align="center">

					
					<tr>
						<td>
							<form action="/payless4sms/RecordFailedSms" method="post">
								<!-- id="formid" name="form" onSubmit="return checkDayMonth();"> -->
								<table><tr>
								<td><b style="color: #5DAC0E">
								<% if(request.getAttribute("msg")!=null){
									out.print(request.getAttribute("msg"));
								} %></b>
								</td>
								</tr>
								<tr>
								<td>
						   Select Client ::</td><td><select name="managerid" id="managerid">
							<%
									
									ClientmanagersManager manager = new ClientmanagersManager();
							      Long mid=null;
									if(request.getAttribute("managerid")!=null){
									mid=(Long)request.getAttribute("managerid");
									}
									System.out.println("managerid:"+mid);
									List<Clientmanager> cltList=manager.getList();
										Iterator<Clientmanager> it1 = cltList.iterator();
									
											while (it1.hasNext()) {
											Clientmanager sr = (Clientmanager) it1.next();
											if(mid!=null){
												if(mid==sr.getPkclientmanagerid()){
													out.println("<option value=" + sr.getPkclientmanagerid() + " selected >" + sr.getSysuser().getUserid() + "</option>");
												}
												else{
													out.println("<option value=" + sr.getPkclientmanagerid() + ">" + sr.getSysuser().getUserid() + "</option>");
													}
											}
											else{
											out.println("<option value=" + sr.getPkclientmanagerid() + ">" + sr.getSysuser().getUserid() + "</option>");
											}
											 
											}
										
									%>

						</select>
					</td>
								</tr>
									<tr>
										<%
											Calendar d = new GregorianCalendar();
											String date = (d.get(Calendar.MONTH) + 1) + "/"
													+ d.get(Calendar.DATE) + "/" + d.get(Calendar.YEAR);
										%>
										<td>From: <input name="fromDate" id="datepicker1"
											value="<% if(request.getAttribute("fromDate")!=null){%><%=request.getAttribute("fromDate") %><%}else{%><%=date%><% } %>" />
											
										</td>
									</tr>
									<tr>
										<td>To:&nbsp;&nbsp;&nbsp; <input name="toDate"
											id="datepicker2" value="<% if(request.getAttribute("toDate")!=null){%><%=request.getAttribute("toDate") %><%}else{%><%=date%><% } %>"/></td>
									</tr>
									<tr>
										<td><input type="submit" value="getreport" id="getreport" />
										</td>
									</tr>
								</table>
							</form></td>
					</tr>
					<tr>
						<td>
						<form action="/payless4sms/ResendFailedSms" method="post" id="resendform" name="resendform">
							
							<% 
			if(request.getAttribute("map")!=null){
				out.print("<div>Report form "+request.getAttribute("fromDate")+" to "+ request.getAttribute("toDate")+"</div>");
				Map map=(HashMap)request.getAttribute("map");
				if(map!=null){
				out.print("<table border='1' id='flexme1' width='100%'>");
				out.print("<tr>");
		        out.print("<th>");
		        out.print("Record");
		        out.print("</th>");
		        Iterator ithd=map.entrySet().iterator();
				while(ithd.hasNext()){
					Map.Entry pairs=(Map.Entry)ithd.next();
					Map subMap=(Map)pairs.getValue();
					Iterator subIthd = subMap.entrySet().iterator();
					while(subIthd.hasNext()){
						out.print("<th>");
						Map.Entry subpairs1=(Map.Entry)subIthd.next();
						out.print(subpairs1.getKey());
						out.print("</th>");
			     }
					break;
			     }
				out.print("</tr>");
		        Iterator it=map.entrySet().iterator();
		        int count=1;
				while(it.hasNext()){
					Map.Entry pairs=(Map.Entry)it.next();
					Map subMap=(Map)pairs.getValue();
					out.println("<tr>");
					out.println("<td>");
					out.println(count++);
					out.println("</td>");
					Iterator subIt1 = subMap.entrySet().iterator();
					while(subIt1.hasNext()){
//					    System.out.println("day /"+pairs.getKey()+" smssent::"+Subpairs.getKey()+" credit::"+Subpairs.getValue());
						out.println("<td>");
						
						
						Map.Entry subpairs1=(Map.Entry)subIt1.next();
						//out.println("subpairs1.getKey()::"+((String)subpairs1.getKey()).trim());
						if("Failed to be accepted by gateway".equalsIgnoreCase(((String)subpairs1.getKey()).trim()) || "Failed before getting to gateway".equalsIgnoreCase(((String)subpairs1.getKey()).trim()) || "Unconfirmed delivery to mobile network".equalsIgnoreCase(((String)subpairs1.getKey()).trim()) ){
							if((Integer)subpairs1.getValue()!=0){
							out.print(subpairs1.getValue()+" &nbsp &nbsp <input type='button' name='resend' id='resend' value ='resend' onclick=\"submitform('"+pairs.getKey()+"','"+subpairs1.getKey()+"')\" /> ");
							}
							else{
								out.print(subpairs1.getValue());
							}
							}
						else{
							//out.print(subpairs1.getValue()+"<input type='button' name='resend' id='resend' value='resend' onclick='submitform("+pairs.getKey()+","+subpairs1.getKey()+")/>");
							
	                    	out.print(subpairs1.getValue());
	                    }
	                   out.println("</td>");
					  
					}  
				out.println("<td>");
				//out.println("<input type='hidden' value='"+pairs.getKey()+"' name='pkHoldingAccount'/>"+pairs.getKey());
				out.println("<input type='hidden' id='pkHoldingAccount' name='pkHoldingAccount'/>");
				out.println("<input type='hidden' id='type' name='type'/>");
				
				
				out.println("</td>");
				out.println("</tr>");
					  
				}
				out.print("</table >");
			}
				else{
				out.print("<b>Record not found.</b>");}
			}
			%>
			</form>
						</td>
					</tr>

				</table> <br /> <br /> <!-- End page content in right pane --></td>
		</tr>
		<tr>
		</tr>
	</table>
</body>
</html>


