package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;


/**
 * Servlet implementation class AdminLogin
 */
public class AdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminLogin() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Logger log=LoggerFactory.getLogger(AdminLogin.class);
		HttpSession httpSession = request.getSession(true);
		String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		String clientRef = request.getParameter("clientref");
		long clientmanagerId=0;
		long sysuserId=0;
		
		SysuserManager usermanager=new SysuserManager();
		
		ClientmanagersManager clientmanager=new ClientmanagersManager();
	
		List<Sysuser> userlist = usermanager.getList();
		boolean login = false;
		Iterator<Sysuser> it = userlist.iterator();
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
			user=null;
			}
	
		
		if(login)
		{
			
			RequestDispatcher rd=null;
			
			//String sessionid = session.getId();
			log.info("Logine user id:: {}",userId);
			httpSession.setAttribute("isActive", "true");
			httpSession.setAttribute("username", userId);
			httpSession.setAttribute("userrole", userrole);
			httpSession.setAttribute("clientmanagerId",clientmanagerId);
			ClientmanagersManager clientmanager1 = new ClientmanagersManager();
			Clientmanager client=clientmanager1.findById(clientmanagerId);
			httpSession.setAttribute("credit",client.getCreditaccount().getAvailablecredit());
			httpSession.setAttribute("sysuserId", sysuserId);
			
			if("sysadmin".equalsIgnoreCase(userrole.trim()))
			{
				httpSession.setAttribute("isAdmin", true);
			   rd = request.getRequestDispatcher("admin/admin.jsp");
		
			}
			else if("client".equalsIgnoreCase(userrole.trim()))
			{
				//HoldingaccountManager ham=new HoldingaccountManager();
				//ham.giveBackDepositedCredits(clientmanagerId);
				//ham=null;
				rd = request.getRequestDispatcher("sms/welcome.jsp");
				
			}
			else if("reseller".equalsIgnoreCase(userrole.trim()))
			{
				rd = request.getRequestDispatcher("sms/welcome.jsp");
				
			}
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
			
				e.printStackTrace();
			}
			rd=null;
			userrole=null;
			client=null;
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("adminLogin.jsp");
			try {
				request.setAttribute("errorMessage",
						"Invalid Client Detail !!!!!");
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			rd=null;
		}

		
		usermanager=null;
	}
	
	
		
	
}
