package com.ecomm.ultimatesms.messaging.loadtesting;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.BlacklistManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Blacklist;

/**
 * Servlet implementation class LoadTest
 */

public class LoadTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest( request,
				 response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest( request,
				 response);
		
	}
	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		System.out.println("in load test servlet");
		
		BlacklistManager blm=new BlacklistManager();
		Blacklist bl=new Blacklist();
		bl.setNumber("12345678");
		blm.add(bl);
		
		System.out.println(" Finished load test servlet");
		} 

}
