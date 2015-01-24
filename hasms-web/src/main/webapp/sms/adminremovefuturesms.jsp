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
				<!-- header content --> <%@ include file="/adminmenu.jsp"%>
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/adminnavigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
			<form action="/payless4sms/GetClientIdForRemoveFutureSms" method="post">
				<table>
				
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
					<td>
					<input type="submit" value="click here"/>					
					</td>
					<tr>
						<td colspan="3">
							<%
							if(request.getAttribute("managerid")!=null){
								long clientmanagerid=(Long)request.getAttribute("managerid");
										if(clientmanagerid!=0){
										SmsobjManager som=new SmsobjManager();
										List<Smsobj> list=	som.getFutureSmsList(clientmanagerid);
										
										Iterator it=list.iterator();
										int count=1;
										
										if(list.size()!=0){
										out.println("<table  border='1' align='center' id='flexme1'>");
										out.println("<tr><td><b>No.</b></td><td><b>Destination</b><td><b>Loded Date</b></td><td><b>Future Date</b></td></tr>");
										
										while(it.hasNext()){
											Smsobj so=(Smsobj)it.next();
										out.println("<tr><td>" +count++ + "</td><td>"
												+ so.getReciever() + "</td><td>"
												+ new Helper().myTimeIn(so.getLoadeddate()) + "</td><td>"
												+ new Helper().myTimeIn(so.getFuturedate()) + "</td><td><a href='/payless4sms/AdminDeleteFutureSms?pkey="+so.getPksmsobjid()+"&managerid="+clientmanagerid+"'> Delete </a></td></tr>");
										}
										out.println("</table>");
										}
										else{
											out.println("Record not found");
										}
									}	
							}
							%>
						</td>
					</tr>
					
			</table>
			</form>
			</td>
			
		</tr>
	</table>
</body>
</html>