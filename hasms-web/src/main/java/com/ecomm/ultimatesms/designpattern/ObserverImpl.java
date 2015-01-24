package com.ecomm.ultimatesms.designpattern;

import java.io.IOException;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.ecomm.mdb.ejb.kannel.CreateUrl;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.Helper;

public class ObserverImpl implements Observer {
	TextMessage1 textMessage;

	org.slf4j.Logger log = LoggerFactory.getLogger(ObserverImpl.class);

	
	Properties props;
	public ObserverImpl(){
		try {
			props=Helper.getProps();
		//	System.out.println("**************************//property name::"+props.getProperty("name"));
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}	
	
	public ObserverImpl(TextMessage1 textMessage) {
		this();
		this.textMessage = textMessage;
	}

	public TextMessage1 getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(TextMessage1 textMessage) {
		this.textMessage = textMessage;
	}
	
	/**
	 * This method will check the kannel is UP or DOWN.
	 * If kannel is UP then message send to kannel.
	 * If Kannel id DOWN Messages are not sent to kannel and credits are refunded to client.
	 */

	public synchronized void addToKannel() {
		Long pk = 0L;
		try {
			log.info(" I aM AT KANNEL ");
		  
			log.info("Receiver : {} ",  textMessage.getReciever());
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
				sendMessagetoKannel(textMessage, "31",
						System.currentTimeMillis());
			} else {
				// Message not processed bcoz kannel is down
				try {
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
//					Billing bil=new Billing();
//					bil.returnDepositedcredit(
//							textMessage
//							.getClientmanagerId(),  textMessage.getSmscount(),
//							textMessage.getHoldingaccountId());
//					
					sms = null;
					clientmanager = null;
					smsmanager = null;
					holdmanager=null;

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception i) {
			i.printStackTrace();
		}
   }
	
	/**
	 * This method will send the sms to kannel.
	 * @param textMessage this is reference of TestMessage1 , it will contain the detailed info for sms
	 * @param dlrmask
	 * @param ctime
	 */
	// * sendMessagetoKannel( tmsg.getSender(), tmsg.getReciever(),
	// * tmsg.getText(), tmsg.getSmsc(), "31", randomUUIDString, pkey,
	// * System.currentTimeMillis(), tmsg.getCredit() );

	public synchronized void sendMessagetoKannel(TextMessage1 textMessage,
			String dlrmask, long ctime) {

		CreateUrl createUrl = new CreateUrl();
		String response = null;
		int splits = 0;
		try {
			
			String tempmsg = textMessage.getMessage();
			
//			tempmsg= tempmsg.replace("_", props.getProperty("_"));
//			tempmsg= tempmsg.replace("@", props.getProperty("@")); 
//			tempmsg= tempmsg.replace("$", props.getProperty("$")); 
			
			String feedbackurl=props.getProperty("feedbackurl");
			//http://68.169.52.16:8080/payless4sms/feedback
			
			String dlr_to_encode = feedbackurl.trim()+"?status=%d&smsc=%i&timeDate=%t&destination=%p&delivInfo=%a&source=%P&msgid_sending=%F&msgid_delivery=%k&charge=%B&accountInfo=%o&smsid="
					+ textMessage.getMessageID().trim()+ "&message="+ URLEncoder.encode(tempmsg, "UTF-8");
			//log.info("Dlr URL : " + dlr_to_encode);

//			String dlr_encoded = URLEncoder.encode(dlr_to_encode, "UTF-8");
			//log.info("Encoded DLR URL :" + dlr_encoded);

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
			//log.info("Timestamp SMS sent to Kannel"+ df.format(System.currentTimeMillis()));

			String ipadd=props.getProperty("ip");
			String port=props.getProperty("kannelport");
			
			response = createUrl.sendSMS(ipadd.trim(), port.trim(), "tester",
					"foobar", textMessage.getSender().trim(),
					textMessage.getReciever().trim(), textMessage.getMessage(),
					textMessage.getSmsc().trim(), dlrmask, dlr_to_encode);
		
			log.info("Response:" + response);
			if ("Sent.".equals(response)) {
				splits = 1;
				log.info("Message Sent Successfully");
			
				/*
				 * Add the Sms object to database
				 */
				try {
					ClientmanagersManager clientmanager = new ClientmanagersManager();
					Clientmanager client = clientmanager.findById(textMessage
							.getClientmanagerId());
					clientmanager = null;
					HoldingaccountManager holdmanager = new HoldingaccountManager();
					Holdingaccount holdccount = holdmanager
							.findById(textMessage.getHoldingaccountId());
					holdmanager = null;
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
					double creditAfter = holdccount.getCreditsleft();
					sms.setCreditafter(creditAfter);
					sms.setSendinginterface(client.getSendinginterface());
					sms.setDeliveryinfo(textMessage.getDeliveryinfo());
					// sms.setUm(Integer
					// .valueOf(client.getSysuser().getPkuserid()));
					sms.setDm(new Date());
					SmsManager smsmanager = new SmsManager();
					smsmanager.add(sms);
					log.info("//************ processed the sms for {} ***************// ",textMessage.getReciever());
					
					sms = null;
					smsmanager = null;
					holdmanager=null;
					clientmanager=null;
					holdccount=null;
						
				} catch (Exception e) {
					e.printStackTrace();
				}

				// * Message sent to kannel subtracted from user credits

				log.info("userid :" + textMessage.getClientmanagerId());
			} else if (response != null) {
				/*
				 * If sms not processed Add sms object into database with status
				 * 99
				 */
				try {
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
