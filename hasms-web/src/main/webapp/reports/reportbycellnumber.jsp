<%@page import="java.util.Iterator"%>
<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	    <%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
        <%@page import="com.ecomm.pl4sms.persistence.dao.SmsManager"%>
    
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session" %>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
    <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet"
          type="text/css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <!-- <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.2.min.js"></script>  -->
    <script type="text/javascript">jQuery.noConflict();</script>
    <link rel="stylesheet" type="text/css" href="/payless4sms/css/flexigrid.css" media="all"/>
    <script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>
    <script type="text/javascript">
        //<![CDATA[
        jQuery(document).ready(function ($) {
            $('#flexme1').flexigrid();
            $("#datepicker1").datepicker();
            $("#datepicker2").datepicker();
        });
        //]]>
    </script>
    <script type="text/javascrip t">

function checkDayMonth() {
		len = document.form.day.length;
		for (i = 0; i <len; i++) {
			if (document.form.day[i].checked) {
			chosen = document.form.day[i].value;
			}
			}

			if (chosen == "month") {
				if(!(document.getElementById("fromYear").value==document.getElementById("toYear").value)){
							alert("please select same year");
							return false;
					}
				return true;
			}
			else {
				if(!(document.getElementById("fromMonth").value==document.getElementById("toMonth").value)){
					alert("please select same month");
					return false;
					}
				return true;
			}
	}

    </script>
    <title>
        <%@ include file="/title.jsp" %></title>
<style type="text/css">
			/*demo page css*/
			body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}
			.demoHeaders { margin-top: 2em; }
			#dialog_link {padding: .4em 1em .4em 20px;text-decoration: none;position: relative;}
			#dialog_link span.ui-icon {margin: 0 5px 0 0;position: absolute;left: .2em;top: 50%;margin-top: -8px;}
			ul#icons {margin: 0; padding: 0;}
			ul#icons li {margin: 2px; position: relative; padding: 4px 0; cursor: pointer; float: left;  list-style: none;}
			ul#icons span.ui-icon {float: left; margin: 0 4px;}
		</style>

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
				<%@ include file="/menu.jsp" %>
		</tr>
		<tr>
		<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/navigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top">
				<form action="/payless4sms/ReportByCellNumber" method="post" >
					<!-- id="formid" name="form" onSubmit="return checkDayMonth();"> -->
					<table>
					    <tr>
						<td>Enter Destination Number ::<br><small style="color:#808080">Enter the number you wish to search for <br>e.g. 27844742450 or 844742450 or 0844742450</small>  </td>
						<td><input name ="destination" id="destination"/> </td>
						</tr>
						<tr>
							<td>
							  From :: </td><td> <input name ="fromDate" id="datepicker1"/>
							
						<%
						
							HttpSession httpsession = request.getSession(false);
							long managerid = (Long) httpsession.getAttribute("clientmanagerId");
						%>
							<input type="hidden" name="managerid" value=<%=managerid%> >
							<input type="hidden" name="type" value="client" >
							</td>
						</tr> 
						<tr>
						<td>To :: </td><td>  <input name ="toDate" id="datepicker2"/> </td>
						</tr>
						                    
						<tr>
						<td><input type="submit" value="Search" id="getreport" /></td>
						</tr>
						</table>
				
			    <%
			    String fixDestination=(String)request.getAttribute("fixDestination");
			    String from=(String)request.getAttribute("from");
				String to=(String)request.getAttribute("to");
			    if(fixDestination!=null && from !=null && to !=null){
			    out.print("<table border=\"1\" id='flexme1' width='100%'>");
				long clientmanagerId=(Long)request.getSession(false).getAttribute("clientmanagerId");
				
				
				
				
				List<Sms> smsList= new SmsManager().getReportByCellNumber(fixDestination , from, to ,clientmanagerId);
				if(smsList!=null && smsList.size()!=0){
				out.print("<tr><th>destination</th><th>Timedate</th><th>status</th><tr>");
				Iterator<Sms> it=smsList.iterator();
				Helper hp=new Helper();
				while(it.hasNext())	{
					Sms sms=(Sms)it.next();
					String st=hp.getSmsStatus(sms.getSmsid(),"c");
					out.print("<tr><td>"+sms.getDestination()+"</td><td>"+new Helper().myTimeIn(sms.getDatetime())+"</td><td><font color='#5DAC0E'>"+st+"</font></td><tr>");
				}	
				
				}
				else{
					out.print("<tr><td>Record not found</td></tr>");
				}
			
				out.print("</table>");
			    }
			    %>
			
					
				</form>
				
					
		</td>
		</tr>
		
	</table>
</body>
</html>