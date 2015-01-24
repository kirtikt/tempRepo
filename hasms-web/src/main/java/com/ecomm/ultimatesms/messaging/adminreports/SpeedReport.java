package com.ecomm.ultimatesms.messaging.adminreports;

import java.io.IOException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;

/**
 * Servlet implementation class CreditPerClient
 */
public class SpeedReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SpeedReport() {
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
		Logger log=LoggerFactory.getLogger(SpeedReport.class);

		String managerId = request.getParameter("managerid");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");

		String from = fromDate + " 00:00:00";
		String to = toDate + " 23:59:59";

		Map<Integer, Object> map = new LinkedHashMap<Integer, Object>();
		HoldingaccountManager ha = new HoldingaccountManager();
		List<Holdingaccount> list = null;
		if(managerId != null && managerId != "")
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			Date from1 = null;
			Date to1 = null;
			try {
				from1 = sdf.parse(from);
				to1 = sdf.parse(to);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Long longId = Long.parseLong(managerId);
			list = ha.getClientBatches(longId, from1, to1);
		}
		else
		{
			list = ha.getBatches(from, to);
		}


		int count=1;

		if(list!=null && list.size()!=0){
			Iterator<Holdingaccount> itr = list.iterator();
			while (itr.hasNext()) {
				Map<String,Object> tempMap = new LinkedHashMap<String,Object>();
				Holdingaccount haRow = itr.next();
				tempMap.put("Batch Id", haRow.getPkeyholdingaccountid());
				tempMap.put("Batch Size",haRow.getCountofcellno());
				tempMap.put("DateTime", haRow.getTimedate());
				String temp = ha.getSpeedReport(haRow.getPkeyholdingaccountid());
				String[] s = temp.split("&");
				String[] s1 = s[0].split("\\.");
				String[] s2 = s[1].split("\\.");
				Long is = Long.parseLong(s1[0]);
				Long fs = Long.parseLong(s2[0]);
				Double sd =  Double.parseDouble(s[0]) - Double.parseDouble(s[1]);
				//	Long sd = is-fs;
				tempMap.put("SMSC processing speed",is);
				// Don't change the this name "system speed referring same name in jsp"
				tempMap.put("System Speed",fs);
				NumberFormat formatter = new DecimalFormat("#0.000");
				String format_sd = formatter.format(sd);
				tempMap.put("System delay", format_sd);
				map.put(count,tempMap);
				count++;
			}
		}
		else{
			map=null;
		}

		httpSession.removeAttribute("sortmap");
		httpSession.setAttribute("sortmap", map);
		
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("map", map);

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