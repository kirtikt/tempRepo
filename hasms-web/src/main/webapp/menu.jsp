
<%@page import="org.hibernate.Session"%>
<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Clientmanager"%>
<%@page import="com.ecomm.pl4sms.persistence.dao.SysuserManager,com.ecomm.pl4sms.persistence.dao.ClientManagersManagerDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Iterator"%>
<%@page
	import="com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.entities.Sms"%>
	<%@page import="org.hibernate.SessionFactory"%>
<%@page import="com.ecomm.pl4sms.persistence.dbutils.HibernateUtil" %>
<table width="100%" height="25" cellspacing="0" cellpadding="0" border="0">
  <tbody><tr>
    <td class="bgBlue"><table width="100%" cellspacing="0" cellpadding="0" border="0">
        <tbody><tr><% 
        HttpSession httpsession1 = request.getSession(false);
									if (httpsession1 != null) {
										long clientmanagerid = (Long) request.getSession(false).getAttribute("clientmanagerId");
										//	System.out.println("userid:"+userid);
										if(clientmanagerid!=0){
											ClientmanagersManager clientmanager1 = new ClientmanagersManager();
											
											Clientmanager client=clientmanager1.findById(clientmanagerid);
											int leftSmss=(int) (client.getCreditaccount().getAvailablecredit() / client.getLocalsmscost());
										%>
          <td width="30">&nbsp;</td>
          <td class="txtWhite" align="left"> &nbsp; Welcome  &nbsp; <%if(request.getSession(false)!=null){out.print(request.getSession(false).getAttribute("username"));} %> &nbsp; !!!! &nbsp; Left SMS's : <%=leftSmss %> &nbsp; Credits :: <% out.print(client.getCreditaccount().getAvailablecredit()); 
          
										client = null;
										clientmanager1=null;
										}
									}%></td>
          <td class="txtWhite" align="right">&nbsp; 
            &nbsp;| &nbsp;<a href="/payless4sms/sms/showcreditperclient.jsp" class="txtWhite">show credit</a>&nbsp; 
            &nbsp; |&nbsp; &nbsp; <a href="/payless4sms/logout.jsp" class="txtWhite">log out</a> &nbsp;&nbsp;</td>
        </tr>
      </tbody></table></td>
  </tr>
</tbody>
</table>


