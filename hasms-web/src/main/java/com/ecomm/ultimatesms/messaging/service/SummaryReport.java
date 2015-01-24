package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;




/**
 * Servlet implementation class CreditPerClient
 */
public class SummaryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SummaryReport() {
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
		
		Logger log=LoggerFactory.getLogger(SummaryReport.class);
		
		HttpSession httpSession=request.getSession(false);

		String status = request.getParameter("status");
		
		String managerid = request.getParameter("managerid");
		
		long manageridlong=Long.parseLong(managerid);
		
		String displayStatus = request.getParameter("day");
		
		Map map=new HashMap();
	
//		log.info(">>>> displayStatus   :" + displayStatus);
//		log.info(">>>> displayStatus   :" + status);
//		log.info(">>>> manageridlong   :" + manageridlong);
//	//	log.info(">>>> manager id   :" + managerid);
		
		SmsManager ha= new SmsManager();
		Calendar calendar = Calendar.getInstance();
		
		String toDateTopass =null;
		String fromDateTopass =null;
			
		String month="";
		if("month".equalsIgnoreCase(displayStatus.trim())){
			String fromDate1 = request.getParameter("fromDate1");
			log.info("fromDate1"+fromDate1);
			String 	fromDate = fromDate1.split("/")[1];
			String 	fromMonth = fromDate1.split("/")[0];
			String 	fromYear = fromDate1.split("/")[2];
			
			String toDate1 = request.getParameter("toDate1");
				String toDate = toDate1.split("/")[1];
				String toMonth = toDate1.split("/")[0];
				String toYear = toDate1.split("/")[2];
				
				toDateTopass=toDate1;
				fromDateTopass=fromDate1;
				
			int fromMonthint = Integer.parseInt(fromMonth);
			int fromDateint = Integer.parseInt(fromDate);
			int fromYearint = Integer.parseInt(fromYear);
			int toMonthint = Integer.parseInt(toMonth);
			int toDateint = Integer.parseInt(toDate);
			int toYearint = Integer.parseInt(toYear);
			 
			for(int i=fromMonthint;i<=toMonthint;i++){
				
				calendar.set(fromYearint, i-1, 1);
				int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				 
					String from = i+"/"+1+"/"+fromYearint +" 00:00:00";
					String to=i+"/"+maxDay+"/"+toYearint +" 23:59:59";
					 
					 
					Map  smsCountMap=ha.getMapSmsCountForAdminSummaryRep(from, to,manageridlong);
					if(i==1){
						month="January";
					}
					if(i==2){
						month="February";
					}
					if(i==3){
						month="March";
					}
					if(i==4){
						month="April";
					}
					if(i==5){
						month="May";
					}
					if(i==6){
						month="June";
					}
					if(i==7){
						month="July";
					}
					if(i==8){
						month="August";
					}
					if(i==9){
						month="September";
					}
					if(i==10){
						month="October";
					}
					if(i==11){
						month="November";
					}if(i==12){
						month="December";
						}
					map.put(month,smsCountMap);
					
				 }
		}
		if("day".equalsIgnoreCase(displayStatus.trim())){
			String fromDate1 = request.getParameter("fromDate");
			String 	fromDate = fromDate1.split("/")[1];
			String 	fromMonth = fromDate1.split("/")[0];
			String 	fromYear = fromDate1.split("/")[2];
			
			String toDate1 = request.getParameter("toDate");
				String toDate = toDate1.split("/")[1];
				String toMonth = toDate1.split("/")[0];
				String toYear = toDate1.split("/")[2];
			
				toDateTopass=toDate1;
				fromDateTopass=fromDate1;
			int fromMonthint = Integer.parseInt(fromMonth);
			int fromDateint = Integer.parseInt(fromDate);
			int fromYearint = Integer.parseInt(fromYear);
			int toMonthint = Integer.parseInt(toMonth);
			int toDateint = Integer.parseInt(toDate);
			int toYearint = Integer.parseInt(toYear);
			
			for(int i=fromDateint;i<=toDateint;i++){
					String from =fromMonthint +"/"+i+"/"+fromYearint +" 00:00:00";
					String to=fromMonthint+"/"+i+"/"+toYearint +" 23:59:59";
					Map smsCountMap=ha.getMapSmsCountForAdminSummaryRep(from, to,manageridlong);
					map.put(i,smsCountMap);
					
				 }
			 List list = new LinkedList(map.entrySet());
			 
		        //sort list based on comparator
		        Collections.sort(list, new Comparator() {
		             public int compare(Object o1, Object o2) {
			           return ((Comparable) ((Map.Entry) (o1)).getKey()).compareTo(((Map.Entry) (o2)).getKey());
		             }
			});
		}
		request.setAttribute("map",map);
		
		request.setAttribute("type",displayStatus.trim());
		request.setAttribute("toDate", toDateTopass);
		request.setAttribute("fromDate", fromDateTopass);
	
		request.setAttribute("managerid", manageridlong);

		
		
		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/summaryreport.jsp");
		
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
