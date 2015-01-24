package com.ecomm.ultimatesms.messaging.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.ecomm.ultimatesms.designpattern.Observable;
import com.ecomm.ultimatesms.designpattern.ObservableImpl;
import com.ecomm.ultimatesms.designpattern.ObserverImpl;
import com.ecomm.ultimatesms.messaging.billing.Billing;
import com.ecomm.ultimatesms.messaging.commons.Commons;
import com.ecomm.ultimatesms.messaging.commons.PhoneNumber;
import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxyManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.MessageParamValidator;

/**
 * Servlet implementation class SendMultipleMessage
 */
public class SendMultipleMessage123 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMultipleMessage123() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}


	void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		PrintWriter out = null;
		boolean isCreditFinish = false;
		int counterValue = 0;
		RequestDispatcher rd = null;
		System.out.println("In ProcessRequest()");
		try {
			System.out.println("In Process request");
			out = response.getWriter();
		} catch (IOException e) {
			System.out.println("Couldnot fetch out object.");
			e.printStackTrace();
		}
		Properties props=new Properties();
		String uploaddir="/temp";
		try {
			props.load(new FileInputStream("/opt/jboss6/server/default/conf/ecommproperties/ultimatesms.properties"));
			System.out.println("property name::"+props.getProperty("uploaddirectory"));
			System.out.println("System property::"+System.getProperty("uploaddirectory"));
			uploaddir=System.getProperty("uploaddirectory");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.setProperty("java.io.tmpdir", uploaddir);
		FileItemFactory factory = new DiskFileItemFactory();
		List<String> numberList = new ArrayList<String>();
		List<String[]> tokenList = null;
		Billing billing=new Billing();
		System.out.println("================= In Multiple SMS Sender Servlet ===================");
		
//		 * Start of Reading All the parameters from request
		String sender = null;// = request.getParameter("sender");
		String message = null;// = request.getParameter("message");
		String footer = null;// = request.getParameter("footer");
		String datetime = null;// = request.getParameter("datetime");
		String replies = null;// = request.getParameter("replies");
		String smsc = null;
		String deliveryinfo=null;
		
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
					System.out.print("Is in memory after reader :"+fi.isInMemory());
				} else {
					if (fi.getFieldName().equals("sender")) {
						sender = fi.getString();
						System.out.println("Sender  ===>" + sender);
					}
					if (fi.getFieldName().equals("message")) {
						message = fi.getString();
						System.out.println("Message  ===>" + message);
					}
					if (fi.getFieldName().equals("replies")) {
						replies = fi.getString();
						System.out.println("Replies  ===>" + replies);

					}
					if (fi.getFieldName().equals("footer")) {
						footer = fi.getString();
						System.out.println("footer  ===>" + footer);
					}
					if (fi.getFieldName().equals("datetime")) {
						datetime = fi.getString();
						if (datetime.equals("")) {
							datetime = "now";
						}
						System.out.println("Date Time  ===>" + datetime);
					}
				}
			}
		} catch (FileUploadException fue) {
			out.print(fue.getStackTrace());
		}
		
