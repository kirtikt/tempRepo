package com.ecomm.ultimatesms.messaging.admin.settings;

import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;

public class login {
	public void processRequest()  {

	/*	String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		String clientRef = request.getParameter("clientref");*/
		String userId = "jamshed";
		String password = "jamshed";
		String clientRef = "jamshed";
		long clientmanagerId=0;
		long sysuserId=0;
		
			SysuserManager usermanager=new SysuserManager();
		
		ClientmanagersManager clientmanager=new ClientmanagersManager();
		
		boolean login = false;
		List<Sysuser> userlist = usermanager.getList();
	
		Iterator it = userlist.iterator();
		String userrole = null;
		while (it.hasNext()) {
			Sysuser user = (Sysuser) it.next();
			if (userId.trim().equals(user.getUsername().trim())
					&& password.trim().equals(user.getPassword().trim())
					&& clientRef.trim().equals(user.getClientref().trim())) {
					userrole=user.getRole().getRolename();
					sysuserId=user.getPkuserid();
					clientmanagerId=clientmanager.getclientManagerId(user.getPkuserid());
					login = true;
					break;
				
			}
			user=null;//
			}
		if(login)
		{
			System.out.println("INSIDE lOGIN "+login);
			RequestDispatcher rd=null;
//			HttpSession session = request.getSession(true);
//			String sessionid = session.getId();
//			session.setAttribute("isActive", "true");
//			session.setAttribute("username", userId);
//			session.setAttribute("userrole", userrole);
//			session.setAttribute("clientmanagerId", String.valueOf(clientmanagerId));
//			session.setAttribute("sysuserId", String.valueOf(sysuserId));
		if("sysadmin".equalsIgnoreCase(userrole.trim()))
			{
//			rd = request.getRequestDispatcher("admin/admin.jsp");
			System.out.println("INSIDE ssyadmin ");
			}
			else if("client".equalsIgnoreCase(userrole.trim()))
			{
//				rd = request.getRequestDispatcher("sms/welcome.jsp");
				System.out.println("INSIDE client ");
			}
			else if("reseller".equalsIgnoreCase(userrole.trim()))
			{
//				rd = request.getRequestDispatcher("sms/welcome.jsp");
				System.out.println("INSIDE reseller ");
			}
//			try {
////				rd.forward(request, response);
//			} catch (ServletException e) {
//			
//				e.printStackTrace();
//			}
			rd=null;
			userrole=null;
		}
		else
		{
//			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
//			try {
//				request.setAttribute("errorMessage",
//						"Invalid Client Detail !!!!!");
//				rd.forward(request, response);
//			} catch (ServletException e) {
//				e.printStackTrace();
//			}
		}
		
		usermanager=null;
	}
	
	
		
	
}
