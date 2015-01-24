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

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.StartnumberManager;



import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnodirectproperty;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;


/**
 * Servlet implementation class RegisterUser
 */
public class SetMnoProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetMnoProperty() {
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
		org.slf4j.Logger log = LoggerFactory.getLogger(SetMnoProperty.class);
		
		HttpSession session=request.getSession(false);
		String reply= request.getParameter("reply"); 
		String alphasourceadd= request.getParameter("alphasourceadd");
		String prefixes= request.getParameter("prefixes");
		String concatenation= request.getParameter("concatenation");
		String interfacetype= request.getParameter("interfacetype");
		String mnostr= request.getParameter("mno");
		String sourceadd= request.getParameter("sourceadd"); 
		String deliveryreceipt= request.getParameter("deliveryreceipt");
		String smscost= request.getParameter("smscost"); 
		String replycost= request.getParameter("replycost"); 
		String speed= request.getParameter("speed"); 
		String smscname= request.getParameter("smscname");
		String name= request.getParameter("name");
		
	
		
		
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		String errmsg="";
		
		log.info("reply "+reply);
		log.info("alphasourceadd "+alphasourceadd);
		log.info("concatenation "+concatenation);
		log.info("interfacetype "+interfacetype);
		log.info("name "+mnostr);
		log.info("sourceadd "+sourceadd);
		log.info("smscost "+smscost);
		log.info("replycost "+replycost);
		log.info("deliveryreceipt "+deliveryreceipt);
		
		
		
		RequestDispatcher rd=null;
		
		
		if ( smscost != null && sourceadd != null
				 && !("".equals(smscost))
				&& !("".equals(sourceadd)) 
				&& !("null".equals(smscost)) && !("null".equals(sourceadd))) {
		
			//Session sess=null;
			//SendingInterfaceManager sendinginterfacemanager=new SendingInterfaceManager();
			MnoManager mm=new MnoManager();
			MnodirectpropertyManager mdmanager=new MnodirectpropertyManager();
			StartnumberManager snm=new StartnumberManager();
			
			if("yes".equalsIgnoreCase(concatenation)){
				concatenationbol=true;
			}
			if("yes".equalsIgnoreCase(alphasourceadd)){
				alphasourceaddbol=true;
			}
			if("yes".equalsIgnoreCase(deliveryreceipt)){
				deliveryreceiptbol=true;
			}
			Long mnolong=Long.parseLong(mnostr);
			Mno mno=mm.findById(mnolong);
			
			//mno.setName(name);
			mno.setSmscost(Double.valueOf(smscost));
			mno.setReplycost(Double.valueOf(replycost));
			mno.setSourcenumber(sourceadd);
			mm.update(mno);
			
			Mnodirectproperty mdp=new Mnodirectproperty();
			
			mdp.setConcatenation(concatenationbol);
			mdp.setDeliveryreciepts(deliveryreceiptbol);
		
			mdp.setOfferedinterface(interfacetype);
			mdp.setMno(mno);
			mdp.setSmscost(Double.valueOf(smscost));
			mdp.setReplycost(Double.valueOf(replycost));
			mdp.setSpeed(speed);
			mdp.setReplypath(reply);
			mdp.setKannelsmscname(smscname);
			mdp.setName(name);
			mdmanager.add(mdp);
			
			/*
			 * Entry in sending interface
			 */
			SendingInterfaceManager simanager=new SendingInterfaceManager(); 
			Sendinginterface si=new Sendinginterface();
			si.setName(name);
			si.setKannelname(smscname);
			si.setInterfacepojoname("Mnodirectproperty");
			si.setLocalsmscost(Double.valueOf(smscost));
			si.setLocalreplycost(Double.valueOf(replycost));
			
			simanager.add(si);
		

			
			errmsg="Successfully Inserted ";
			rd=request
					.getRequestDispatcher("admin/setmnoproperty.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
			errmsg="please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/setmnoproperty.jsp");
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
