package com.ecomm.ultimatesms.messaging.adminreports;

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


/**
 * Servlet implementation class CreditPerClient
 */
public class AdminFailReportRedirection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminFailReportRedirection() {
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
		Logger log=LoggerFactory.getLogger(AdminFailReportRedirection.class);
//		String fromMonth = request.getParameter("fromMonth");
//		String fromDate = request.getParameter("fromDate");
//		String fromYear = request.getParameter("fromYear");
//		String toMonth = request.getParameter("toMonth");
//		String toDate = request.getParameter("toDate");
//		String toYear = request.getParameter("toYear");
		
		String status = request.getParameter("status1");
		String managerid = request.getParameter("managerid1");
		String fromDate1 = request.getParameter("fromDate1");
		String 	fromDate = fromDate1.split("/")[1];
		String 	fromMonth = fromDate1.split("/")[0];
		String 	fromYear = fromDate1.split("/")[2];
		
		String toDate1 = request.getParameter("toDate1");
			String toDate = toDate1.split("/")[1];
			String toMonth = toDate1.split("/")[0];
			String toYear = toDate1.split("/")[2];
		
		int fromMonthint = Integer.parseInt(fromMonth);
		int fromDateint = Integer.parseInt(fromDate);
		int fromYearint = Integer.parseInt(fromYear);
		int toMonthint = Integer.parseInt(toMonth);
		int toDateint = Integer.parseInt(toDate);
		int toYearint = Integer.parseInt(toYear);
		long manageridlong=Long.parseLong(managerid);
		
		String displayStatus = request.getParameter("day1");
		String recordperpage = request.getParameter("recordperpage1");
		int recordperpageint =Integer.parseInt(recordperpage);
		Map map=new HashMap();
		
//		log.info(">>>> fromMonth          :" + fromMonth);
//		log.info(">>>> fromDate          :" + fromDate);
//		log.info(">>>> fromYear          :" + fromYear);
//		log.info(">>>> toMonth          :" + toMonth);
//		log.info(">>>> toDate          :" + toDate);
//		log.info(">>>> toYear          :" + toYear);
//		log.info(">>>> displayStatus   :" + displayStatus);
//		log.info(">>>> displayStatus   :" + status);
//		log.info(">>>> manageridlong   :" + manageridlong);
//	
		String from=fromMonth+"/"+fromDate+"/"+fromYear;
		String to=toMonth+"/"+toDate+"/"+toYear;
		
		
		
		httpSession.setAttribute("fromsmreport1", from);
		httpSession.setAttribute("tomsmreport1", to);
		httpSession.setAttribute("recordperpage1", recordperpageint);
		httpSession.setAttribute("managerid1", manageridlong);
		
	
		//request.setAttribute("type",displayStatus.trim());
		
		request.setAttribute("reportTrue1",true);
		request.setAttribute("managerid1", manageridlong);
		request.setAttribute("fromDate1", from);
		request.setAttribute("toDate1", to);
		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/adminfailreport.jsp");
		
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
