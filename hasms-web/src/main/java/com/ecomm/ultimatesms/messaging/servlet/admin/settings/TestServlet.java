package com.ecomm.ultimatesms.messaging.admin.settings;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class RegisterUser
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TestServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		System.out.println("in test servlet");
		System.out.println("in test servlet"+request.getRealPath("/"));
		
		System.out.println("result for 1::"+System.getProperty("1"));
		System.out.println("result for a11::"+System.getProperty("a11"));
		System.out.println("result for 1::"+System.getProperty("1"));
		System.out.println("result for URLLIST::"+System.getProperty("URLLIST"));
		
//		try 
//		{ 
//		Process p=Runtime.getRuntime().exec("service kannel status"); 
//		p.waitFor(); 
//		BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
//		String line=reader.readLine(); 
//		System.out.println("output::"+line); 
//		while(line!=null) 
//		{ 
//		System.out.println("output::"+line); 
//		line=reader.readLine(); 
//		} 
//
//		} 
//		catch(IOException e1) {
//			e1.printStackTrace();
//		} 
//		catch(InterruptedException e2) {
//			e2.printStackTrace();
//		} 
//
//		System.out.println("Done"); 
		} 
	}

