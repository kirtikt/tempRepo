<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%@page import="org.hibernate.Session"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.5.2.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
<script type="text/javascript">jQuery.noConflict();</script>
<link rel="stylesheet" type="text/css"
	href="/payless4sms/css/flexigrid.css" media="all" />
<script type="text/javascript" src="/payless4sms/js/flexigrid.js"></script>
<script type="text/javascript">
//<![CDATA[
                jQuery(document).ready(function($) {
                	  $('#flexme1').flexigrid();
			$('#d1').show();
			$('#d2').hide();
			$("#datepicker1").datepicker();   
			$("#datepicker2").datepicker();  
			$("input:radio[name=day]").click(function() {
			var value = $(this).val();

                         if(value=="month"){
                                 
					$('#d1').hide();
					$('#d2').show();

                                  $("#datepicker3").datepicker({
                                         dateFormat: 'mm/dd/yy',
					  changeMonth: true,
					  changeYear: true,
					  showButtonPanel: true,
					 
					  onClose: function (dateText, inst) {
					    var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
					    var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
					    $(this).val($.datepicker.formatDate('mm/dd/yy', new Date(year, month, 1)));
					  }
					});
					$("#datepicker3").focus(function () {
					  $(".ui-datepicker-calendar").hide();
					  $("#ui-datepicker-div").position({
					    my: "center top",
					    at: "center bottom",
					    of: $(this)
					  });
					});
	                         $("#datepicker4").datepicker({
                                         dateFormat: 'mm/dd/yy',
					  changeMonth: true,
					  changeYear: true,
					  showButtonPanel: true,
					 
					  onClose: function (dateText, inst) {
					    var month = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
					    var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
					    $(this).val($.datepicker.formatDate('mm/dd/yy', new Date(year, month, 1)));
					  }
					});
					$("#datepicker4").focus(function () {
					  $(".ui-datepicker-calendar").hide();
					  $("#ui-datepicker-div").position({
					    my: "center top",
					    at: "center bottom",
					    of: $(this)
					  });
					});
}else{
$('#d1').show();
$('#d2').hide();
}
           });
			
                });
                //]]>
               
      </script>
<script type="text/javascript">
       function radio_button_checker()
                {
                var radio_choice = false;
                for (counter = 0; counter < document.radioform.day.length; counter++)
                {
                if ( document.radioform.day[counter].checked)
                radio_choice = true; 
                }
                if (!radio_choice)
                {
                alert("Please select a day or month.")
                return (false);
                }
                return (true);
                }
      </script>



<title><%@ include file="/title.jsp"%></title>
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
		</tr>
		<tr>
			<td width="20%" valign="top">
				<!-- Page content in left pane --> <%@ include
					file="/navigation.jsp"%> <!-- End page content in left pane -->
			</td>
			<td valign="top">
				<form action="/payless4sms/MultiReports" method="post"
					id="radioform" onsubmit="return radio_button_checker();"
					name="radioform">

					<%
							Calendar d = new GregorianCalendar();
							String date=(d.get(Calendar.MONTH) + 1)+"/"+d.get(Calendar.DATE)+"/"+d.get(Calendar.YEAR);
					%>
					<div id="d1" style="display: none;">

						From: <input name="fromDate" id="datepicker1"
							value="<% if(request.getAttribute("fromDate")!=null){%><%=request.getAttribute("fromDate") %><%}else{%><%=date%><% } %>" />
						<br> To:&nbsp;&nbsp;&nbsp; <input name="toDate"
							id="datepicker2"
							value="<% if(request.getAttribute("toDate")!=null){%><%=request.getAttribute("toDate") %><%}else{%><%=date%><% } %>" />
						<br>

					</div>

					<div id="d2" style="display: none;">

						From: <input name="fromDate1" id="datepicker3"
							value="<% if(request.getAttribute("fromDate")!=null){%><%=request.getAttribute("fromDate") %><%}else{%><%=date%><% } %>" /><br>

						To:&nbsp;&nbsp;&nbsp; <input name="toDate1" id="datepicker4"
							value="<% if(request.getAttribute("toDate")!=null){%><%=request.getAttribute("toDate") %><%}else{%><%=date%><% } %>" />
						<br>
					</div>

					<input type="radio" name="day" value="day"
						<%if("day".equalsIgnoreCase((String)request.getAttribute("type"))){%>
						<%="checked"%> <% }%>> Day <input type="radio" name="day"
						value="month"
						<%if("month".equalsIgnoreCase((String)request.getAttribute("type"))){%>
						<%="checked"%> <% } %>> Month <br>

					<div>
						Select report type:: <select id="reporttype"
							name="reporttype">
							<option value="advr">Advanced Report</option>
							<option value="sur">Summary Report</option>
							<option value="dsur">Detailed Summary Report</option>
							<option value="btchr">Batch Report</option>
						</select>
					</div>
					<input type="submit" value="getreport" id="getreport" />
				</form></td>
		</tr>

	</table>
</body>
</html>