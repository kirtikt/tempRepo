package com.ecomm.ultimatesms.designpattern;

import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import com.ecomm.mdb.ejb.kannel.CreateUrl;
import com.ecomm.ultimatesms.messaging.billing.Billing;
import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.Helper;

public class BulkSmsManager {
	org.slf4j.Logger log = LoggerFactory.getLogger(BulkSmsManager.class);
	Properties props;
	
	public BulkSmsManager(){
		try {
			props=Helper.getProps();
		//	System.out.println("**************************//property name::"+props.getProperty("name"));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}
	
	public synchronized void addBulkToKannel(List<TextMessage1> textMessages) {
		Long pk = 0L;
		List<String> receiversList=null;
		try {
			Iterator<TextMessage1> it=textMessages.iterator();
			 receiversList =new LinkedList<String>();
			while(it.hasNext()){
				TextMessage1 txtmsg=it.next();
				receiversList.add(txtmsg.getReciever());
			}
			log.info(" I aM AT KANNEL ");
		    log.info("====================== onMessage ====================");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			
			boolean success = true;
			try {
				String ip=props.getProperty("ip");
				String kannelport=props.getProperty("kannelport");
				int intPort=Integer.parseInt(kannelport);
				
				(new Socket(ip.trim(), intPort)).close();//ip=68.169.52.16
			} catch (UnknownHostException e) {
				// unknown host
				success = false;
				log.info("Kannel DOWN");
				
			} catch (IOException e) {
				// io exception, service probably not running
				success = false;
			}
			if (success) {
				log.info("Kannel is UP");
				sendBulkMessagetoKannel(textMessages, "31",System.currentTimeMillis(),receiversList);
			} else {
				// Message not processed bcoz kannel is down
				try {
					for(int i=0;i<textMessages.size();i++){
						TextMessage1 textMessage=textMessages.get(i);
						ClientmanagersManager clientmanager = new ClientmanagersManager();
					Clientmanager client = clientmanager.findById(textMessage
							.getClientmanagerId());
					
					HoldingaccountManager holdmanager = new HoldingaccountManager();
					Holdingaccount holdccount = holdmanager
							.findById(textMessage.getHoldingaccountId());
					holdmanager = null;
					Sms sms = new Sms();
					sms.setClientmanager(client);
					sms.setHoldingaccount(holdccount);
					sms.setSmsid(textMessage.getMessageID().trim());
					sms.setMessage(textMessage.getMessage().trim());
					sms.setSource(textMessage.getSender().trim());
					sms.setDestination(textMessage.getReciever().trim());
					sms.setStatus("88"); // Message not send bcoz kannel is not up
					sms.setDatetime(new Date());
					int smscount = textMessage.getSmscount();
					sms.setSmscount(smscount);
					
					// sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
					double creditAfter = holdccount.getCreditsleft();
					sms.setCreditafter(creditAfter);
					sms.setSendinginterface(client.getSendinginterface());
					sms.setDeliveryinfo(textMessage.getDeliveryinfo());
					// sms.setUm(Integer
					// .valueOf(client.getSysuser().getPkuserid()));
					sms.setDm(new Date());
					SmsManager smsmanager = new SmsManager();
					smsmanager.add(sms);
					log.info("//************ messase not processed becoz kannel is down. destination is {} ***************// ",textMessage.getReciever());
					
					/*
					 * Return the deposited credits
					 */
					Billing bil=new Billing();
					bil.returnDepositedcredit(
							textMessage
							.getClientmanagerId(),  textMessage.getSmscount(),
							textMessage.getHoldingaccountId());
					
					sms = null;
					clientmanager = null;
					smsmanager = null;
					holdmanager=null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				log.info(" Message not processed bcoz kannel is down");
			}

		} catch (Exception i) {
			i.printStackTrace();
		}
   }
	
	public synchronized void sendBulkMessagetoKannel(List<TextMessage1> textMessages,
			String dlrmask, long ctime, List<String> receiver) {

		CreateUrl createUrl = new CreateUrl();
		String response = "";
		int splits = 0;
		try {
			String tempmsg = textMessages.get(0).getMessage();
			  String feedbackurl=props.getProperty("feedbackurl");
			//http://68.169.52.16:8080/payless4sms/feedback
			String dlr_to_encode = feedbackurl.trim()+"?status=%d&smsc=%i&timeDate=%t&destination=%p&delivInfo=%a&source=%P&msgid_sending=%F&msgid_delivery=%k&charge=%B&accountInfo=%o&smsid="
					+ textMessages.get(0).getMessageID().trim()
					+ "&message="
					+ URLEncoder.encode(tempmsg, "UTF-8");
			//log.info("Dlr URL : " + dlr_to_encode);

			String dlr_encoded = URLEncoder.encode(dlr_to_encode, "UTF-8");
			//log.info("Encoded DLR URL :" + dlr_encoded);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			//log.info("Timestamp SMS sent to Kannel"+ df.format(System.currentTimeMillis()));

			String ipadd=props.getProperty("ip");
			String port=props.getProperty("kannelport");
//			response = createUrl.sendBulkSMS(ipadd.trim(), port.trim(), "tester",
//					"foobar", textMessages.get(0).getSender().trim(),
//					receiver, textMessages.get(0).getMessage(),
//					textMessages.get(0).getSmsc().trim(), dlrmask, dlr_encoded);
//			
			log.info("Response:" + response);
			if ("Sent.".equals(response)) {
				splits = 1;
				log.info("Message Sent Successfully");
			
				/*
				 * Add the Sms object to database
				 */
				SessionFactory sessionFactory = null;
		        Session session=null;
		        Transaction tr=null;
				try {
					 sessionFactory = HibernateUtil.getSessionFactory();
			         session=sessionFactory.openSession();
			         tr= session.beginTransaction();
					for(int i=0;i<textMessages.size();i++){
						
					TextMessage1 textMessage=textMessages.get(i);
					
//					ClientmanagersManager clientmanager = new ClientmanagersManager();
//					Clientmanager client = clientmanager.findById(textMessage
//							.getClientmanagerId());
//					clientmanager = null;
					Clientmanager client=(Clientmanager)session.load(Clientmanager.class, textMessage.getClientmanagerId());
					
//					HoldingaccountManager holdmanager = new HoldingaccountManager();
//					Holdingaccount holdccount = holdmanager
//							.findById(textMessage.getHoldingaccountId());
//					holdmanager = null;
					Holdingaccount holdccount=(Holdingaccount)session.load(Holdingaccount.class, textMessage.getHoldingaccountId());
					
					
					Sms sms = new Sms();
					sms.setClientmanager(client);
					sms.setHoldingaccount(holdccount);
					sms.setSmsid(textMessage.getMessageID());
					sms.setMessage(textMessage.getMessage());
					sms.setSource(textMessage.getSender());
					sms.setDestination(textMessage.getReciever());
					sms.setStatus("11"); //Message processed.
					sms.setDatetime(new Date());
					int smscount = textMessage.getSmscount();
					sms.setSmscount(smscount);
					// sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
				//	double creditAfter = holdccount.getCreditsleft();
				//	sms.setCreditafter(creditAfter);
					sms.setSendinginterface(client.getSendinginterface());
					sms.setDeliveryinfo(textMessage.getDeliveryinfo());
					// sms.setUm(Integer
					// .valueOf(client.getSysuser().getPkuserid()));
					sms.setDm(new Date());
					//SmsManager smsmanager = new SmsManager();
					session.save(sms);
					 if ( i % 20 == 0 ) { //20, same as the JDBC batch size
					        //flush a batch of inserts and release memory:
					        session.flush();
					        session.clear();
					        }
					log.info("//************ processed the sms for {} ***************// ",textMessage.getReciever());
					
					sms = null;
				//	smsmanager = null;
					
					}
					tr.commit();
						
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally{
					
					session.close();
	                session = null;
	                tr=null;
				}

				// * Message sent to kannel subtracted from user credits
			} else if (response != null) {
				/*
				 * If sms not processed Add sms object into database with status
				 * 99
				 */
				try {
					for(int i=0;i<textMessages.size();i++){
						TextMessage1 textMessage=textMessages.get(i);
						ClientmanagersManager clientmanager = new ClientmanagersManager();
					Clientmanager client = clientmanager.findById(textMessage
							.getClientmanagerId());
					clientmanager = null;
					HoldingaccountManager holdmanager = new HoldingaccountManager();
					Holdingaccount holdccount = holdmanager.findById(textMessage.getHoldingaccountId());
					holdmanager = null;
					Sms sms = new Sms();
					sms.setClientmanager(client);
					sms.setHoldingaccount(holdccount);
					sms.setSmsid(textMessage.getMessageID());
					sms.setMessage(textMessage.getMessage());
					sms.setSource(textMessage.getSender());
					sms.setDestination(textMessage.getReciever());
					sms.setStatus("99"); // Message not send we not get succeed from kannel
					sms.setDatetime(new Date());
					int smscount = textMessage.getSmscount();
					sms.setSmscount(smscount);
					// sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
					double creditAfter = holdccount.getCreditsleft();
					sms.setCreditafter(creditAfter);
					sms.setSendinginterface(client.getSendinginterface());
					sms.setDeliveryinfo(textMessage.getDeliveryinfo());
					
					sms.setDm(new Date());
					SmsManager smsmanager = new SmsManager();
					smsmanager.add(sms);
					log.info("//************ Failed the sms for {} ***************// ",textMessage.getReciever());
					
					sms = null;
					smsmanager = null;
					clientmanager=null;
					holdmanager=null;
					holdccount=null;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (response.contains("Message splits:")) {
					splits = Integer.parseInt(Character.toString((response
							.charAt(response.length() - 1))));
					log.info("Message Sent, split into " + splits + " parts :"
							+ response);
				}
			} else {
				log.info("Message sending failed :" + response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
