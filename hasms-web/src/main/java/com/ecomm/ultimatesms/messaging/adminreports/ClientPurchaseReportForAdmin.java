package com.ecomm.ultimatesms.messaging.adminreports;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.CreditsManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;

/**
 * Servlet implementation class CreditPerClient
 */
public class ClientPurchaseReportForAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClientPurchaseReportForAdmin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		HttpSession httpSession=request.getSession(false);
		Logger log=LoggerFactory.getLogger(ClientPurchaseReportForAdmin.class);
    
		String status = request.getParameter("status");
		
		String fromDate = request.getParameter("fromDate");
		String from	= fromDate+" 00:00:00";
		String toDate = request.getParameter("toDate");
		String to = toDate+" 23:59:59";
		RequestDispatcher rd = null;
		String managerid1 = request.getParameter("managerid");
		String managerName=request.getParameter("managername");
		
		//long managerid = (Long) httpSession.getAttribute("clientmanagerId");
		long managerid=Long.parseLong(managerid1);
		Map map=new CreditsManager().getCreditsMap(managerid,from,to);
		
		
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("map", map);
		request.setAttribute("reportTrue",true);
		
		httpSession.removeAttribute("downloadedMap");
		httpSession.setAttribute("downloadedMap",map);
		 
		rd = request.getRequestDispatcher("adminreports/clientpurchasereportforadmin.jsp");
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}