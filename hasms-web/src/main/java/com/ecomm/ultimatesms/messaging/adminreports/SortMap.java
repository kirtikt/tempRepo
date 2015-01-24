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

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.ultimatesms.messaging.utils.Helper;

/**
 * Servlet implementation class CreditPerClient
 */
public class SortMap extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SortMap() {
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
		Logger log=LoggerFactory.getLogger(SortMap.class);

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String sortColumn = request.getParameter("sortColumn");
		String sortType = request.getParameter("sortType");

		String from = fromDate + " 00:00:00";
		String to = toDate + " 23:59:59";

		Map<Integer, Object> map = (Map)httpSession.getAttribute("sortmap");
		
		int count=1;

		Map Sortedmap=new Helper().SortMap(map,sortColumn,sortType);
		
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		
		request.setAttribute("map", Sortedmap);
		request.setAttribute("sortType", sortType);

		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/speedreport.jsp");

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