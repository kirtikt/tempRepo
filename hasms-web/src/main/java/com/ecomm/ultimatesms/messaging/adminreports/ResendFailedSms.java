package com.ecomm.ultimatesms.messaging.adminreports;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.LinkedList;
import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.service.SendSmsProcess;


/**
 * Servlet implementation class CreditPerClient
 */
public class ResendFailedSms extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResendFailedSms() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 processRequest(request, response);
	}
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException  {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		
		HttpSession httpSession=request.getSession(false);
		Logger log=LoggerFactory.getLogger(ResendFailedSms.class);
		log.info("in ResendFailedSms ");
		
		String pkHoldingAccount= request.getParameter("pkHoldingAccount");
		String type= request.getParameter("type");
		log.info("pkHoldingAccount:"+pkHoldingAccount);
		log.info("type:"+type);
		
		long pkHoldingAccountLong=Long.parseLong(pkHoldingAccount);
		
		List<Sms> list=null;
		if("Failed before getting to gateway".equalsIgnoreCase(type.trim())){
			
		}
		if("Unconfirmed delivery to mobile network".equalsIgnoreCase(type.trim())){
			list=new SmsManager().getFailedSms(pkHoldingAccountLong,"8");
		}
        if("Failed to be accepted by gateway".equalsIgnoreCase(type.trim())){
		// still no record to fetch on this condition
        	list=new SmsManager().getFailedSms(pkHoldingAccountLong);
		}
		
		List<TextMessage1> listTextMessage=new LinkedList<TextMessage1>();
		if(list!=null){
			if(list.size()!=0){
				for(int i=0;i<list.size();i++){
					Sms sms=list.get(i);
					TextMessage1 textMessage = new TextMessage1();
					
					textMessage.setSmscount(sms.getSmscount());
					textMessage.setSender(sms.getSource().trim());
					textMessage.setReciever(sms.getDestination().trim());
					textMessage.setMessage(sms.getMessage().trim());
				//	textMessage.setFooter(sms.get);
					
					textMessage.setSmsc(sms.getSendinginterface().getKannelname().trim());
					textMessage.setMessageID(sms.getSmsid().trim());
					DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
					Date date = new Date();
					String dd = dateFormat.format(date).toString();
					textMessage.setDatetime(dd);
					textMessage.setSysuserId(sms.getClientmanager().getSysuser().getPkuserid());
					textMessage.setClientmanagerId(sms.getClientmanager().getPkclientmanagerid());
					textMessage.setHoldingaccountId(pkHoldingAccountLong);
					textMessage.setDeliveryinfo(sms.getDeliveryinfo());
					
					listTextMessage.add(textMessage);
				}
				Thread t = new SendSmsProcess(listTextMessage);
				t.start();
				request.setAttribute("msg", "Message sent Successfully !!!");
			}
			else{
				request.setAttribute("msg", "Message not sent !!!");
			}
			
		}
		else{
			request.setAttribute("msg", "Message not sent !!!");
		}
		
		
		RequestDispatcher rd = request
				.getRequestDispatcher("adminreports/recordfailedsms.jsp");
		
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
