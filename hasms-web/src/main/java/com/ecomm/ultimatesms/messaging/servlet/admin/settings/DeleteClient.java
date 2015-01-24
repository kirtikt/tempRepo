package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;

/**
 * Servlet implementation class RegisterUser
 */
public class DeleteClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteClient() {
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
		Logger log=LoggerFactory.getLogger(DeleteClient.class);
		
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		String pkeydeleteuser=request.getParameter("pKeyManager");
		HttpSession httpSession=request.getSession(false);
		long clientmanagerId=(Long)httpSession.getAttribute("clientmanagerId");
		
		
	//	log.info("clinetmanagerId "+clientmanagerId);
		
		RequestDispatcher rd=null;
		
		ClientmanagersManager clientmanager=new ClientmanagersManager();
		if (pkeydeleteuser != null 
				&& !("".equals(pkeydeleteuser)) && !("null".equals(pkeydeleteuser))
				) {
			long pkeydeleteuserlong=Long.parseLong(pkeydeleteuser);
			//log.info("Adding user to database.");
		

			Clientmanager cm=clientmanager.findById(pkeydeleteuserlong);
			cm.setIsdeleted(true);
			clientmanager.update(cm);

			log.info("Successfully Inserted ");
		    rd= request.getRequestDispatcher("admin/ClientsList.jsp");
		}else{
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/ClientsList.jsp");
			request.setAttribute("errorMessage", "please enter all parameters");
		}
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		}finally{
			
			clientmanager=null;
			
		}
	}
}
