package com.ecomm.ultimatesms.messaging.adminreports;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedHashMap;
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
public class SendingInterfaceSummaryReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendingInterfaceSummaryReport() {
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
		Logger log=LoggerFactory.getLogger(SendingInterfaceSummaryReport.class);
		HttpSession httpSession=request.getSession(false);

		Calendar calendar = Calendar.getInstance();
		String sendingInterfaceid = request.getParameter("sendinginterfaceid");
		String displayStatus = request.getParameter("day");

		String fromDate1 = request.getParameter("fromDate");
		int fromDateint = Integer.parseInt(fromDate1.split("/")[1]);
		int fromMonthint = Integer.parseInt(fromDate1.split("/")[0]);
		int fromYearint = Integer.parseInt(fromDate1.split("/")[2]);

		String toDate1 = request.getParameter("toDate");
		int toDateint = Integer.parseInt(toDate1.split("/")[1]);
		int toMonthint =Integer.parseInt( toDate1.split("/")[0]);
		int toYearint = Integer.parseInt(toDate1.split("/")[2]);

		Map<String,Object> map=new LinkedHashMap<String,Object>();
		SmsManager sm= new SmsManager();
		String month="";

		if("month".equalsIgnoreCase(displayStatus.trim())){
			calendar.set(fromYearint, toMonthint-1, 1);
			int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			String from = fromMonthint+"/"+1+"/"+fromYearint +" 00:00:00";
			String to=toMonthint+"/"+maxDay+"/"+toYearint +" 23:59:59";
			List list = sm.getMonthlyDataForDashboardRep(from,to,Long.parseLong(sendingInterfaceid));

			for(int i=fromMonthint;i<=toMonthint;i++){

				String str_key = i+".0"+fromYearint+".0";
				Map smsCountMap = sm.getMapSmsCountForMonthlyDashboardRep(str_key, list);

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
				}
				if(i==12){
					month="December";
				}
				map.put(month,smsCountMap);
			}
		}

		if("day".equalsIgnoreCase(displayStatus.trim())){
			String from = fromMonthint+"/"+fromDateint+"/"+fromYearint +" 00:00:00";
			String to=toMonthint+"/"+toDateint+"/"+toYearint +" 23:59:59";
			List list = sm.getDailyDataForDashboardRep(from,to,Long.parseLong(sendingInterfaceid));
			for(int i=fromDateint;i<=toDateint;i++){
				String str_key=i+".0"+fromMonthint+".0"+fromYearint+".0";
				Map smsCountMap = sm.getMapSmsCountForDailyDashboardRep(str_key,list);
				map.put("Day/"+i,smsCountMap);
			}
		}
		request.setAttribute("map",map);
		request.setAttribute("type",displayStatus.trim());
		request.setAttribute("toDate", toDate1);
		request.setAttribute("fromDate", fromDate1);
		request.setAttribute("sendinginterfaceid", sendingInterfaceid);
		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/sendingInterfacesummaryreport.jsp");
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