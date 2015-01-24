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
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Creditaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Credits;

/**
 * Servlet implementation class RegisterUser
 */
public class AddCredit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	public AddCredit() {
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
		Logger log=LoggerFactory.getLogger(AddCredit.class);
		String  credit = request.getParameter("credit");
		Long pkeyManagerid=Long.valueOf(request.getParameter("pkeyManagerid"));
		HttpSession httpSession=request.getSession(false);
		long clientmanagerId=(Long)httpSession.getAttribute("clientmanagerId");
		
	
		//log.info("credit "+credit);
		
	//	log.info("clinetmanagerId "+clientmanagerId);
		
		RequestDispatcher rd=null;
		
		ClientmanagersManager clientmanager=new ClientmanagersManager();
		CreditaccountManager creditaccountmanager=new CreditaccountManager();
		
		if (credit != null && !("".equals(credit)) && !("null".equals(credit))) {
			Double creditlong= Double.valueOf(credit);
			//log.info("Adding user to database.");
			
			
		    Clientmanager cm=clientmanager.findById(pkeyManagerid);
			Creditaccount creditaccount=cm.getCreditaccount();
			creditaccount.setAvailablecredit(creditaccount.getAvailablecredit()+creditlong);
			creditaccount.setUc((int) clientmanagerId);
			creditaccount.setDc(new Date());
			creditaccount.setPurchasedby(clientmanagerId);
			creditaccount.setPurchaseddate(System.currentTimeMillis());
			

			creditaccountmanager.update(creditaccount);
			CreditsManager creditmanager=new CreditsManager();
			Credits credits=new Credits();
			credits.setPurchasedamount(creditlong);
			credits.setPurchasedby(clientmanagerId);
			credits.setPurchaseddate(new Date());
			credits.setClientmanager(cm);
			creditmanager.add(credits);
			creditmanager=null;
			credits=null;
			
			log.info("Successfully Inserted ");
		    rd= request.getRequestDispatcher("admin/ClientsList.jsp");
		}else{
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/addcredit.jsp");
			request.setAttribute("errorMessage", "please enter all parameters");
		}
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			
			clientmanager=null;
			creditaccountmanager=null;
			
			
		
		}
	}
}
