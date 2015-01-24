package com.ecomm.ultimatesms.messaging.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class smslist extends HttpServlet {

	public void doGet(HttpServletRequest request,HttpServletResponse res) throws IOException
	{
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			res.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		long clientmanagerId=(Long)request.getSession(false).getAttribute("clientmanagerId");
		
		List<Sms> smsList= new SmsManager().getSmsList("status","11",clientmanagerId);
		/*if(smsList!=null){
			
		Iterator<Sms> it=smsList.iterator();
		System.out.println("proessreques sms report list size:"+smsList.size());
		while(it.hasNext())	{
			Sms sms=(Sms)it.next();
			
			out.print("<tr><td>"+sms.getDestination()+"</td><td>"+sms.getSource()+"</td><td>"+DateFormat.getDateTimeInstance(
		            DateFormat.LONG, DateFormat.LONG).format(sms.getDatetime())+"</td><td><a href='/payless4sms/reports/getDetailSmsIdReport.jsp?smsid="+sms.getSmsid()+"'>"+sms.getSmsid()+"</a></td><td>"+sms.getMessage()+"</td><tr>");
		}	*/
		//}
	
		}
}
