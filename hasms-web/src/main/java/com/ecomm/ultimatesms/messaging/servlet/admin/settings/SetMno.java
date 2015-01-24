package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnodirectpropertyManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.StartnumberManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Startnumber;

/**
 * Servlet implementation class RegisterUser
 */
public class SetMno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetMno() {
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
		org.slf4j.Logger log = LoggerFactory.getLogger(SetMno.class);
		
		HttpSession session=request.getSession(false);
		 
		
		String prefixes= request.getParameter("prefixes");
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
		
		long clientmanagerId=(Long)session.getAttribute("clientmanagerId");
		
		
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		String errmsg="";
		
		
		RequestDispatcher rd=null;
		
		
		if ( name != null 
				 &&!("null".equals(name))) {
			
			MnoManager mm=new MnoManager();
			MnodirectpropertyManager mdmanager=new MnodirectpropertyManager();
			StartnumberManager snm=new StartnumberManager();
			
		
			Mno mno=new Mno();
			
			mno.setName(name);
			
			 mno.setAemailid(aemailid);
			 mno.setAlandno(alandno);
			 mno.setAmobno(amobno);
			 mno.setAddress(aphyadd);
			 
			 mno.setBemailid(bemailid);
			 mno.setBlandno(blandno);
			 mno.setBmobno(bmobno);
			 
			 mno.setTfemailid(tfemailid);
			 mno.setTflandno(tflandno);
			 mno.setTfmobno(tfmobno);
			 
			 mno.setTsemailid(tsemailid);
			 mno.setTslandno(tslandno);
			 mno.setTsmobno(tsmobno);
			 
			mm.add(mno);
			
			
			String[] startnoarr=prefixes.split(",");

		
			for(String stno:startnoarr){
				Startnumber sn=new Startnumber();
				sn.setNumber(stno);
				sn.setMno(mno);
				snm.add(sn);
			}

			
			errmsg="Successfully Inserted ";
				rd=request
					.getRequestDispatcher("admin/setmno.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
			errmsg="please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/setmno.jsp");
			request.setAttribute("errorMessage", errmsg);
		}
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			
		}
	}
}
