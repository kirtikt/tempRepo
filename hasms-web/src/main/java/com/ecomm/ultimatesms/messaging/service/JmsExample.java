package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hornetq.api.core.management.MessageCounterInfo;
import org.hornetq.api.core.management.ObjectNameBuilder;
import org.hornetq.api.jms.management.JMSQueueControl;
import org.omg.CORBA.Request;

/**
 * Servlet implementation class JmsExample
 */

public class JmsExample extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JmsExample() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.GetJMSAtrribute(request,response);
		
		
	}

	
	public void GetJMSAtrribute(HttpServletRequest request, HttpServletResponse response)
	{
		   ObjectName on;

	       try
	       {
	       on = ObjectNameBuilder.DEFAULT.getJMSQueueObjectName("testMyQueue");
	       String host = "localhost";
	       int port = 9999;  // management-native port
	       String urlString = System.getProperty("jmx.service.url","service:jmx:remoting-jmx://" + host + ":" + port);
	       JMXServiceURL serviceURL = new JMXServiceURL(urlString);
	       Map<String, String> env = new HashMap<String, String>();
	       env.put(InitialContext.INITIAL_CONTEXT_FACTORY, RMIContextFactory.class.getName());
	       System.out.println("serviceURL "+serviceURL.getProtocol());
	       JMXConnector cntor = JMXConnectorFactory.connect(serviceURL,env);
	       System.out.println("cntor "+cntor.getConnectionId());
	       MBeanServerConnection mbsc = cntor.getMBeanServerConnection();
	       JMSQueueControl queueControl = (JMSQueueControl)MBeanServerInvocationHandler.newProxyInstance(mbsc, on, JMSQueueControl.class, false);
	       String counters = queueControl.listMessageCounter();
	       MessageCounterInfo messageCounter = MessageCounterInfo.fromJSON(counters);
	       System.out.println("Number of message_:"+queueControl.getMessageCount());
	       System.out.println("DeliveringCount_:"+queueControl.getDeliveringCount());
	       System.out.println("Messages Added_:"+queueControl.getMessagesAdded());
	       System.out.println("Messages Added_:"+queueControl.getScheduledCount());
	       System.out.println("Counter History _:"+ queueControl.listMessageCounterHistory());
	       System.out.println("Number of Expire Mesage "+queueControl.expireMessages(""));
	       
	       //**************************88 to Remove the message ******************
	       System.out.println("Number of Removed Mesage "+queueControl.removeMessages(""));
	      
	       RequestDispatcher rd = request.getRequestDispatcher("JmsExample.jsp");
	       request.setAttribute("queueControl",queueControl);
	       rd.forward(request, response);
	        
	       
//	       Object param[]=new Object[]{null};
//	       String[] signature={"java.lang.String"};
//	       System.out.println("Number of Removed Mesage "+ mbsc.invoke(on, "removeMessages" , param, signature));

	     /*  QueueViewMBean queue = (QueueViewMBean) MBeanServerInvocationHandler
                   .newProxyInstance(mbsc, on,
                                   QueueViewMBean.class, true);*/

   /**/

   // Purge the queue
   //queue.purge(); 

	         
	       }
	       catch(Exception e){
	    	   e.printStackTrace();
	       }
	    
	   }
		
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
