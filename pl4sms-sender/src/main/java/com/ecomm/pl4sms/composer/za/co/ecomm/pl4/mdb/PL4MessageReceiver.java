package com.ecomm.pl4sms.composer.za.co.ecomm.pl4.mdb;


import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.ecomm.pl4sms.common.SMSQueueMessageDTO;

import com.ecomm.pl4sms.common.kannel.KannelSend_HTTP_URL;
import com.ecomm.pl4sms.common.kannel.KannelSender;
import org.apache.log4j.Logger;


@MessageDriven(name = "PL4MessageReceiver", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue/Sender3"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class PL4MessageReceiver implements MessageListener, MessageDrivenBean{

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(PL4MessageReceiver.class);

    public PL4MessageReceiver() {
        logger.info("Snmp MDB Created :: " + this);
        System.out.println("Snmp MDB Created :: " + this);
    }



    public void onMessage(Message message) {
        try
        {
            System.out.println("Entered in onMessage::: ");
            logger.info("Trap Received In Processor ::: ");

            ObjectMessage objectMessage  =  (ObjectMessage)message;
            SMSQueueMessageDTO received = (SMSQueueMessageDTO)objectMessage.getObject();
            System.out.println("Batch Id [" + received.getBatchId() + "]");
            System.out.println("Message Text [" + received.getMessageText() + "]");

            KannelSend_HTTP_URL httpURL = new KannelSend_HTTP_URL();

            httpURL.setServerName("localhost");
            httpURL.setPortNumber("13013");
            httpURL.setUrlContext("cgi-bin/sendsms");
            httpURL.setFrom("Test");
            httpURL.setUsername("tester");
            httpURL.setPassword("foobar");

            httpURL.setTargetMSDN(received.getMsisdn());
            httpURL.setMessageText(received.getMessageText());
            httpURL.setSmsCentre(received.getSmsCentre());

            KannelSender.submitKannelMessage(httpURL);

            message.acknowledge();

        }
        catch (Exception e)
        {
            logger.error("Error in receiving alarm from queue", e);

        }finally{

        }

    }

    public void ejbRemove() throws EJBException {
        logger.info("QueueListenerMDB is being removed");
    }


    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {


    }




}