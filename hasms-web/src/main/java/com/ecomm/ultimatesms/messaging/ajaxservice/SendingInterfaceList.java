package com.ecomm.ultimatesms.messaging.ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Iterator;
import net.sf.json.JSONObject;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;

/**
 * Servlet implementation class CreditPerClient
 */
public class SendingInterfaceList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendingInterfaceList() {
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
	
		JSONObject object=new JSONObject();
		List<Sendinginterface> interfaceList=new SendingInterfaceManager().getSendingInterface();
		if(interfaceList!=null || interfaceList.size()!=0)
		{
			Iterator<Sendinginterface> it=interfaceList.iterator();
			while(it.hasNext())
			{
			Sendinginterface sr= it.next();
			object.put(sr.getPksendinginterface(),sr.getName());
			}
		
		response.setContentType("application/json");
		response.getWriter().write(object.toString());
	}
}
}