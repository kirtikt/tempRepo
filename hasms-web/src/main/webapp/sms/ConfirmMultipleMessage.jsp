<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
	
</script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
 <script type="text/javascript">jQuery.noConflict();</script>
 <link rel="stylesheet" type="text/css" href="/payless4sms/css/flexigrid.css" media="all" />
  <script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>
<title><%@ include file="/title.jsp"%></title>
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
	
 <script type="text/javascript">
                //<![CDATA[
                jQuery(document).ready(function($) {
                        $('#flexme1').flexigrid();
                        $('#btnLogin').click(function(){
                            $('#loader').show();
                            $('#btnLogin').hide();
                        });
                });
                //]]>
                </script>
                <script type="text/javascript">
                function disable() {
                    document.formid.btnSubmit.disabled=true;
                    }
                   </script>

        <style type="text/css" media="screen">
    <!--

        DIV#loader {
            height: 20px;
            overflow: hidden;
        }

        DIV#loader.loading {
            background: url(/payless4sms/images/spinner.gif) no-repeat center center;
        }
    -->
    </style>
                
</head>
<body>
	<%
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/sms/loginToSendMsg.jsp?errorMessage=please login");
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
				<!-- Page content in right pane -->
				
				<form action="/payless4sms/SendMultipleMessage" method="post"
					id="formid" name="formid">
					<table align="center" id="flexme1" >
						<tr>
						<td>Confirmation</td>
						</tr>
						<tr>
							<td>Invalid Numbers in the file:: </td>
							<td> <%List list1=(List)request.getSession(false).getAttribute("rejectedlist");
							                               out.print(list1.size());%>
							 </td>
						</tr>
						<tr>
							<td>Valid Numbers in the file:: </td>
							<td> <%List list=(List)request.getSession(false).getAttribute("recfixlist");
							                               out.print(list.size());%>
							 </td>
						</tr>
						<tr>
							<td>Credits required to send message:: </td>
							<td> <%Double credits=(Double)request.getSession(false).getAttribute("creditNeeded");
							                               out.print(credits);%>
							 </td>
						</tr>
						
						<tr>
							<td><input type="submit" value="Confirm"  id="btnLogin" name="btnLogin" />
							<div style="display: none;" id="loader" class="loading"></div></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
</body>
</html>