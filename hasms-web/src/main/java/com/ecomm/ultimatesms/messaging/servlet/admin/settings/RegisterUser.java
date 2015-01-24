package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.CreditaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.CreditsManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.RoleManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Creditaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Credits;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;

/**
 * Servlet implementation class RegisterUser
 */
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log=LoggerFactory.getLogger(RegisterUser.class);
		
		String userId = request.getParameter("userid");
		String password = request.getParameter("password");
		String clientRef = request.getParameter("clientref");
		Long sendinginterface = Long.valueOf(request.getParameter("sendinginterface"));
		Long role = Long.valueOf(request.getParameter("role"));
		Double  credit =  Double.valueOf(request.getParameter("credit"));
		Double smscost = Double.valueOf(request.getParameter("smscost"));
		Double replycost = Double.valueOf(request.getParameter("replycost"));
		HttpSession httpSession=request.getSession(false);
		long clientmanagerId=(Long)httpSession.getAttribute("clientmanagerId");
		
//		log.info("userid "+userId);
//		log.info("password "+password);
//		log.info("clientRef "+clientRef);
//		log.info("sendinginterface "+sendinginterface);
//		log.info("role "+role);
//		log.info("credit "+credit);
//		log.info("smscost "+smscost);
//		log.info("replycost "+replycost);
//		log.info("clinetmanagerId "+clientmanagerId);
//		
		RequestDispatcher rd=null;
		SysuserManager usermanager = new SysuserManager();
		RoleManager rolemanager=new RoleManager();
		ClientmanagersManager clientmanager=new ClientmanagersManager();
		Clientmanager clientmg=new Clientmanager();
		CreditaccountManager creditaccountmanager=new CreditaccountManager();
		Creditaccount creditaccount=new Creditaccount();
		SendingInterfaceManager sendinginterfacemanager=new SendingInterfaceManager();
		if (userId != null && password != null && clientRef != null
				&& !("".equals(userId)) && !("".equals(password))
				&& !("".equals(clientRef)) && !("null".equals(userId))
				&& !("null".equals(password)) && !("null".equals(clientRef))) {
			log.info("Adding user to database.");
			Sysuser user=new Sysuser();
			user.setUsername(userId);
			user.setUserid(userId);
			user.setClientref(clientRef);
			user.setPassword(password);
			
			user.setRole(rolemanager.findById(role));
			user.setUc((int) clientmanagerId);
			user.setDc(new Date());
			usermanager.add(user);
			
			creditaccount.setAvailablecredit(credit);
			creditaccount.setSpendcredit(0d);
			creditaccount.setUc((int) clientmanagerId);
			creditaccount.setDc(new Date());
			creditaccount.setPurchasedby(clientmanagerId);
			creditaccount.setPurchaseddate(System.currentTimeMillis());
			creditaccountmanager.add(creditaccount);
			
			clientmg.setLocalsmscost(smscost);
			clientmg.setLocalreplycost(replycost);
			clientmg.setSysuser(user);
			clientmg.setIsdeleted(false);
			clientmg.setCreatedby(clientmanagerId);
			clientmg.setCreditaccount(creditaccount);
			
			
			clientmg.setSendinginterface(sendinginterfacemanager.findById(sendinginterface));
			clientmanager.add(clientmg);
			
			CreditsManager creditmanager=new CreditsManager();
			Credits credits=new Credits();
			credits.setPurchasedamount(credit);
			credits.setPurchasedby(clientmanagerId);
			credits.setPurchaseddate(new Date());
			credits.setClientmanager(clientmg);
			creditmanager.add(credits);
		   
			log.info("Successfully Inserted user {} ",userId);
		    rd= request.getRequestDispatcher("admin/admin.jsp");
		}else{
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/clientRegistration.jsp");
			request.setAttribute("errorMessage", "please enter all parameters");
		}
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			usermanager=null;
			rolemanager=null;
			clientmanager=null;
			creditaccountmanager=null;
			creditaccount=null;
			clientmg=null;
			sendinginterfacemanager=null;
			
		}
	}
}
