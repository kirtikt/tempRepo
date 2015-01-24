<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%@ include file="/title.jsp" %></title>
</head>
<body>
<% 
    HttpSession httpSession=request.getSession(false);
httpSession.removeAttribute("isActive");
httpSession.removeAttribute("isActiveAdmin");
	if(httpSession!= null){
		
		session.invalidate();
	}
	response.sendRedirect("index.jsp");
%>
</body>
</html>