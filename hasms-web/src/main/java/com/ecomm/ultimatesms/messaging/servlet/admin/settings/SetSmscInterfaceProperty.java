package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxyManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxypropertyManager;


import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnosmscproperty;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxy;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxyproperty;


/**
 * Servlet implementation class RegisterUser
 */
public class SetSmscInterfaceProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetSmscInterfaceProperty() {
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
		Logger log = Logger.getLogger(SetSmscProperty.class);
		BasicConfigurator.configure();
		HttpSession session=request.getSession(false);
		
		String reply= request.getParameter("reply"); 
		String alphasourceadd= request.getParameter("alphasourceadd");
		String concatenation= request.getParameter("concatenation");
		String interfacetype= request.getParameter("interfacetype");
		String name= request.getParameter("interfacename");
		String deliveryreceipt= request.getParameter("deliveryreceipt");
		String kannelname= request.getParameter("kannelname");
		
		String smscid= request.getParameter("smscid");
		String speed= request.getParameter("speed");
		
		
		
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		String errmsg="";
		
		log.info("reply "+reply);
		log.info("alphasourceadd "+alphasourceadd);
		log.info("concatenation "+concatenation);
		log.info("interfacetype "+interfacetype);
		log.info("name "+name);
		
		log.info("deliveryreceipt "+deliveryreceipt);
		log.info("kannelname "+kannelname);
		
		
		RequestDispatcher rd=null;
		
		
		if (kannelname != null && !("".equals(kannelname)) && !("null".equals(kannelname))) {
			
			SmscproxypropertyManager sppmanager=new SmscproxypropertyManager();
			Smscproxyproperty spp =new Smscproxyproperty();
			
			if("yes".equalsIgnoreCase(concatenation)){
				concatenationbol=true;
			}
			if("yes".equalsIgnoreCase(alphasourceadd)){
				alphasourceaddbol=true;
			}
			if("yes".equalsIgnoreCase(deliveryreceipt)){
				deliveryreceiptbol=true;
			}
			if("yes".equalsIgnoreCase(reply)){
				replybol=true;
			}
			/*
			 * Add the property for smsc
			 */
			
			long id=Long.parseLong(smscid);
			SmscproxyManager mnom1=new SmscproxyManager();
			 org.hibernate.Transaction tr=null;
			Session sess=null;
			try{
			
				
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	            sess=sessionFactory.openSession();
			
			Smscproxy sp=mnom1.findById(id,sess);
			
			spp.setName(name);
			spp.setConcatination(concatenationbol);
			spp.setDeliveryreciepts(deliveryreceiptbol);
			spp.setAlphanumericadd(alphasourceaddbol);
			spp.setKannelsmscname(kannelname);
			spp.setOfferedinterface(interfacetype);
			spp.setSmscproxy(sp);
			spp.setReplypath(reply);
			// count of sms to send per second.
			spp.setSpeedofsms(speed);
			sppmanager.add(spp);
			
			/*
			 * Entry in sending interface
			 */
			SendingInterfaceManager simanager=new SendingInterfaceManager(); 
			Sendinginterface si=new Sendinginterface();
			si.setName(name);
			si.setKannelname(kannelname);
			si.setInterfacepojoname("Smscproxyproperty");
			si.setLocalsmscost(Double.valueOf("14"));
			si.setLocalreplycost(Double.valueOf("2"));
			// Its Mandatory 
			si.setSmscproxyproperty(spp);
			simanager.add(si);
			
			/*
			 * Add the mno smscost,replycost,and source number
			 */
			
			MnoManager mnom=new MnoManager();
			
			List<Mno> list=mnom.getList();
		    Iterator<Mno> it=list.iterator();
		   tr=  sess.beginTransaction();
		    while(it.hasNext()){
		        Mno mno=(Mno)it.next();
		       Mnosmscproperty msc=  new Mnosmscproperty();
		       msc.setMno(mno);
		         msc.setSmscost(Double.valueOf(request.getParameter(mno.getName().trim())));
		       String rep= "r"+mno.getName().trim();
		       
		       msc.setReplycost(Double.valueOf(request.getParameter(("r"+mno.getName()).trim())));
		       msc.setSourcenumber(request.getParameter(("sa"+mno.getName()).trim()));
		      
		       msc.setSendinginterface(si);
		       
		       sess.save(msc);
		       }
		        tr.commit();
			}finally{
				sess.flush();
				sess.close();
				sess=null;
				
			tr=null;
			}
			errmsg="Successfully Inserted ";
			rd=request
					.getRequestDispatcher("admin/setsmscproperty.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
			errmsg="please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/setsmscproperty.jsp");
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
