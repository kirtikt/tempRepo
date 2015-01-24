
<table width="100%" height="25" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
    <td class="bgBlue"><table width="100%" cellspacing="0" cellpadding="0" border="0">
        <tbody><tr>
          <td width="30">&nbsp;</td>
          <td class="txtWhite" align="left"> &nbsp; Welcome &nbsp; <%if(request.getSession(false)!=null){out.print(request.getSession(false).getAttribute("username"));} %> &nbsp; !!!!</td>
          <td class="txtWhite" align="right">&nbsp; 
            &nbsp;<a href="/payless4sms/admin/admin.jsp"  class="txtWhite">Home</a>&nbsp; 
            &nbsp;| &nbsp;<a href="#"  class="txtWhite">Settings</a>&nbsp; 
            &nbsp;| &nbsp; <a href="/payless4sms/logout.jsp" class="txtWhite">log out</a> &nbsp;&nbsp;</td>
        </tr>
      </tbody></table></td>
  </tr>
</tbody>
</table>
