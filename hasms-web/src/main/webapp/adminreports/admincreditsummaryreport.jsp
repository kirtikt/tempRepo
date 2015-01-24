<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>

<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Holdingaccount"%>
<%@page import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
<%@page import="com.ecomm.ultimatesms.messaging.commons.MyComparable"%>
<%@ page import="java.text.DateFormat,org.hibernate.Session" %>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Collections"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
<title><%@ include file="/title.jsp" %></title>
 <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
 <script type="text/javascript">jQuery.noConflict();</script>
 <link rel="stylesheet" type="text/css" href="/payless4sms/css/flexigrid.css" media="all" />
  <script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>

</head>

 <script type="text/javascript">
                //<![CDATA[
                jQuery(document).ready(function($) {
                        $('#flexme1').flexigrid();
                });
                //]]>
                </script>

<body>
<%  if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
	response.sendRedirect("//payless4sms/adminLogin.jsp?errorMessage=please login");
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
			</td>
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> 
				<%@ include file="/adminnavigation.jsp" %>
				<!-- End page content in left pane -->
			</td>
			<td valign="top">
				<!-- Page content in right pane -->
				
				<table width="70%" align="center">
				
				<tr> 
						    <td colspan="3">
						    <form action="/payless4sms/AdminCreditSummaryReport" method="post"
					id="formid" name="form">
						     Select Client ::<select name="managerid" id="manageriddrop">
									<%
									
									ClientmanagersManager manager1 = new ClientmanagersManager();
							
									List<Clientmanager> cltList=manager1.getList();
										Iterator<Clientmanager> it1 = cltList.iterator();
									
										while (it1.hasNext()) {
											Clientmanager sr = (Clientmanager) it1.next();
											out.println("<option value=" + sr.getPkclientmanagerid() + ">" + sr.getSysuser().getUserid() + "</option>");
										
											}
										%>
									
							</select>
							<input type="submit" value="Go" id="getreport" />
							</form>
							</td>
							</tr>
							
				        <tr>
						<td>
						
						<% 
						Long pkerManager=(Long) request.getAttribute("clientmanagerId");
						if(pkerManager !=null){
							out.print("<table  border='1'  width=100% id='flexme1'>");
						ClientmanagersManager manager = new ClientmanagersManager();
						//Long pkerManager=Long.parseLong(request.getParameter("pKeyManager"));
						

						
						Clientmanager client=manager.findById(pkerManager);
						Long id=client.getCreatedby();
						Clientmanager createdBy=manager.findById(id);
						Set<Holdingaccount> holdingAccountList1=client.getHoldingaccounts();
						List holdingAccountList=new ArrayList<Holdingaccount>(holdingAccountList1);
						Collections.sort(holdingAccountList,new MyComparable());	
						Iterator<Holdingaccount> it = holdingAccountList.iterator();
							
							out.print("<b>Credit report for "+client.getSysuser().getUserid()+"</b>");
							out.print("<tr><td><b>Date/Time</b></td><td><b>Available credit Before send </b></td><td><b>Used Credits</b></td><td><b>Credits left in account after send</b></td><td><b>Credits Added by</b></td><tr>");
							while (it.hasNext()) {
								
								Holdingaccount ha = (Holdingaccount) it.next();
								out.print("<tr><td>"
										+ new Helper().myTimeIn(ha.getTimedate())
										+ "</td><td>"
										+ ha.getBeforeavailablecredits()
										+ "</td><td>"
										+ha.getCreditsdeposited()
										+ "</td><td>"
										+ha.getAfteravailablecredits()
										+ "</td><td>"
										+createdBy.getSysuser().getUserid()
										+ "</td>"
										);
							}
							out.print("</table>");
						}
						%>
						
						</td>
						</tr>
					
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
		<tr>
	</tr>
	</table>
</body>
</html>


