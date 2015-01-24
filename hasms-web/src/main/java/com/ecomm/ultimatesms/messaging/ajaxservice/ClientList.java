package com.ecomm.ultimatesms.messaging.ajaxservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Iterator;
import net.sf.json.JSONObject;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;

/**
 * Servlet implementation class CreditPerClient
 */
public class ClientList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ClientList() {
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
		ClientmanagersManager manager = new ClientmanagersManager();
		Long mid = null;
		if (request.getAttribute("managerid") != null) {
			mid = (Long) request.getAttribute("managerid");
		}
		System.out.println("managerid:" + mid);
		List<Clientmanager> cltList = manager.getList();
		Iterator<Clientmanager> it1 = cltList.iterator();
		JSONObject object=new JSONObject();
		while (it1.hasNext()) {
			Clientmanager sr = (Clientmanager) it1.next();
			object.put(sr.getPkclientmanagerid(),sr.getSysuser().getUserid());
		}
		response.setContentType("application/json");
		response.getWriter().write(object.toString());
	}
}