package com.ecomm.ultimatesms.messaging.MDB;

import java.io.Serializable;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
//import org.jboss.ejb3.annotation.Pool;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;

/**
 * Message-Driven Bean implementation class for: MessageBean
 *
 */
@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/test"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode",propertyValue = "Auto-acknowledge") 

		})


public class MessageDrivenBean implements MessageListener {

	
    /**
     * Default constructor. 
     */
    public MessageDrivenBean() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	Logger log=LoggerFactory.getLogger(MessageDrivenBean.class);
    	log.info("====================== onMessage ====================");
    	Serializable seObj = null;
		FeedbackMessageBean tmsg = null;
    	try {
			seObj = ((ObjectMessage) message).getObject();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tmsg = (FeedbackMessageBean) seObj;
//    	MyMessage tm =  (MyMessage) message;
    	try {
    		String smsID = tmsg.getSmsID();
			String status = tmsg.getStatus();
			String messageSendingID = tmsg.getMessageSendingID();
			String source = tmsg.getSource();
			String destination = tmsg.getDestination();
			String textMessage = tmsg.getMessage();
			String timedate = tmsg.getTimeDate();
			String accInfo = tmsg.getAccountInfo();
			String deliveryInfo = tmsg.getMessageDeliveryID();
			String messageSendingID_param = "";
			//log.info("***********-messageSendingID-*********** :: "+messageSendingID);
			if (messageSendingID != " " && messageSendingID != null) {
				messageSendingID_param = "yes";
			} else {
				messageSendingID = "n/a";
			}
			/*
			 * Feedback of kannel to database
			 */

			Session session = null;
			Transaction tr = null;

			try {

				SessionFactory sessionFactory = HibernateUtil
						.getSessionFactory();
				session = sessionFactory.openSession();
				 tr = session.beginTransaction();
				// String SQL_QUERY =
				// "select savefeedback('0a609648-ad1c-4bca-90c5-19ca8ce38bb0','16','deliveryInfo','12/12/12','msgdeli','n/a','27711681922')";
				 
				 /*
					 smsid_param =smsID
					 status_param =status
					 deliveryinfo_param =deliveryInfo
					 timedate_param =timedate
					 accountinfo_param =accInfo
					 msgsendingid_param =messageSendingID_param
					 destination_param=destination */

				 
				 // Saves sms via stored procedure  savefeedback
				String SQL_QUERY = "select savefeedback('" + smsID + "','"
						+ status + "','" + deliveryInfo + "','" + timedate
						+ "','" + accInfo + "','" + messageSendingID_param
						+ "','" + destination + "')";
				Query query = session.createSQLQuery(SQL_QUERY);
				List list = query.list();
				tr.commit();
				log.info("#######-Done-#####"+list);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.flush();
				session.close();
				tr=null;
				session = null;
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

   	
    }
    
    
}