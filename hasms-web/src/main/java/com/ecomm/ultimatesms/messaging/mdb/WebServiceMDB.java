/**
 * 
 */
package com.ecomm.ultimatesms.messaging.MDB;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.ws.input.JSONSendSMSInput;
import com.google.gson.Gson;

/**
 * @author webwerks
 *
 */

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/beforeKannelQueue"),
				@ActivationConfigProperty(propertyName = "acknowledgeMode",propertyValue = "Auto-acknowledge") 

		})
public class WebServiceMDB implements MessageListener{

	
	/**
     * Default constructor. 
     */
    public WebServiceMDB() {
        // TODO Auto-generated constructor stub
    }
	
	/**
     * @see MessageListener#onMessage(Message)
     */
    public void onMessage(Message message) {
    	Logger log=LoggerFactory.getLogger(MessageDrivenBean.class);
    	log.info("====================== onMessage ====================");
    	Serializable seObj = null;
    	JSONSendSMSInput tmsg = null;
    	try {
			seObj = ((ObjectMessage) message).getObject();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tmsg = (JSONSendSMSInput) seObj;

		storeSMS(tmsg);
//    	MyMessage tm =  (MyMessage) message;
    	
   	
    }
    
    public void storeSMS(JSONSendSMSInput obj){
    	
    	try{
    		Gson gson = new Gson();
    		String smsJson = gson.toJson(obj);
    		File file = new File("/home/webwerks/Desktop/smsstorage/store.txt");
    		
    		if(!file.exists()){
    			file.createNewFile();
    		}
    		
    		FileWriter fw = new FileWriter(file.getAbsoluteFile());
    		BufferedWriter bw = new BufferedWriter(fw);
    		bw.write(smsJson);
    		
    		bw.close();
    		
    	}catch(Exception e){
    		System.out.println("File not found exception : "+e);
    	}
    }
}
