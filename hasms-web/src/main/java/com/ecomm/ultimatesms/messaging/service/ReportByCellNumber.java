package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.messaging.admin.settings.AdminCreditSummaryReport;
import com.ecomm.ultimatesms.messaging.commons.PhoneNumber;




/**
 * Servlet implementation class CreditPerClient
 */
public class ReportByCellNumber extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportByCellNumber() {
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
		Logger log=LoggerFactory.getLogger(ReportByCellNumber.class);
		HttpSession httpSession=request.getSession(false);
		
	//	String fromMonth = request.getParameter("fromMonth");
	//	String fromDate = request.getParameter("fromDate");
	//	String fromYear = request.getParameter("fromYear");
	String fromDate1 = request.getParameter("fromDate");
	String 	fromDate = fromDate1.split("/")[1];
	String 	fromMonth = fromDate1.split("/")[0];
	String 	fromYear = fromDate1.split("/")[2];
	
	String toDate1 = request.getParameter("toDate");
		String toDate = toDate1.split("/")[1];
		String toMonth = toDate1.split("/")[0];
		String toYear = toDate1.split("/")[2];
		
//		String toMonth = request.getParameter("toMonth");
	//	String toDate = request.getParameter("toDate");
		//String toYear = request.getParameter("toYear");
		
		String managerid = request.getParameter("managerid");
		
		
		int fromMonthint = Integer.parseInt(fromMonth);
		int fromDateint = Integer.parseInt(fromDate);
		int fromYearint = Integer.parseInt(fromYear);
		int toMonthint = Integer.parseInt(toMonth);
		int toDateint = Integer.parseInt(toDate);
		int toYearint = Integer.parseInt(toYear);
		long manageridlong=Long.parseLong(managerid);
		String type = request.getParameter("type");
		
		
		String destination = request.getParameter("destination");
		PhoneNumber ph = new PhoneNumber();
		String fixDestination=ph.fixNumber(destination);
		Map map=new HashMap();
		
//		log.info(">>>> fromMonth          :" + fromMonth);
//		log.info(">>>> fromDate          :" + fromDate);
//		log.info(">>>> fromYear          :" + fromYear);
//		log.info(">>>> toMonth          :" + toMonth);
//		log.info(">>>> toDate          :" + toDate);
//		log.info(">>>> toYear          :" + toYear);
//		log.info(">>>> manageridlong   :" + manageridlong);
//	
		//	log.info(">>>> manager id   :" + managerid);
		
		
		String from =fromMonthint +"/"+fromDateint+"/"+fromYearint +" 00:00:00";
		String to=fromMonthint+"/"+toDateint+"/"+toYearint +" 23:59:59";
		
		request.setAttribute("from", from);
		request.setAttribute("to", to);
		request.setAttribute("fixDestination", fixDestination);
		RequestDispatcher rd=null;
		if("client".equalsIgnoreCase(type.trim())){
		 rd = request
				.getRequestDispatcher("reports/reportbycellnumber.jsp");
		}
		if("sysadmin".equalsIgnoreCase(type.trim())){
			 rd = request
					.getRequestDispatcher("adminreports/adminreportbycellnumber.jsp");
			}
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
