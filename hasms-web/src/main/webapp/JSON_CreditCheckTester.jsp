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

	var jsonInput = {	"username":"varsha",
						"password":"varshag123",
						"clientRef":"varsha"						
					 };

	alert('ws/smsbulksender/j_credit_check/' + JSON.stringify(jsonInput));
	 
	Send.action='ws/smsbulksender/j_credit_check/' + JSON.stringify(jsonInput);
	Send.submit();			
	
}

</script>
<body>

	<form name="Send" method='GET' >

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