package com.ecomm.ultimatesms.messaging.clientreports;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;

/**
 * Servlet implementation class CreditPerClient
 */
public class ClientCreditReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClientCreditReport() {
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
		Logger log=LoggerFactory.getLogger(ClientCreditReport.class);
		HttpSession httpSession=request.getSession(false);
		
		Long managerid = (Long) httpSession.getAttribute("clientmanagerId");
		String from = request.getParameter("fromDate")+ " 00:00:00";
		String to = request.getParameter("toDate")+" 23:59:59";
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HoldingaccountManager ha= new HoldingaccountManager();
		Map map = ha.getBatchesMap(managerid, fromDate, toDate);
		
		httpSession.removeAttribute("downloadedMap");
		httpSession.setAttribute("downloadedMap", map);
		
		request.setAttribute("map",map);
		request.setAttribute("fromDate", from);
		request.setAttribute("toDate", to);
		RequestDispatcher rd = request
				.getRequestDispatcher("reports/clientcreditreport.jsp");
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