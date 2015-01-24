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




import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnosmscpropertyManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SendingInterfaceManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxypropertyManager;



import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnosmscproperty;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxyproperty;


/**
 * Servlet implementation class RegisterUser
 */
public class SetMnoSmscProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetMnoSmscProperty() {
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
		Logger log = Logger.getLogger(SetMnoSmscProperty.class);
		BasicConfigurator.configure();
		HttpSession session=request.getSession(false);
		String reply= request.getParameter("reply"); 
		String alphasourceadd= request.getParameter("alphasourceadd");
		
		String sendinginterface= request.getParameter("sendinginterface"); 
		String mno= request.getParameter("mno");
		
		
		String sourceadd= request.getParameter("sourceadd"); 
		String deliveryreceipt= request.getParameter("deliveryreceipt");
		String smscost= request.getParameter("smscost"); 
		String replycost= request.getParameter("replycost"); 
		
	
		boolean replybol=false;
		boolean alphasourceaddbol=false;
		boolean concatenationbol=false;
		boolean deliveryreceiptbol=false;
		
		long sendinginterfaceid=Long.parseLong(sendinginterface);
		long mnoid=Long.parseLong(mno);
		
		String errmsg="";
		
//		log.info("reply "+reply);
//		log.info("alphasourceadd "+alphasourceadd);
//		
//		log.info("sourceadd "+sourceadd);
//		//log.info("smscost "+smscost);
//	//	log.info("replycost "+replycost);
//		log.info("deliveryreceipt "+deliveryreceipt);
//		
		
		RequestDispatcher rd=null;
		
		
		if (sourceadd != null
				&& !("null".equals(sourceadd))) {
			
			//Session sess=null;
			//SendingInterfaceManager sendinginterfacemanager=new SendingInterfaceManager();
			SmscproxypropertyManager sppmanager=new SmscproxypropertyManager();
			Smscproxyproperty spp =new Smscproxyproperty();
		
			if("yes".equalsIgnoreCase(alphasourceadd)){
				alphasourceaddbol=true;
			}
			if("yes".equalsIgnoreCase(deliveryreceipt)){
				deliveryreceiptbol=true;
			}
			if("yes".equalsIgnoreCase(reply)){
				replybol=true;
			}
			
			SendingInterfaceManager sifm=new SendingInterfaceManager();
			MnoManager mm=new MnoManager();
			
			Sendinginterface siObj=sifm.findById(sendinginterfaceid);
			Mno mObj=mm.findById(mnoid);
			
			MnosmscpropertyManager mspm=new MnosmscpropertyManager();
		 
			Mnosmscproperty mnsp =	new Mnosmscproperty();
			mnsp.setAlphanumericsenderid(alphasourceaddbol);
			mnsp.setMno(mObj);
			mnsp.setReplycost(Double.parseDouble(replycost));
			mnsp.setReplypath(replybol);
			mnsp.setSendinginterface(siObj);
			mnsp.setSmscost(Double.parseDouble(smscost));
			mnsp.setSourcenumber(sourceadd);
			
			mspm.add(mnsp);
			
			errmsg="Successfully Inserted ";
			
			rd=request
					.getRequestDispatcher("admin/drpmnoprop.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
			errmsg="please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/drpmnoprop.jsp");
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
