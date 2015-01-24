package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.messaging.billing.Billing;
import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.Helper;
import com.ecomm.ultimatesms.messaging.utils.MessageParamValidator;

/**
 * Servlet implementation class SendMultipleMessage
 */
public class SendMultipleMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendMultipleMessage() {
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

	void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Properties props = null;
		try {
			props = Helper.getProps();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Logger log = LoggerFactory.getLogger(SendMultipleMessage.class);
		
		List<TextMessage1> textMessageList = new LinkedList<TextMessage1>();
		PrintWriter out = null;
		boolean isCreditFinish = false;
		int counterValue = 0;
		RequestDispatcher rd = null;

		try {

			out = response.getWriter();
		} catch (IOException e) {

			e.printStackTrace();
		}

		String uploaddir = "/temp";
		try {

			uploaddir = props.getProperty("uploaddirectory");
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.setProperty("java.io.tmpdir", uploaddir);
		
		// List<String> numberList = new ArrayList<String>();
		// List<String[]> tokenList = null;
		Billing billing = new Billing();
		log.info("================= In Multiple SMS Sender Servlet ===================");

		// * Start of Reading All the parameters from request
		String sender = "Default";// = request.getParameter("sender");
		String message = " ";// = request.getParameter("message");
		String footer = " ";// = request.getParameter("footer");
		String datetime = " ";// = request.getParameter("datetime");
		String replies = " ";// = request.getParameter("replies");
		String smsc = " ";
		String deliveryinfo = null;
		HttpSession httpSession = request.getSession(false);
		MessageParamValidator smpv = new MessageParamValidator();
		/*
		 * 
		 */
		try {
			if (httpSession.getAttribute("sender") != null) {
				sender = (String) httpSession.getAttribute("sender");
			}
			log.info("Sender  ===>" + sender);

			message = (String) httpSession.getAttribute("message");
			log.info("Message  ===>" + message);

			replies = (String) httpSession.getAttribute("replies");
			log.info("Replies  ===>" + replies);

			footer = (String) httpSession.getAttribute("footer");
			log.info("footer  ===>" + footer);

			datetime = (String) httpSession.getAttribute("datetime");
			if (datetime.equals("")) {
				datetime = "now";
			}
			log.info("Date Time  ===>" + datetime);

		} catch (Exception fue) {
			out.print(fue.getStackTrace());
		}

		if (httpSession != null) {
			// userid = (String) httpSession.getAttribute("userid");
			String userid = (String) httpSession.getAttribute("username");

			long clientmanagerId = (Long) httpSession
					.getAttribute("clientmanagerId");

			long sysuserId = (Long) httpSession.getAttribute("sysuserId");
			message = message + " " + footer;
			List<String> recfix_list = (List<String>) httpSession
					.getAttribute("recfixlist");
			String error = smpv.validateParams(recfix_list, sender, message,
					footer, datetime, replies);
			message = smpv.check(message);

			if (error.equals("")) {
				int messagecount = billing.getSmsCount(message);

				int setSender = 0;
				if ("1".equals(replies)
						&& ("".equals(sender) || sender == null)) {
					setSender = 1;
				}

				if (billing.checkClientCredit(clientmanagerId, messagecount,
						recfix_list.size())) {
					if (billing.checkAdminCredit(clientmanagerId, messagecount,
							recfix_list.size())) {
						/*
						 * Take deposit from client and added to holding account
						 */
						Holdingaccount ha = billing.depositCredit(
								clientmanagerId, messagecount,
								recfix_list.size());
						if (ha != null) {
							SessionFactory sessionFactory = null;
							Session session = null;
							Transaction tr = null;
							try {
								sessionFactory = HibernateUtil
										.getSessionFactory();
								session = sessionFactory.openSession();
								tr = session.beginTransaction();
								for (int counter = 0; counter < recfix_list
										.size(); counter++) {
									/*
									 * Send message one by one
									 */
									// String reciever_fixed=null;
									UUID uuid = UUID.randomUUID();

									// reciever_fixed =
									// ph.fixNumber(recfix_list.get(counter));
									// if (reciever_fixed != null)
									// {if(ha==null)

									TextMessage1 textMessage = new TextMessage1();
									if (setSender == 1) {

										if ("".equals(sender) || sender == null
												|| "null".equals(sender))

										{
											sender = "Default";

										}
									}

								
									Clientmanager client = (Clientmanager) session
											.load(Clientmanager.class,
													clientmanagerId);
									
									textMessage.setSmscount(messagecount);
									textMessage.setSender(sender.trim());
									textMessage.setReciever(recfix_list
											.get(counter));
									textMessage.setMessage(message.trim());
									textMessage.setFooter(footer);
									textMessage.setFutureDate(datetime);
									// textMessage.setSmsc(smscproxy.getName());

									if (client != null) {
										textMessage.setSmsc(client
												.getSendinginterface()
												.getKannelname());

									}
									textMessage.setMessageID(uuid.toString()
											.trim());
									DateFormat dateFormat = new SimpleDateFormat(
											"MM/dd/yyyy hh:mm:ss");
									Date date = new Date();
									String dd = dateFormat.format(date)
											.toString();
									textMessage.setDatetime(dd);
									textMessage.setSysuserId(sysuserId);
									textMessage
											.setClientmanagerId(clientmanagerId);
								
									textMessage.setHoldingaccountId(ha
												.getPkeyholdingaccountid());
									
									textMessage.setDeliveryinfo(deliveryinfo);

								
									// Explicitly set by me -----> Status
									Sms sms = new Sms();
									sms.setSource(sender.trim());
									sms.setClientmanager(client);
									// sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
									Double creditAfter = 0d;
									if (ha != null) {
										creditAfter = (Double) ha
												.getCreditsleft();
									}
									sms.setCreditafter(creditAfter);
									sms.setDatetime(new Date());
									sms.setDestination(recfix_list.get(counter));
									sms.setMessage(message.trim());
									sms.setStatus("00"); // Message delivered to
															// servlet
									// log.info(" client.getSendinginterface().getInterfacename()"+client.getSendinginterface().getInterfacename());
									if ("now".equalsIgnoreCase(datetime)) {
										sms.setFuturestatus(false);
									} else {
										sms.setFuturestatus(true);
									}

									sms.setSendinginterface(client
											.getSendinginterface());
									sms.setHoldingaccount(ha);
									sms.setSmscount(messagecount);
									sms.setSmsid(uuid.toString().trim());
									sms.setUc((int) sysuserId);
									sms.setDc(new Date());
									/* please see for the delivery info */
									sms.setDeliveryinfo(deliveryinfo);
									// SmsManager smsmanager = new SmsManager();
									// smsmanager.add(sms);
									session.save(sms);
									// smsmanager = null;
									sms = null;
									client = null;
									log.info(
											"//************ Loaded the sms for {} ***************// ",
											recfix_list.get(counter));

									textMessageList.add(textMessage);

									if (counter % 20 == 0) { // 20, same as the
																// JDBC
										// batch size
										// flush a batch of inserts and release
										// memory:
										session.flush();
										session.clear();
									}
									uuid = null;
								}// End of for loop of receivers
								tr.commit();
							} catch (Exception e) {
								e.printStackTrace();

							} finally {
								session.close();
								session = null;
								tr = null;
							}
							/*
							 * pass list to thread thread will send the sms to
							 * kannel
							 */
							if ("now".equalsIgnoreCase(datetime)) {
								Thread t = new SendSmsProcess(textMessageList);
								t.start();
							} else {
								Thread st = new StoredFutureSms(textMessageList);
								st.start();
							}

							

						}// If will get holdingAccount instace null.
						else {
							isCreditFinish = true;
							log.info("message not sent beacause of Insufficient clients credits");
						}
					}

					else {
						isCreditFinish = true;
						log.info("message not sent beacause of Insufficient credits in admins wallet");

					}
				}

				else {

					isCreditFinish = true;
					// counterValue = counter;
					log.info("message not sent beacause of Insufficient clients credits");
				}

				if (isCreditFinish) {
					log.info("message not sent beacause of Insufficient credits");
					rd = request.getRequestDispatcher("sms/MultipleSMS.jsp");
					// int creditRequired = recfix_list.size() - counterValue;
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
					log.info("Message sent Successfully !!!");
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

		}// End of if(httpSession)

	}// End of proessrequest method

}
