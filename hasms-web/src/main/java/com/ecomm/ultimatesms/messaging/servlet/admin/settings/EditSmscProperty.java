package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;




import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxyManager;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxy;


/**
 * Servlet implementation class RegisterUser
 */
public class EditSmscProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditSmscProperty() {
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
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		Logger log = Logger.getLogger(EditSmscProperty.class);
		BasicConfigurator.configure();
		HttpSession session=request.getSession(false);
		 
		String name= request.getParameter("name");
		
		
		String blandno= request.getParameter("blandno");
		String bmobno= request.getParameter("bmobno");
		String bemailid= request.getParameter("bemailid");  
		
		String alandno= request.getParameter("alandno");
		String amobno= request.getParameter("amobno");
		String aemailid= request.getParameter("aemailid");
		String aphyadd= request.getParameter("aphyadd");
		
		String tflandno= request.getParameter("tflandno");
		String tfmobno= request.getParameter("tfmobno");
		String tfemailid= request.getParameter("tfemailid");
		
		String tslandno= request.getParameter("tslandno");
		String tsmobno= request.getParameter("tsmobno");
		String tsemailid= request.getParameter("tsemailid");
		String smscid=request.getParameter("smscid");
		
		
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		String errmsg="";
		
		//log.info("name "+name);
		RequestDispatcher rd=null;
		
			//log.info("Adding user to database.");
			
			SmscproxyManager sppmanager=new SmscproxyManager();
			long smscidlong=Long.parseLong(smscid);
			
			
			
			Smscproxy spp = sppmanager.findById(smscidlong);
		
			
			
			spp.setAemailid(aemailid);
			spp.setAlandno(alandno);
			spp.setAmobno(amobno);
			spp.setAphyadd(aphyadd);
		    spp.setName(name);
			
			spp.setBemailid(bemailid);
			spp.setBlandno(blandno);
			spp.setBmobno(bmobno);
			 
			spp.setTfemailid(tfemailid);
			spp.setTflandno(tflandno);
			spp.setTfmobno(tfmobno);
			 
			spp.setTsemailid(tsemailid);
			spp.setTslandno(tslandno);
			spp.setTsmobno(tsmobno);
			
			sppmanager.update(spp);
			
			
			
			errmsg="Successfully Inserted ";
			log.info("Successfully Inserted ");
			rd=request
					.getRequestDispatcher("admin/smscproxy.jsp");
			request.setAttribute("errorMessage", errmsg);
		
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			
		}
	}
}
