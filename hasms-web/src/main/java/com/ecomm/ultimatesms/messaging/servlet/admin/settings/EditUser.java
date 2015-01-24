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
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;

/**
 * Servlet implementation class RegisterUser
 */
public class EditUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditUser() {
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
	/**
	 * This will edit sysuser with username.clientref,smscost,replycost,sending interface 
	 * @param request
	 * @param response
	 * @throws IOException
	 */

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log=LoggerFactory.getLogger(EditUser.class);
	
		String userId = request.getParameter("userid");
		
		String clientRef = request.getParameter("clientref");
		Long sendinginterface = Long.valueOf(request.getParameter("sendinginterface"));
		String smscost = request.getParameter("smscost");
		String replycost = request.getParameter("replycost");
		
		HttpSession httpSession=request.getSession(false);
		long clientmanagerId=(Long)httpSession.getAttribute("clientmanagerId");
		Long pkeyManagerid=Long.valueOf(request.getParameter("pkeyManagerid"));
		
//		log.info("userid "+userId);
//		
//		log.info("clientRef "+clientRef);
//		log.info("sendinginterface "+sendinginterface);
//		log.info("smscost "+smscost);
//		log.info("replycost "+replycost);
//		log.info("clinetmanagerId "+clientmanagerId);
//		log.info("edited user id "+pkeyManagerid);
		
		RequestDispatcher rd=null;
		SysuserManager usermanager = new SysuserManager();
		ClientmanagersManager clientmanager=new ClientmanagersManager();
		
		SendingInterfaceManager sendinginterfacemanager=new SendingInterfaceManager();
		if (userId != null &&  clientRef != null
				&& !("".equals(userId)) &&
				 !("".equals(clientRef)) && !("null".equals(userId))
				 && !("null".equals(clientRef))) {
			Double smscost1 = Double.valueOf(smscost);
			Double replycost1 = Double.valueOf(replycost);
			log.info("Added user {} to database.",userId);
		
		    Clientmanager cm=clientmanager.findById(pkeyManagerid);
			
			
			Sysuser user=cm.getSysuser();
			user.setUsername(userId);
			user.setUserid(userId);
			user.setClientref(clientRef);
			

			user.setUc((int) clientmanagerId);
			user.setDc(new Date());

			usermanager.update(user);
			
			cm.setLocalsmscost(smscost1);
			cm.setLocalreplycost(replycost1);
			cm.setSendinginterface(sendinginterfacemanager.findById(sendinginterface));			
			clientmanager.update(cm);
		   
			log.info("Successfully Updated ");
		    rd= request.getRequestDispatcher("admin/ClientsList.jsp");
		}else{
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/editclient.jsp");
			request.setAttribute("errorMessage", "please enter all parameters");
		}
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			usermanager=null;
			
			clientmanager=null;
			
			
		}
	}
}
