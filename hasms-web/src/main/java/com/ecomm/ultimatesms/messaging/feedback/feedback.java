package com.ecomm.ultimatesms.messaging.feedback;

import java.io.IOException;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.messaging.MDB.FeedbackMessageBean;

public class feedback extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public feedback() {
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

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) {
		Logger log = LoggerFactory.getLogger(feedback.class);
		// DatabaseHelper dbHlper=new DatabaseHelper();
		System.out.println("In Feedback servlet :");

		String status = request.getParameter("status");
		String SMSC = request.getParameter("smsc");
		String timeDate = request.getParameter("timeDate");
		String destination = request.getParameter("destination");
		String deliveryInfo = request.getParameter("delivInfo");
		String source = request.getParameter("source");
		String messageSendingID = request.getParameter("msgid_sending");
		String messageDeliveryID = request.getParameter("msgid_delivery");
		String accountInfo = request.getParameter("accountInfo");
		String charge = request.getParameter("charge");
		String smsID = request.getParameter("smsid");
		String message = request.getParameter("message");

		/*
		 * For load Testing
		 */

		// String status = "16";
		// String SMSC = "silverstreettest";
		// String timeDate = "2012-07-05+08:08:10";
		// String destination = "27718681922";
		// String deliveryInfo = "NACK%2FMALFORMED+SMS";
		// String source = "n%2";
		// String messageSendingID = "test";
		// String messageDeliveryID = "";
		// String accountInfo = "";
		// String charge = "";
		// String smsID = "0a609648-ad1c-4bca-90c5-19ca8ce38bb0";
		// String message="test";

		log.info("Status :" + request.getParameter("status"));
		log.info("Destination :" + request.getParameter("destination"));
		log.info("SMS ID" + request.getParameter("smsid"));
		log.info("Message Sending ID :" + request.getParameter("msgid_sending"));
//		log.info("Message Delivery ID :"
//				+ request.getParameter("msgid_delivery"));

		/*
		 * Code to add the jms sender
		 */

		//String destinationName = "queue/test";
		String destinationName = "queue/Mytest" ;
		Context ic = null;
		QueueConnectionFactory cf = null;
		Connection connection = null;
		MessageProducer publisher = null;
		Session session = null;
		try {

			// Properties props = new Properties();
			// props.setProperty("java.naming.factory.initial",
			// "org.jboss.as.naming.InitialContextFactory");
			// props.setProperty("java.naming.factory.url.pkgs",
			// "org.jboss.naming");
			// props.setProperty("java.naming.provider.url", "localhost");

			// No need to Use the properties As we have the MDB and Application
			// on server.
			ic = new InitialContext();

			//Lookup the connection factory
			//java:/JmsXA which is the pooled connection factory provider
			cf = (QueueConnectionFactory) ic.lookup("java:/JmsXA");

			//Lookup the JMS queue
			//We are sending message to queue as named queue/test 
			Queue queue = (Queue) ic.lookup(destinationName);

			//Create the JMS objects to connect to the server and manage a session
			// AUTO_ACKNOWLEDGE mode follows the policy of delivering the
			// message once-and-only once but this incurs an overhead on the
			// server to maintain this policy .
			connection = cf.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//Create a JMS Message Producer to send a message on the queue
			publisher = session.createProducer(queue);

			connection.start();

			// Created the Bean to send as Object Message
			FeedbackMessageBean mymesage = new FeedbackMessageBean();
			mymesage.setStatus(status);
			mymesage.setSMSC(SMSC);
			mymesage.setDestination(destination);
			mymesage.setTimeDate(timeDate);
			mymesage.setDeliveryInfo(deliveryInfo);
			mymesage.setSource(source);
			mymesage.setMessageDeliveryID(messageDeliveryID);
			mymesage.setMessageSendingID(messageSendingID);
			mymesage.setAccountInfo(accountInfo);
			mymesage.setSmsID(smsID);
			mymesage.setMessage(message);

			ObjectMessage msg = session.createObjectMessage(mymesage);

			publisher.send(msg);
			

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
//			if (ic != null) {
//				try {
//					ic.close();
//				} catch (NamingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (publisher != null) {
//				try {
//					publisher.close();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if (session != null) {
//				try {
//					
//					session.close();
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}

		}
	}
}
