package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import java.util.HashSet;

import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;


import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.StartnumberManager;


import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Startnumber;


/**
 * Servlet implementation class RegisterUser
 */
public class UpdateMno extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateMno() {
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
		org.slf4j.Logger log = LoggerFactory.getLogger(UpdateMno.class);
		
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
		 
		
		String mnoid= request.getParameter("mnoid");
		
		long longmnoid=Long.parseLong(mnoid);
		
		
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		String errmsg="";
		
		
		
		RequestDispatcher rd=null;
		
		
		if ( name != null 
				 &&!("null".equals(name))) {
			
			MnoManager mm=new MnoManager();
			StartnumberManager snm=new StartnumberManager();
			
		 
		   Session sess=null;
		   org.hibernate.Transaction tr=null;
		try {
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            sess=sessionFactory.openSession();
            
		    tr=sess.beginTransaction();
			
		    Mno mno=mm.findById(longmnoid,sess);
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
			
			mno.getStartnumbers().clear();
			
			String[] startnoarr=prefixes.split(",");
			Set<Startnumber> set=new HashSet<Startnumber>();
			for(String stno:startnoarr){
				Startnumber sn=new Startnumber();
				sn.setNumber(stno);
				sn.setMno(mno);
				sess.save(sn);
				set.add(sn);
			}
			mno.setStartnumbers(set);
			
			sess.update(mno);
			
        tr.commit();
       
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
				sess.flush();
				sess.close();
				sess=null;
				
			tr=null;
		}
			errmsg="Successfully updated ";
			
			rd=request
					.getRequestDispatcher("admin/mno.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
		
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/mno.jsp");
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
