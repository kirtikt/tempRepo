<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SMS Tester</title>
</head>
<script>

function submitForm() {

	var jsonInput = {
			"username":"varsha",
			"password":"varshag123",
			"clientRef":"varsha",
			"messageList":[{"msisdn":"27828501754","message":"Test Message 1"},{"msisdn":"27828501754","message":"Test Message 1"}],
			"sessionRef":"sessionref","transactionRef":"transactionref","callbackURL":"callback","getResultsURL":"getresults"
			};

	 
	Send.action='ws/smsbulksender/j_postsend_sms_list/' + JSON.stringify(jsonInput);
	Send.submit();			
	
}

</script>
<body>

	<form name="Send" method='POST' >

		<table align="center">

			<tr>	
			 <td>
                <input type="button" value="Submit" onClick="submitForm();"/>
             </td>
            </tr>
		</table>


	</form>
</body>
</html>