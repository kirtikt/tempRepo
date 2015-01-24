<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
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
						</tr><tr>
						<td align='center'><b><% if(request.getAttribute("msg")!=null){
					out.print(request.getAttribute("msg"));
					
				}
				%>
				</b></td></tr><tr>
				<td><form action="/payless4sms/DownloadExcel" method="post"
					id="formid"> 
					<input type="hidden" name="hdfreportpath" id="hdfreportpath" value="<%=(String)request.getAttribute("reportpath") %>" />
					<input type="submit" value="Download Now"/>
					</form></td>
				</tr>
					
				</table> <br /> <br /> <!-- End page content in right pane -->
			</td>
		</tr>
		<tr>
				
		</tr>
	</table>

</body>
</html>
