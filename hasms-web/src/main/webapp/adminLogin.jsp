<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page errorPage="/error404.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">

</head>
<body>
	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="2">
				<!-- header content -->
				<%@ include file="/loginheader.jsp" %>
			</td>
		</tr>
		<tr>
			<td valign="top" align="center">
				<!-- Page content in right pane -->
				<div id="login-content">
				
					<h3 class="heading_text">Login</h3>
					<form action="/payless4sms/AdminLogin" id="form" method="post">
						<table id="login-table">
						<tr style="color: #CE3939">
								 <%
									if (request.getAttribute("errorMessage") != null) {
										out.print("<td style='color: #CE3939;width:200px'>"
												+ request.getAttribute("errorMessage") + "</td>");
									}
								%>
								<%
									if (request.getParameter("errorMessage") != null) {
										out.print("<td style='color: #CE3939;width:200px' >"
												+ request.getParameter("errorMessage") + "</td>");
									}
								%> 
							</tr>
							<tr>
								<td style="width: 55px;">UserId</td>
								<td style="width: 210px;"><input type="text" id="userid"
									name="userid" id="userid" /></td>
							</tr>
							<tr>
								<td>Password</td>
								<td><input type="password" name="password" id="password" />
								</td>
							</tr>
							<tr>
								<td>ClientRef</td>
								<td><input type="text" name="clientref" id="clientref" /></td>
							</tr>
							<tr>
								<td colspan="2" align="right" style="padding-top: 10px;">
								<input type="hidden" name="isLogin" value="true"/>
								<input type="submit" id="login_submit" name="login_submit"
									value="Log in" /></td>
							</tr>
							<tr></tr>
						</table>
					</form>
				</div> <!-- End page content in right pane --></td>
		</tr>
		<tr>
			
		</tr>
	</table>

</body>
</html>