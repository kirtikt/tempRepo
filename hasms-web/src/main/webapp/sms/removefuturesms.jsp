<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Smsobj"%>
<%@page
	import="com.ecomm.pl4sms.persistence.dao.SmsobjManager"%>
<%@page import="org.hibernate.Session"%>

<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page
	import="com.ecomm.pl4sms.persistence.dao.SysuserManager,com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp"%></title>
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<script type="text/javascript">jQuery.noConflict();</script>
<link rel="stylesheet" type="text/css"
	href="/payless4sms/css/flexigrid.css" media="all" />
<script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>

<script type="text/javascript">
                //<![CDATA[
                jQuery(document).ready(function($) {
                        $('#flexme1').flexigrid();
                });
                //]]>
                </script>
                <script type="text/javascript">
	function showDiv() {
		document.getElementById("reportdiv").style.display = "block";
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
				<!-- header content --> <%@ include file="/header.jsp"%>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- header content --> <%@ include file="/menu.jsp"%>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/navigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<table>
					<tr>
						<td colspan="3">
							<%
									HttpSession httpsession = request.getSession(false);
									if (httpsession != null) {
										long clientmanagerid = (Long) httpsession.getAttribute("clientmanagerId");
										//	System.out.println("userid:"+userid);
										if(clientmanagerid!=0){
											
											SmsobjManager som=new SmsobjManager();
										List<Smsobj> list=	som.getFutureSmsList(clientmanagerid);
										if(list.size()!=0){
										Iterator it=list.iterator();
										int count=1;
										out.println("<table  border='1' align='center' id='flexme1'>");
										out.println("<tr><td><b>No.</b></td><td><b>Destination</b><td><b>Loded Date</b></td><td><b>Future Date</b></td></tr>");
										
										while(it.hasNext()){
											Smsobj so=(Smsobj)it.next();
										out.println("<tr><td>" +count++ + "</td><td>"
												+ so.getReciever() + "</td><td>"
												+ new Helper().myTimeIn(so.getLoadeddate()) + "</td><td>"
												+ new Helper().myTimeIn(so.getFuturedate()) + "</td><td><a href='/payless4sms/DeleteFutureSms?pkey="+so.getPksmsobjid()+"'> Delete </a></td></tr>");
										}
										out.println("</table>");
										}else{
											out.println("<b>Record not found</b>");
										}
										}
									}
								%>
						</td>
					</tr>
			</table></td>
		</tr>
	</table>
</body>
</html>