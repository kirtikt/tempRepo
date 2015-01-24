package com.ecomm.ultimatesms.messaging.transferMessage;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class TransferMessageToQueue {
	Logger log = Logger.getLogger(TransferMessageToQueue.class);
	private Context initialContext = null;
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Queue queue = null;
	private javax.jms.Session session = null;
	private MessageProducer messageProducer = null;

	public void sendMessagetoJMS(TextMessage1 textMessage) {
		int count = 0;
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jnp.interfaces.NamingContextFactory");
		ht.put(Context.PROVIDER_URL, "69.89.2.245");
		ht.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		System.out.println("In SendSMS to JMS Queue");
		try {
			initialContext = new InitialContext(ht);
			connectionFactory = (ConnectionFactory) initialContext
					.lookup("ConnectionFactory");
			queue = (Queue) initialContext.lookup("/queue/ExpiryQueue");
			connection = (Connection) connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			messageProducer = session.createProducer(queue);
			connection.start();


			/*
			 * Update to database
			 */
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			org.hibernate.Session hiberSession = sessionFactory.openSession();
			org.hibernate.Transaction hiberTransaction = null;
		
			try {
				hiberTransaction = hiberSession.beginTransaction();
				/*
				 * Extract details from text message and update to database.
				 */
				Sms simpleReport = new Sms();
				Long pkey = 0L;//Long.parseLong(request.getParameter("pkey"))
			    Query  query   = hiberSession.createQuery("select max(pkey)from Simplereport ");
			    List list =query.list();
			    Long max_key  = (Long)list.get(0);
			    
				simpleReport.setSource(textMessage.getSender());
				simpleReport.setDestination(textMessage.getReciever());
				simpleReport.setMessage(textMessage.getMessage());
				simpleReport.setStatus("22"); // Message sent to JMS Queue.
//				simpleReport.setSmsc(textMessage.getSmsc());
//				simpleReport.setUserid(textMessage.getUserid());
				simpleReport.setSmsid(textMessage.getMessageID());
				//simpleReport.setTimedate(Long.toString(System.currentTimeMillis()));
				Long pk=(Long)hiberSession.save(simpleReport);
				System.out.println("TransferMessageToQueue Message sent to JMS Queue pk ================> "+ pk);
				simpleReport=null;
				hiberTransaction.commit();
				 hiberSession.flush();

			} catch (HibernateException e) {
				hiberTransaction.rollback();
			} finally {
				hiberSession.close();
				hiberSession = null;
				hiberTransaction = null;
			}

			ObjectMessage message = session.createObjectMessage();
			message.setObject((Serializable) textMessage);
			messageProducer.send((ObjectMessage) message);
			log.info("Message sent ");
			connection.close();
		} catch (Exception ex) {
			log.debug(ex);
		}

	}
}
