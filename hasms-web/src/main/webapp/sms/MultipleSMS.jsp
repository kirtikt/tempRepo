<%@page import="com.ecomm.ultimatesms.messaging.utils.Helper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript"> 


var ck_validchar = /^[\040-\177]*$/;
function validate(Message,event) {

	var message1 = Message.value;
	if(event.keyCode!=8)
	{
	if (!ck_validchar.test(message1)) {
		alert("Please enter standard SMS characters");
	 	
	}
		
	
	}	
}
function validateThis(dataform) {
	if(validMessage(dataform.message.value) && validReply(dataform.replies.checked,dataform.sender.value) && validMessageLength(dataform.message.value))
		return true;
	else
		return false;
	
}
function validReply(result,senderdata)
{
	
	var flag=true;
	if(!result)
	{
			if(senderdata=="")
			{	
				alert("Please enter sender data");
				flag=false;
			}	
	}
	return flag;
}

function validMessageLength(Message)
{

	if (Message.length < 160)
	 {
		 
			//alert("Please enter standard SMS characters");
		 	return true;
	}
	else
	{
		alert("Only 160 characters are allowed");
			return false;
	}	

}

function validMessage(Message)
{

	if (!ck_validchar.test(Message))
	 {
		 
			//alert("Please enter standard SMS characters");
		 	return true;
	}
	else
	{
			return true;
	}	

}
// fieldname, warningname, remainingname, maxchars
function CheckFieldLength(fn,wn,rn,mc) {

  var len = fn.value.length + document.getElementById("footer").value.length;;
  var no_of_messages = 1;
 if (len > mc) {
    fn.value = fn.value.substring(0,mc);
    len = mc;
  }
  document.getElementById(wn).innerHTML = len;
  document.getElementById(rn).innerHTML = mc - len;
 
 
if(len<161){
	      document.getElementById("nomessages").innerHTML = 1;
	    }else{
		    for(i=0;i<=len;i++){
			  no_of_messages = parseInt((len)/153);
		        }
	    document.getElementById("nomessages").innerHTML = no_of_messages+1;
	  }
}


function CheckFieldLengthfooter(fn,wn,rn,mc) {
	  var len = fn.value.length+ document.getElementById("Message").value.length;
	  var no_of_messages = 1;
	 if (len > mc) {
	    fn.value = fn.value.substring(0,mc);
	    len = mc;
	  }
	  document.getElementById(wn).innerHTML = len;
	  document.getElementById(rn).innerHTML = mc - len;
	 
	 
	if(len<161){
		      document.getElementById("nomessages").innerHTML = 1;
		    }else{
			    for(i=0;i<=len;i++){
				  no_of_messages = parseInt((len)/153);
			        }
		    document.getElementById("nomessages").innerHTML = no_of_messages+1;
		  }
	}


function checkReplies(){
var val  = document.getElementById("sender").value;
oFormObject = document.forms['formid'];
oFormElement = oFormObject.elements["operator"];
if(document.getElementById("replies").checked==true){
	oFormElement.value="routesms_in";
document.getElementById("sender").disabled  = true;
}else{
	oFormElement.value = "silverstreet_an";
document.getElementById("sender").disabled  = false;
}
}

</script> 
<title><%@ include file="/title.jsp" %></title>
<link href="/payless4sms/css/style.css" rel="stylesheet" type="text/css">


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
<script type="text/javascript">
	        
	jQuery(document).ready(function($) {
                $("#datepicker1").datepicker({ minDate: new Date });
	});
	
</script>
</head>
<body>

	<table border="1px" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="2">
				<!-- header content -->
				<%@ include file="/header.jsp" %>
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
<h3>SendMessage</h3>
<form action="/payless4sms/ConfirmMultipleMessageSending" method="post" id="formid" enctype="multipart/form-data" onSubmit="return validateThis(this)">
<table width="70%" align="center">
					<tr>
					<td></td><td><b style="color: #DD1818">
					<% if(request.getAttribute("errormsg")!=null){
						out.println(request.getAttribute("errormsg"));
					}%></b>
					</td>
					</tr>
					<tr>
					<td> </td>
					 <td><b style="color: #5DAC0E">
					<% if(request.getAttribute("successmessage")!=null){
						out.println(request.getAttribute("successmessage"));
					}%></b>
					</td>
					</tr>
	<tr>
		<td>Number File (CSV):</td>
		<td><!--  <textarea id="receiver" cols="16" rows="10" name="receiver"></textarea>-->
		<input type="file" name="csvFile" id="csvFile"/>
		</td>
	</tr>
	
	<tr>
		<td>System Response</td>
		<td><textarea name="message" id="message" cols="40" rows="5"
			onkeyup="CheckFieldLength(message, 'charcount', 'remaining', 153*9);" onkeypress="validate(this,event);"
			onkeydown="CheckFieldLength(message, 'charcount', 'remaining', 153*9);" onfocus="CheckFieldLength(message, 'charcount', 'remaining', 153*9);"></textarea></td>

	</tr>
		<tr>
		<td>Footer</td>
		<td><textarea name="footer" id="footer" style="height: 21px;"
					onkeyup="CheckFieldLengthfooter(footer, 'charcount', 'remaining', 153*9);"
			onkeydown="CheckFieldLengthfooter(footer, 'charcount', 'remaining', 153*9);" onfocus ="CheckFieldLengthfooter(footer, 'charcount', 'remaining', 153*9);"></textarea></td>
	</tr>
	<tr>
		<td colspan="2"><small><span id="charcount">0</span>
		characters | <span id="remaining"></span> characters remaining.| <span
			id="nomessages">1</span> message(s).</small></td>
	</tr>
	<tr>
		<td><input type="hidden" name="operator" id="operator" style="height: 21px;" value="silverstreet_an"/></td>
	</tr>
	<tr>
		<td colspan="2">Click for Date for future SMS</td>
		
	</tr>
	<tr>
		<td>Send on Date</td><td><input name="datetime" id="datepicker1"/></td>
	</tr>
	<% if(request.getSession(false).getAttribute("clientmanagerId")!=null){
							long clientManagerId=(Long)request.getSession(false).getAttribute("clientmanagerId");
							boolean isPresent=new Helper().isPreesentSenderId(clientManagerId);
							System.out.println("isPresent::: "+isPresent);
						     if(isPresent){
					     %>
						<tr>
							<td colspan="2"><input type="checkbox" value="1"
								name="replies" id="replies" onClick="checkReplies()" />Reply to
								System Response</td>
							
						</tr>
						<tr>
							<td>Sender</td>
							<td><input type="text" name="sender" id="sender" />
							</td>
						</tr>
						<% }
						    } %>
	<tr>
		<td ></td>
		<td><input type="submit" value="Send"  />
		</td>
	</tr>
</table>
</form>
</td>
</tr>
</table>
</body>
</html>