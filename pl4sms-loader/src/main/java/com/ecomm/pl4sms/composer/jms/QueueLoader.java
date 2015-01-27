package com.ecomm.pl4sms.composer.jms;


import com.ecomm.pl4sms.common.SMSQueueMessageDTO;
import org.apache.log4j.Logger;

import javax.jms.*;
import javax.jms.Queue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.*;


public class QueueLoader {

    private static Logger LOG = Logger.getLogger(QueueLoader.class);

    public static void main (String[] args ) {


    }



    public PL4_JMS_Session getQueueSession() {
        System.out.println( "Get queue session ...");
        Session session = null;
        Queue queue = null;
        try {
            Properties p = new Properties( );
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        //    p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, "http-remoting://41.185.26.92:8080");
            p.put(Context.SECURITY_PRINCIPAL, "admin");
            p.put(Context.SECURITY_CREDENTIALS, "admin");

//            p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SSL_STARTTLS", "true");

            Context ctx = new InitialContext(p);
            // Step 2. Lookup the connection factory
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");

            System.out.println("Looking up the queue ...");
            queue = (Queue) ctx.lookup("java:jms/queue/SenderQueue");

            // Step 4. Create the JMS objects to connect to the server and manage a session
            Connection connection = cf.createConnection("admin", "admin");
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println( "Done ...");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);


        }
        return new PL4_JMS_Session(session,queue);
    }


public void testMessage() {


    Session session = null;
    Queue queue = null;
    try {
        Properties p = new Properties( );
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        //    p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "http-remoting://127.0.0.1:8080");
        p.put(Context.SECURITY_PRINCIPAL, "admin");
        p.put(Context.SECURITY_CREDENTIALS, "admin");

//            p.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_DISALLOWED_MECHANISMS", "JBOSS-LOCAL-USER");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT", "false");
//            p.put("remote.connection.default.connect.options.org.xnio.Options.SSL_STARTTLS", "true");

        Context ctx = new InitialContext(p);
        // Step 2. Lookup the connection factory
        ConnectionFactory cf = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");


    String destinationString = System.getProperty("destination", "java:/jms/queue/Sender3");

    Destination destination = (Destination) ctx.lookup(destinationString);



    JMSContext context = cf.createContext("admin", "admin");

        for (int i = 0; i < 30; i++) {
            context.createProducer().send(destination, "Tester");
        }



} catch (NamingException e) {

    } finally {

    }
}

    public void sendMessage(SMSQueueMessageDTO smsMessage) {

        try {

            PL4_JMS_Session pl4Session = getQueueSession();

            // Step 5. Create a JMS Message Producer to send a message on the queue
            MessageProducer producer = pl4Session.getJmsSession().createProducer(pl4Session.getJmsQueue());

            // Step 6. Create a Text Message and send it using the producer
            ObjectMessage message = pl4Session.getJmsSession().createObjectMessage();
            message.setObject(smsMessage);
            producer.send(message);

        } catch (Exception e) {

            LOG.error(e.getMessage(), e);

            throw new RuntimeException( e.getMessage(), e );
        }
    }


    public void sendBulkMessage(List<SMSQueueMessageDTO> smsMessageList) {
        System.out.println( "Sending bulk message list of size [" + smsMessageList.size() + "]");
        PL4_JMS_Session pl4Session = null;
        try {

            pl4Session = getQueueSession();
            System.out.println("Got session ...");
            MessageProducer producer = pl4Session.getJmsSession().createProducer(pl4Session.getJmsQueue());


            int i = 0;
            System.out.println("Loop start ... [" + new Date() + "]");
            for ( SMSQueueMessageDTO smsMessage : smsMessageList) {
                ObjectMessage message = pl4Session.getJmsSession().createObjectMessage();
                //TextMessage tm = pl4Session.getJmsSession().createTextMessage();
                message.setObject(smsMessage);

                //tm.setText("test");

                producer.send(message);

            }
            System.out.println("Loop finish ... [" + new Date() + "]");

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                pl4Session.getJmsSession().close();
            } catch ( JMSException jmse) {

            }

        }
    }


    public void readFromQueue() {

        try {
            // Step 1. Create an initial context to perform the JNDI lookup.



            Properties p = new Properties( );
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                  "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                   " org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, "jnp://localhost:1099");

            Context ctx = new InitialContext(p);
            // Step 2. Lookup the connection factory
            ConnectionFactory cf = (ConnectionFactory) ctx.lookup("/ConnectionFactory");

            // Step 3. Lookup the JMS queue
            Queue queue = (Queue) ctx.lookup("/queue/ExampleQueue");

            // Step 4. Create the JMS objects to connect to the server and manage a session
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Step 7. Create a JMS Message Consumer to receive message from the queue
            MessageConsumer messageConsumer = session.createConsumer(queue);

            // Step 8. Start the Connection so that the server starts to deliver messages
            connection.start();

            // Step 9. Receive the message
            TextMessage messageReceived = (TextMessage) messageConsumer.receive(5000);
            System.out.println("Received message: " + messageReceived.getText());

            // Finally, we clean up all the JMS resources
            connection.close();

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
