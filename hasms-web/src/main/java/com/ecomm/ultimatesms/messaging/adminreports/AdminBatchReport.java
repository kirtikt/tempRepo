package com.ecomm.ultimatesms.messaging.adminreports;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;

/**
 * Servlet implementation class CreditPerClient
 */
public class AdminBatchReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminBatchReport() {
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
		Logger log=LoggerFactory.getLogger(AdminBatchReport.class);

		String status = request.getParameter("status");
		String managerid = request.getParameter("managerid");
		String fromDate = request.getParameter("fromDate");
		String from	= fromDate+" 00:00:00";
		String toDate = request.getParameter("toDate");
		String to = toDate+" 23:59:59";
		

		Map<Integer, Object> map = new HashMap<Integer, Object>();
		Map<String,String> tempMap = new HashMap<String,String>();
		Set<String> set = new HashSet<String>();
		HoldingaccountManager ha=new HoldingaccountManager();
		int count=1;
		long manageridlong=Long.parseLong(managerid);
		List list = ha.getBatchesforClientManager(manageridlong, from, to);
		if(list!=null && list.size()!=0){
			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				Object[] row = (Object[]) itr.next();
				set.add((row[1]).toString().trim());
				tempMap.put((row[0].toString().trim())+(row[1].toString().trim()), row[2].toString().trim());
			}
			Iterator it = set.iterator();
			while (it.hasNext()) {
				String datetime = it.next().toString();
				Map<String, Object> smsCountMap=ha.getBatchReportMap(datetime, tempMap);
				map.put(count,smsCountMap);
				count++;
			}
		}
		else{
			map=null;
		}
		
		// Set the session attribute for download purpose.Its avoid the multiple instance removed the downloaded map
		httpSession.removeAttribute("downloadedMap");
		httpSession.setAttribute("downloadedMap",map);
		
		
		request.setAttribute("map", map);
		request.setAttribute("managerid", manageridlong);
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		//		request.setAttribute("type",displayStatus.trim());
		request.setAttribute("reportTrue",true);
		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/adminbatchreport.jsp");

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