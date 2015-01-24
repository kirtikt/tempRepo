<%  
	   if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive"))){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
%>
 <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-latest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrid.js/grid.import.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrid.js/grid.locale-en.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrids.js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrids.js/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrids.js/jquery.jqGrid.src.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/jqgrids/*"></script> --%>

<table width="100%" height="136" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
  
    <td width="308"><img width="308" height="136" src="/payless4sms/images/1.jpg"></td>
    <td width="473"><object width="473" height="136" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
        <param value="/payless4sms/images/anim.swf" name="movie">
        <param value="high" name="quality">
        <embed width="473" height="136" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="/payless4sms/images/anim.swf"></object></td>
    <td background="/payless4sms/images/3.jpg">&nbsp;</td>
  </tr>
  </tbody>
</table>
