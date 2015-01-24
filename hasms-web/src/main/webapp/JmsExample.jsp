<%@page import="org.hornetq.api.jms.management.JMSQueueControl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--  
  System.out.println("Number of message_:"+queueControl.getMessageCount());
	       System.out.println("DeliveringCount_:"+queueControl.getDeliveringCount());
	       System.out.println("Messages Added_:"+queueControl.getMessagesAdded());
	       System.out.println("Messages Added_:"+queueControl.getScheduledCount());
	       System.out.println("Counter History _:"+ queueControl.listMessageCounterHistory());
	       System.out.println("Number of Expire Mesage "+queueControl.expireMessages(""));
-->

Hellloooooooooooooo

Number of message_:
<c:out value="${queueControl.getMessageCount()}"/>
<br>
DeliveringCount_:
<c:out value="${queueControl.getDeliveringCount()}"/>
<br>
Messages Added_
<c:out value="${queueControl.getMessagesAdded()}"/>
<br>
Messages Added_
<c:out value="${queueControl.getScheduledCount()}"/>
<br>
Counter History _:
<c:out value="${queueControl.listMessageCounterHistory()}"/>
<br>
Number of Expire Mesage
<c:out value="${queueControl.expireMessages('')}"/>
<br>
Number of Removed Mesage
<c:out value="${queueControl.removeMessages('')}"/>
<br>



</body>
</html>