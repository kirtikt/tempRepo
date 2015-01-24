package com.ecomm.ultimatesms.messaging.clientreports;

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

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;

/**
 * Servlet implementation class CreditPerClient
 */
public class MultiReports extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MultiReports() {
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
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}

		Logger log = LoggerFactory.getLogger(MultiReports.class);

		HttpSession httpSession = request.getSession(false);

		String status = request.getParameter("status");

		String reporttype = request.getParameter("reporttype");

		// String managerid =
		// request.getSession().getAttribute("clientmanagerId");

		String displayStatus = request.getParameter("day");

		String toDateTopass = null;
		String fromDateTopass = null;

		System.out.println("manager id::"
				+ httpSession.getAttribute("clientmanagerId"));
		long manageridlong = (Long) httpSession.getAttribute("clientmanagerId");

		Map map = new HashMap();
//
//		log.info(">>>> displayStatus   :" + displayStatus);
//		log.info(">>>> displayStatus   :" + status);
//		log.info(">>>> manageridlong   :" + manageridlong);
//		log.info(">>>> **************reporttype   :" + reporttype);

		String fromDate1 = "";
		String fromDate = "";
		String fromMonth = "";
		String fromYear = "";

		String toDate1 = "";
		String toDate = "";
		String toMonth = "";
		String toYear = "";

		int fromMonthint = 0;
		int fromDateint = 0;
		int fromYearint = 0;
		int toMonthint = 0;
		int toDateint = 0;
		int toYearint = 0;

		if ("month".equalsIgnoreCase(displayStatus.trim())) {
			fromDate1 = request.getParameter("fromDate1");
			fromDate = fromDate1.split("/")[1];
			fromMonth = fromDate1.split("/")[0];
			fromYear = fromDate1.split("/")[2];

			toDate1 = request.getParameter("toDate1");
			toDate = toDate1.split("/")[1];
			toMonth = toDate1.split("/")[0];
			toYear = toDate1.split("/")[2];

			toDateTopass = toDate1;
			fromDateTopass = fromDate1;

			fromMonthint = Integer.parseInt(fromMonth);
			fromDateint = Integer.parseInt(fromDate);
			fromYearint = Integer.parseInt(fromYear);
			toMonthint = Integer.parseInt(toMonth);
			toDateint = Integer.parseInt(toDate);
			toYearint = Integer.parseInt(toYear);

		}
		if ("day".equalsIgnoreCase(displayStatus.trim())) {
			fromDate1 = request.getParameter("fromDate");
			fromDate = fromDate1.split("/")[1];
			fromMonth = fromDate1.split("/")[0];
			fromYear = fromDate1.split("/")[2];

			toDate1 = request.getParameter("toDate");
			toDate = toDate1.split("/")[1];
			toMonth = toDate1.split("/")[0];
			toYear = toDate1.split("/")[2];

			toDateTopass = toDate1;
			fromDateTopass = fromDate1;

			fromMonthint = Integer.parseInt(fromMonth);
			fromDateint = Integer.parseInt(fromDate);
			fromYearint = Integer.parseInt(fromYear);
			toMonthint = Integer.parseInt(toMonth);
			toDateint = Integer.parseInt(toDate);
			toYearint = Integer.parseInt(toYear);
		}
		// If report type is ----> Advanced report
		if ("advr".equals(reporttype.trim())) {
			request.setAttribute("from",fromDate1);
			request.setAttribute("to",toDate1);
			
			RequestDispatcher rd = request
					.getRequestDispatcher("reports/advancereport.jsp");

			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		// If report type is ----> Batch report
		if ("btchr".equals(reporttype.trim())) {
			String from = fromMonth + "/" + fromDate + "/" + fromYear
					+ " 00:00:00";
			String to = toMonth + "/" + toDate + "/" + toYear + " 23:59:59";

			List<Holdingaccount> list = new HoldingaccountManager().getBatches(
					manageridlong, from, to);
			SmsManager sm = new SmsManager();
			int count = 1;
			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					Holdingaccount ha = list.get(i);
					Map smsCountMap = sm.getBatchSmsStatusCount(ha);
					map.put(count, smsCountMap);
					count++;
				}
			} else {
				map = null;
			}
			request.setAttribute("fromDate", fromDateTopass);
			request.setAttribute("toDate", toDateTopass);
			request.setAttribute("map", map);
			request.setAttribute("reportTrue", true);

			RequestDispatcher rd = request
					.getRequestDispatcher("reports/batchreport.jsp");

			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

		// If report type is --->Summary report

		if ("sur".equals(reporttype.trim())) {
			SmsManager ha = new SmsManager();
			Calendar calendar = Calendar.getInstance();

			String month = "";
			if ("month".equalsIgnoreCase(displayStatus.trim())) {

				toDateTopass = toDate1;
				fromDateTopass = fromDate1;

				for (int i = fromMonthint; i <= toMonthint; i++) {
					calendar.set(fromYearint, i - 1, 1);
					int maxDay = calendar
							.getActualMaximum(Calendar.DAY_OF_MONTH);

					String from = i + "/" + 1 + "/" + fromYearint + " 00:00:00";
					String to = i + "/" + maxDay + "/" + toYearint
							+ " 23:59:59";
					//log.info("from::" + from + "to::" + to);
					Map smsCountMap = ha.getMapSmsCountForClientSummaryRep(
							from, to, manageridlong);
					if (i == 1) {
						month = "January";
					}
					if (i == 2) {
						month = "February";
					}
					if (i == 3) {
						month = "March";
					}
					if (i == 4) {
						month = "April";
					}
					if (i == 5) {
						month = "May";
					}
					if (i == 6) {
						month = "June";
					}
					if (i == 7) {
						month = "July";
					}
					if (i == 8) {
						month = "August";
					}
					if (i == 9) {
						month = "September";
					}
					if (i == 10) {
						month = "October";
					}
					if (i == 11) {
						month = "November";
					}
					if (i == 12) {
						month = "December";
					}
					map.put(month, smsCountMap);

				}
			}
			if ("day".equalsIgnoreCase(displayStatus.trim())) {

				toDateTopass = toDate1;
				fromDateTopass = fromDate1;

				for (int i = fromDateint; i <= toDateint; i++) {
					String from = fromMonthint + "/" + i + "/" + fromYearint
							+ " 00:00:00";
					String to = fromMonthint + "/" + i + "/" + toYearint
							+ " 23:59:59";
					//log.info("from::" + from + "to::" + to);
					Map smsCountMap = ha.getMapSmsCountForClientSummaryRep(
							from, to, manageridlong);
					map.put(i, smsCountMap);

				}
				List list = new LinkedList(map.entrySet());

				// sort list based on comparator
				Collections.sort(list, new Comparator() {
					public int compare(Object o1, Object o2) {
						return ((Comparable) ((Map.Entry) (o1)).getKey())
								.compareTo(((Map.Entry) (o2)).getKey());
					}
				});
			}
			request.setAttribute("map", map);

			request.setAttribute("type", displayStatus.trim());
			request.setAttribute("toDate", toDateTopass);
			request.setAttribute("fromDate", fromDateTopass);

			RequestDispatcher rd = request
					.getRequestDispatcher("reports/clientsummaryreport.jsp");

			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		// if report type is --->detailed summary report
		if ("dsur".equals(reporttype.trim())) {
			SmsManager ha = new SmsManager();
			Calendar calendar = Calendar.getInstance();

			String month = "";
			if ("month".equalsIgnoreCase(displayStatus.trim())) {

				toDateTopass = toDate1;
				fromDateTopass = fromDate1;

				for (int i = fromMonthint; i <= toMonthint; i++) {
					calendar.set(fromYearint, i - 1, 1);
					int maxDay = calendar
							.getActualMaximum(Calendar.DAY_OF_MONTH);

					String from = i + "/" + 1 + "/" + fromYearint + " 00:00:00";
					String to = i + "/" + maxDay + "/" + toYearint
							+ " 23:59:59";
				//	log.info("from::" + from + "to::" + to);
					Map smsCountMap = ha
							.getMapSmsCountForAdminSummaryDetailedRep(from, to,
									manageridlong);
					if (i == 1) {
						month = "January";
					}
					if (i == 2) {
						month = "February";
					}
					if (i == 3) {
						month = "March";
					}
					if (i == 4) {
						month = "April";
					}
					if (i == 5) {
						month = "May";
					}
					if (i == 6) {
						month = "June";
					}
					if (i == 7) {
						month = "July";
					}
					if (i == 8) {
						month = "August";
					}
					if (i == 9) {
						month = "September";
					}
					if (i == 10) {
						month = "October";
					}
					if (i == 11) {
						month = "November";
					}
					if (i == 12) {
						month = "December";
					}
					map.put(month, smsCountMap);

				}
			}
			if ("day".equalsIgnoreCase(displayStatus.trim())) {

				toDateTopass = toDate1;
				fromDateTopass = fromDate1;

				for (int i = fromDateint; i <= toDateint; i++) {
					String from = fromMonthint + "/" + i + "/" + fromYearint
							+ " 00:00:00";
					String to = fromMonthint + "/" + i + "/" + toYearint
							+ " 23:59:59";
				//	log.info("from::" + from + "to::" + to);
					Map smsCountMap = ha
							.getMapSmsCountForAdminSummaryDetailedRep(from, to,
									manageridlong);
					map.put(i, smsCountMap);

				}
				List list = new LinkedList(map.entrySet());

				// sort list based on comparator
				Collections.sort(list, new Comparator() {
					public int compare(Object o1, Object o2) {
						return ((Comparable) ((Map.Entry) (o1)).getKey())
								.compareTo(((Map.Entry) (o2)).getKey());
					}
				});
			}
			request.setAttribute("map", map);

			request.setAttribute("type", displayStatus.trim());
			request.setAttribute("toDate", toDateTopass);
			request.setAttribute("fromDate", fromDateTopass);

			RequestDispatcher rd = request
					.getRequestDispatcher("reports/detailedclientsummaryreport.jsp");

			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

	}

}
