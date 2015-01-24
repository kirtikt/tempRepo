package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.ecomm.ultimatesms.messaging.billing.Billing;
import com.ecomm.ultimatesms.messaging.commons.PhoneNumber;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.utils.Helper;
import com.ecomm.ultimatesms.messaging.utils.MessageParamValidator;

/**
 * Servlet implementation class CreditPerClient
 */
public class ConfirmMultipleMessageSending extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	
	public ConfirmMultipleMessageSending() {
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
		Logger log=LoggerFactory.getLogger(ConfirmMultipleMessageSending.class);
		 HttpSession httpSession=request.getSession(false);
		// httpSession.getAttribute("numberList");
		// httpSession.getAttribute("message");
		PrintWriter out = null;
		boolean isCreditFinish = false;
		int counterValue = 0;
		RequestDispatcher rd = null;
		//log.info("In ProcessRequest()");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String uploaddir = "/temp";
		Properties props=null;
		try {
			props=Helper.getProps();
			uploaddir = props.getProperty("uploaddirectory");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		System.setProperty("java.io.tmpdir", uploaddir);
		FileItemFactory factory = new DiskFileItemFactory();
		List<String> numberList = new ArrayList<String>();
		List<String[]> tokenList = null;
		Billing billing = new Billing();
		// * Start of Reading All the parameters from request
		String sender = " ";// = request.getParameter("sender");
		String message = " ";// = request.getParameter("message");
		String footer = " ";// = request.getParameter("footer");
		String datetime = " ";// = request.getParameter("datetime");
		String replies = " ";// = request.getParameter("replies");
		String smsc = " ";
		String deliveryinfo = " ";

		MessageParamValidator smpv = new MessageParamValidator();
		try {
			List<FileItem> list = new FileUpload(factory).parseRequest(request);
			for (FileItem fi : list) {
			
				if (!fi.isFormField()) {
				
					CSVReader reader = new CSVReader(new StringReader(
							fi.getString()));
					try {
						tokenList = reader.readAll();
					} catch (IOException e) {
						out.print(e.getStackTrace());
					}
					for (String[] token : tokenList) {
						for (int i = 0; i < token.length; i++) {
							numberList.add(token[i]);
						}
					}
					
				} else {
					if (fi.getFieldName().equals("sender")) {
						sender = fi.getString();
						httpSession.setAttribute("sender", sender);
						log.info("Sender  ===>" + sender);
					}
					if (fi.getFieldName().equals("message")) {
						message = fi.getString();
						httpSession.setAttribute("message", message);
						log.info("Message  ===>" + message);
					}
					if (fi.getFieldName().equals("replies")) {
						replies = fi.getString();
						httpSession.setAttribute("replies", replies);
						log.info("Replies  ===>" + replies);

					}
					if (fi.getFieldName().equals("footer")) {
						footer = fi.getString();
						httpSession.setAttribute("footer", footer);
						log.info("footer  ===>" + footer);
					}
					if (fi.getFieldName().equals("datetime")) {
						datetime = fi.getString();
						if (datetime.equals("")) {
							datetime = "now";
						}
						httpSession.setAttribute("datetime", datetime);
						log.info("Date Time  ===>" + datetime);
					}
				}
			}
		} catch (FileUploadException fue) {
			fue.printStackTrace();
		}
		
	
		if (httpSession != null) {
			
			String userid = (String) httpSession.getAttribute("username");
		
			long clientmanagerId = (Long) httpSession
					.getAttribute("clientmanagerId");
			
			long sysuserId = (Long) httpSession.getAttribute("sysuserId");
			message = message + " " + footer;
			
//			for (int counter = 0; counter <= numberList.size() - 1; counter++) {
//				log.info("Number ==========================>"
//						+ numberList.get(counter));
//			}
			//
			// * Send All received parameters for validation

			String error = smpv.validateParams(numberList, sender, message,
					footer, datetime, replies);
			message = smpv.check(message);
			log.info("error " + error);
			if (error.equals("")) {
				int messagecount = billing.getSmsCount(message);
				//log.info(">>>> messagecount          :"+ messagecount);
				log.info("Paramters are OK !!");
				int setSender = 0;
				if ("1".equals(replies)
						&& ("".equals(sender) || sender == null)) {
					setSender = 1;
				}
				//log.info("Number of recievers : " + numberList.size());
				PhoneNumber ph = new PhoneNumber();
				List<String> recfix_list = new ArrayList<String>();
				List<String> rejected_list = new ArrayList<String>();
				for (int counter = 0; counter <= numberList.size() - 1; counter++) {
					String rec_fix = null;
					rec_fix = ph.fixNumber(numberList.get(counter));
					if (rec_fix != null) {
						recfix_list.add(rec_fix);
					} else {
						rejected_list.add(numberList.get(counter));
						log.info( " {} Number was Rejected.",rec_fix );
					}
				}
				log.info( " Total number of valid numbers are :: {} ",recfix_list.size() );
				httpSession.setAttribute("recfixlist", recfix_list);
				httpSession.setAttribute("rejectedlist", rejected_list);
				
				Double creditNeeded=billing.getCreditsNeeded(clientmanagerId, messagecount,
						recfix_list.size());
				
				httpSession.setAttribute("creditNeeded", creditNeeded);
				rd = request.getRequestDispatcher("sms/ConfirmMultipleMessage.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					
					e.printStackTrace();
				}
			}else {
				log.info(error);
				rd = request.getRequestDispatcher("sms/MultipleSMS.jsp");
				request.setAttribute("errormsg", error);
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}

}
