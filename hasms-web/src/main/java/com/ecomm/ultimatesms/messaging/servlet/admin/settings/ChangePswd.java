package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;



import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;

/**
 * Servlet implementation class RegisterUser
 */
public class ChangePswd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePswd() {
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
	 * Update the password for user
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log = Logger.getLogger(ChangePswd.class);
		BasicConfigurator.configure();
		
		String password = request.getParameter("password");
		HttpSession session = request.getSession(false);
		long clientmanagerId = (Long) session.getAttribute("clientmanagerId");
		Long pkeyManagerid = Long
				.valueOf(request.getParameter("pkeyManagerid"));

	
		RequestDispatcher rd = null;
		SysuserManager usermanager = new SysuserManager();
		ClientmanagersManager clientmanager = new ClientmanagersManager();

		SendingInterfaceManager sendinginterfacemanager = new SendingInterfaceManager();
		if (password != null && !("".equals(password))
				&& !("null".equals(password))) {

			Session sess = null;
			Transaction tr=null;
			
			try {
				sess=HibernateUtil.getSessionFactory().openSession();
				 tr=sess.beginTransaction();
				Clientmanager cm = clientmanager.findById(pkeyManagerid);
				Sysuser user = cm.getSysuser();
				user.setPassword(password);

				user.setUc((int) clientmanagerId);
				user.setDc(new Date());
				
				sess.update(user);
				sess.update(cm);
				tr.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				sess.close();
				sess=null;
				tr=null;
			}
			log.info("Successfully Updated ");
			rd = request.getRequestDispatcher("admin/ClientsList.jsp");
		} else {
			log.info("user not added to database. parameters are null.");
			rd = request.getRequestDispatcher("admin/editclient.jsp");
			request.setAttribute("errorMessage", "please enter all parameters");
		}

		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			usermanager = null;
			clientmanager = null;

		}
	}
}
