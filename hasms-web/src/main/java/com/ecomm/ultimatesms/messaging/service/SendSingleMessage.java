package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.messaging.billing.Billing;
import com.ecomm.ultimatesms.messaging.commons.PhoneNumber;
import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.MessageParamValidator;

/**
 * Servlet implementation class SendSingleMessage
 */
public class SendSingleMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SendSingleMessage() {
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

	/**
	 * Method Triggered when index.jsp submits its info the current page
	 * "SendSingleMessage".
	 * 
	 * @param request
	 * @param response
	 */
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log=LoggerFactory.getLogger(SendSingleMessage.class);
		PrintWriter out = null;
		boolean isCreditFinish = false;
		RequestDispatcher rd = null;
		int counterValue = 0;
		List textMessageList=new LinkedList();
		HttpSession httpSession = request.getSession(false);
		Billing billing=new Billing();
		
		
		
		if (httpSession != null) {
			
			/*
			String userid = (String) httpSession.getAttribute("userid");
			log.info(">>>> userid          :" + userid);
			long clientmanagerId=Long.valueOf(httpSession.getAttribute("clientmanagerId").toString());*/
			String userid = (String)httpSession.getAttribute("username");
			long clientmanagerId=(Long) httpSession.getAttribute("clientmanagerId");
			long sysuserId=(Long) httpSession.getAttribute("sysuserId");
			response.setContentType("text/html");
			
		/*	String userid = "jamshed";
			log.info(">>>> userid          :" + userid);
			long clientmanagerId=2;
			log.info(">>>> clientmanagerId          :" + clientmanagerId);
			String sysuserId="2";*/
			response.setContentType("text/html");
				MessageParamValidator smpv = new MessageParamValidator();
			/**
			 * Fetch all the parameters sent by index.jsp
			 */
			try {
				
				out = response.getWriter();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			String title = " Reading All Request Parameters ";
			
			Enumeration paramNames = request.getParameterNames();
		
			String reciever = request.getParameter("receiver");
			String sender="Default";
			if(request.getParameter("sender")!=null){
			 sender = request.getParameter("sender");
			}
			String message = request.getParameter("message");
			String footer = request.getParameter("footer");
			message = message + " " + footer;
			String datetime = request.getParameter("datetime");
			String replies = request.getParameter("replies");
			String deliveryinfo = "yes";
			log.info(">>>> reciever          :" + reciever);
			log.info(">>>> sender          :" + sender);
			log.info(">>>> message          :" + message);
//			log.info(">>>> footer          :" + footer);
//			log.info(">>>> message          :" + message);
//			log.info(">>>> datetime          :" + datetime);
//			log.info(">>>> replies          :" + replies);
//		
			
			System.out.println("------------->datetime  "+datetime);
			
			
			if (datetime.equals("")) {
				datetime = "now";
			}
			
			
			String error = smpv.validateParams(reciever, sender, message,footer, datetime, replies);
			message=smpv.check(message);
			//log.info("The Replaced message is "+message);
			//log.info("error "+error);
			if (error.equals("")) {
				int messagecount=billing.getSmsCount(message);
				//log.info(">>>> messagecount          :" + messagecount);
				String[] receivers_arr = reciever.split(",");
				List<String> recfix_list =new ArrayList<String>();
				
				
				/*Send delivery report here sender manually provides his number*/
				int setSender = 0;
				if ("1".equals(replies)
						&& ("".equals(sender) || sender == null)) {
					setSender = 1;
				}
				// check user credits and smsc credits
				//log.info("Number of recievers : "+ receivers_arr.length);				
				PhoneNumber ph = new PhoneNumber();
				
				for (int counter = 0; counter <= receivers_arr.length - 1; counter++) {
					String rec_fix=null;
					rec_fix = ph.fixNumber(receivers_arr[counter]);
					if(rec_fix != null){
						recfix_list.add(rec_fix);
					}
					else{
						log.info("{} Number was Rejected.",rec_fix );
					}
				}
				//log.info("{}",recfix_list.size());
				if(billing.checkClientCredit(clientmanagerId, messagecount, recfix_list.size()))
				{
					if(billing.checkAdminCredit(clientmanagerId, messagecount, recfix_list.size()))
					{
						/*
						 * Take deposit from client and added to holding account
						 */
						
						Holdingaccount ha=new Billing().depositCredit(clientmanagerId, messagecount, recfix_list.size());
						
						for (int counter = 0; counter < recfix_list.size(); counter++) {
									
							
							//Gives an unique identifier
									UUID uuid = UUID.randomUUID();
									//log.info("In Counter");log.info("\n\nFixed Reciever : "+ recfix_list.get(counter) + "\n\n");
										TextMessage1 textMessage = new TextMessage1();
										
										ClientmanagersManager clientmanager=new ClientmanagersManager();
										
										Clientmanager client=clientmanager.findById(clientmanagerId);
										//SmscproxyManager smscproxymanager=new SmscproxyManager();
										//Smscproxy smscproxy=smscproxymanager.findById(client.getFksmscid());
										textMessage.setSmscount(messagecount);
										textMessage.setSender(sender);
										textMessage.setReciever(recfix_list.get(counter));
										textMessage.setMessage(message);
										textMessage.setFooter(footer);
										//textMessage.setSmsc(client.getSendinginterface().getKannelname());
										if (client != null) {
											textMessage.setSmsc(client
													.getSendinginterface().getKannelname());
											}
										textMessage.setMessageID(uuid.toString());
										DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
								    	Date date = new Date();
								    	String dd=dateFormat.format(date).toString();
										textMessage.setDatetime(dd);
										textMessage.setFutureDate(datetime);
										textMessage.setSysuserId(sysuserId);
										textMessage.setClientmanagerId(clientmanagerId);
									
										if (ha != null) {
											textMessage.setHoldingaccountId(ha
													.getPkeyholdingaccountid());
										}
									
										textMessage.setDeliveryinfo(deliveryinfo);
										
										// under construction 14 sept
										try 
										{
											// Explicitly set by me -----> Status
											Sms sms = new Sms();
											sms.setSource(sender);
											sms.setClientmanager(client);
//											sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
											Double creditAfter = 0d;
											if (ha != null) {
												creditAfter = (Double) ha.getCreditsleft();
											}
											sms.setCreditafter(creditAfter);	
											sms.setDatetime(new Date());
											sms.setDestination(recfix_list.get(counter));
											sms.setMessage(message);
											sms.setStatus("00");         // Message delivered to servlet
										//	sms.setFksmscid(client.getFksmscid());
											sms.setSendinginterface(client.getSendinginterface());
											sms.setHoldingaccount(ha);
											sms.setSmscount(messagecount);
											sms.setSmsid(uuid.toString());
											sms.setUc((int) sysuserId);
											sms.setDc(new Date());
/* please see for the delivery info*/		sms.setDeliveryinfo(deliveryinfo);
											SmsManager smsmanager=new SmsManager();
											smsmanager.add(sms);
											log.info("//************ Loaded the sms for {} ***************// ",recfix_list.get(counter));
											
											smsmanager=null;
											sms=null;
											client=null;
										
											
										} catch (Exception e) 
										{
											e.printStackTrace();
										} 
										textMessageList.add(textMessage);
										
//										ObserverImpl observer = new ObserverImpl(textMessage);
//										Observable observableImpl = new ObservableImpl();
//										observableImpl.add(observer);
//										observableImpl.notifyOperations();

										uuid = null;
							}// End of for loop of receivers
						/*
						 *  pass list to thread thread will send the sms to kannel
						 */
						if("now".equalsIgnoreCase(datetime)){
							Thread t=new SendSmsProcess(textMessageList);
							t.start();
						}else{
							Thread st=new StoredFutureSms(textMessageList);
							st.start();
						}
						
					}
					else
					{
						isCreditFinish = true;
						log.info("message not sent beacause of Insufficient credits in admins wallet");
					}
				}
				else 
				{
					isCreditFinish = true;
					log.info("message not sent beacause of Insufficient clients credits");
				}
				if (isCreditFinish) {
					log.info("message not sent beacause of Insufficient credits");
					rd = request.getRequestDispatcher("sms/SingleSms.jsp");
					int creditRequired = recfix_list.size() - counterValue;
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
					rd = request.getRequestDispatcher("sms/SingleSms.jsp");
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
				log.info(error);
				rd = request.getRequestDispatcher("sms/SingleSms.jsp");
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
	
}// End of class