//		 * Finsh Reading All the parameters from request
		 
		HttpSession httpSession = request.getSession(false);
		//String userid = null;
		if (httpSession != null) {
			//userid = (String) httpSession.getAttribute("userid");
			String userid = (String)httpSession.getAttribute("username");
			System.out.println(">>>> userid          :"+userid);
			long clientmanagerId=(Long) httpSession.getAttribute("clientmanagerId");
			System.out.println(">>>> clientmanagerId          :" + clientmanagerId);
			long sysuserId=(Long) httpSession.getAttribute("sysuserId");
				message = message + " " + footer;
		System.out.println("Number's List from csv file reader" + numberList);
		for (int counter = 0; counter <= numberList.size() - 1; counter++) {
			System.out.println("Number ==========================>"
					+ numberList.get(counter));
		}
//		
//		 * Send All received parameters for validation
		 

		String error = smpv.validateParams(numberList, sender, message,footer, datetime, replies);
		message=smpv.check(message);
		System.out.println("error "+error);
		if (error.equals("")) {
			int messagecount=billing.getSmsCount(message);
			System.out.println(">>>> messagecount          :" + messagecount);
			System.out.println("Paramters are OK !!");
			int setSender = 0;
			if ("1".equals(replies)
					&& ("".equals(sender) || sender == null)) {
				setSender = 1;
			}
			// check user credits and smsc credits
			System.out.println("Number of recievers : "+ numberList.size());
			PhoneNumber ph = new PhoneNumber();
			List<String> recfix_list =new ArrayList<String>();
			for (int counter = 0; counter <= numberList.size() - 1; counter++) {
				String rec_fix=null;
				rec_fix = ph.fixNumber(numberList.get(counter));
				if(rec_fix != null){
					recfix_list.add(rec_fix);
				}
				else{
					System.out.println(rec_fix +"Number was Rejected.");
				}
			}
			
			System.out.println("newly created list size :: "+recfix_list.size());
		
			if(billing.checkClientCredit(clientmanagerId, messagecount, recfix_list.size()))
			{
				if(billing.checkAdminCredit(clientmanagerId, messagecount, recfix_list.size()))
				{
					/*
					 * Take deposit from client and added to holding account
					 */
					Holdingaccount ha=new Billing().depositCredit(clientmanagerId, messagecount, numberList.size()-1);
					
					for (int counter = 0; counter < recfix_list.size(); counter++) {
								/*
								 * Send message one by one
								 */
								//String reciever_fixed=null;
								UUID uuid = UUID.randomUUID();
								System.out.println("In Counter");
								
								//reciever_fixed = ph.fixNumber(recfix_list.get(counter));
							//	if (reciever_fixed != null) 
							//	{
									System.out.println("\n\nFixed Reciever : "+ recfix_list.get(counter) + "\n\n");
									TextMessage1 textMessage = new TextMessage1();
									if (setSender == 1)
									{
										System.out.println("(\"1\".equals(replies) && (\"\".equals(sender) || sender == null) )");
										Commons c = new Commons();
										sender = c.searchForSender(recfix_list.get(counter));
										c = null;
										if("".equals(sender) || sender == null || "null".equals(sender));
										{
											sender="Default";
											System.out.println("No operator available for the reciever"+recfix_list.get(counter));
											
										}
									}
									ClientmanagersManager clientmanager=new ClientmanagersManager();
									
									Clientmanager client=clientmanager.findById(clientmanagerId);
									SmscproxyManager smscproxymanager=new SmscproxyManager();
									//Smscproxy smscproxy=smscproxymanager.findById();
									textMessage.setSmscount(messagecount);
									textMessage.setSender(sender);
									textMessage.setReciever(recfix_list.get(counter));
									textMessage.setMessage(message);
									textMessage.setFooter(footer);
									//textMessage.setSmsc(smscproxy.getName());
									textMessage.setSmsc(client.getSendinginterface().getKannelname());
									System.out.println(" client.getSendinginterface().getInterfacename()"+client.getSendinginterface().getKannelname());
									textMessage.setMessageID(uuid.toString());
									textMessage.setDatetime(String.valueOf(System.currentTimeMillis()));
									textMessage.setSysuserId(sysuserId);
									textMessage.setClientmanagerId(clientmanagerId);
									textMessage.setHoldingaccountId(ha.getPkeyholdingaccountid());
									textMessage.setDeliveryinfo(deliveryinfo);
									
									// under construction 14 sept
									try 
									{
										
										// Explicitly set by me -----> Status
										Sms sms = new Sms();
										System.out.println("PKEy at send single sms servlet ==============>:");
										sms.setSource(sender);
										sms.setClientmanager(client);
//										sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
										Double creditAfter=(Double)ha.getCreditsleft();
										sms.setCreditafter(creditAfter);	
										sms.setDatetime(new Date());
										sms.setDestination(recfix_list.get(counter));
										sms.setMessage(message);
										sms.setStatus("11");         // Message delivered to servlet
										//System.out.println(" client.getSendinginterface().getInterfacename()"+client.getSendinginterface().getInterfacename());
										
										sms.setSendinginterface(client.getSendinginterface());
										sms.setHoldingaccount(ha);
										sms.setSmscount(messagecount);
										sms.setSmsid(uuid.toString());
										sms.setUc((int) sysuserId);
										sms.setDc(new Date());
/*please see for the delivery info*/	sms.setDeliveryinfo(deliveryinfo);
										SmsManager smsmanager = new SmsManager();
										smsmanager.add(sms);
										smsmanager=null;
										sms=null;
										client=null;
									
										System.out.println(" SendSingleMessage Message sent to Servlet pk ================> ");
					
									} catch (Exception e) 
									{
										e.printStackTrace();
									} 
									
						/*			 * Send Message to JMSQueue
									 * 
									 * TransferMessageToQueue tmtq = new
									 * TransferMessageToQueue(); System.out
									 * .println(
									 * "Sending message to TransferMessageToQueue() "
									 * );
									 * System.out.println("Content of the message is \n"
									 * + textMessage);
									 * tmtq.sendMessagetoJMS(textMessage);
									 * 
									 */
									ObserverImpl observer = new ObserverImpl(textMessage);
									Observable observableImpl = new ObservableImpl();
									observableImpl.add(observer);
									observableImpl.notifyOperations();

									uuid = null;

								//	reciever_fixed = null;
								//} else 
								//{
								//	System.out.println(numberList.get(counter)
								//			+ "Number was Rejected.");
								//}
							
					}// End of for loop of receivers
	
				}
				else
				{
					isCreditFinish = true;
					System.out.println("message not sent beacause of Insufficient credits in admins wallet");
					
				}
			}
			else 
			{

				isCreditFinish = true;
//				counterValue = counter;
				System.out
						.println("message not sent beacause of Insufficient clients credits");
			}
			
			if (isCreditFinish) {
				System.out
						.println("message not sent beacause of Insufficient credits");
				rd = request.getRequestDispatcher("sms/MultipleSMS.jsp");
				//int creditRequired = recfix_list.size() - counterValue;
				request.setAttribute("errormsg",
						"Message not sent beacause of Insufficient credits. ");
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			if (!isCreditFinish) {
				System.out.println("Message sent Successfully !!!");
				rd = request.getRequestDispatcher("sms/MultipleSMS.jsp");
				request.setAttribute("successmessage",
						"Message sent Successfully !!!");
				try {
					rd.forward(request, response);
				} catch (ServletException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println(error);
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
	
	}// End of if(httpSession)

}// End of proessrequest method


	}
