package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;


import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;

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
public class UpdateSmscInterfaceProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateSmscInterfaceProperty() {
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

	/**
	 * This update the smsc property.
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(request.getSession(false)==null || request.getSession(false).getAttribute("isActive")==null || "null".equals(request.getSession(false).getAttribute("isActive")) ){
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		org.slf4j.Logger log = LoggerFactory.getLogger(SetSmscProperty.class);
		
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
		
//		log.info("reply "+reply);
//		log.info("alphasourceadd "+alphasourceadd);
//		log.info("concatenation "+concatenation);
//		log.info("interfacetype "+interfacetype);
//		log.info("name "+name);
//		
//		log.info("deliveryreceipt "+deliveryreceipt);
//		log.info("kannelname "+kannelname);
//		
		
		RequestDispatcher rd=null;
		
		
		if (kannelname != null && !("".equals(kannelname)) && !("null".equals(kannelname))) {
			
			
			
			SmscproxypropertyManager sppmanager=new SmscproxypropertyManager();
			
			
			
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
			
			String smscprpid=request.getParameter("smscinterfaceid");
			Long smscprpidlong=Long.parseLong(smscprpid.trim());
			/*
			 * Add the property for smsc
			 */
			
			 Session hiberSession=null;
			 org.hibernate.Transaction tr=null;
			try{
				 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				 hiberSession=sessionFactory.openSession();
				 tr= hiberSession.beginTransaction();
				 SmscproxyManager mnom1=new SmscproxyManager();
			 long id=Long.parseLong(smscid);
			 Smscproxy sp=mnom1.findById(id);
			 Smscproxyproperty spp=	 (Smscproxyproperty)hiberSession.get(Smscproxyproperty.class, smscprpidlong);
			//Smscproxyproperty spp =sppmanager.findById(smscprpidlong);
			spp.setName(name);
			spp.setConcatination(concatenationbol);
			spp.setDeliveryreciepts(deliveryreceiptbol);
			spp.setKannelsmscname(kannelname);
			spp.setSmscproxy(sp);
			spp.setOfferedinterface(interfacetype);
			// count of sms to send per second.
			spp.setSpeedofsms(speed);
			spp.setReplypath(reply);
			
			
			/*
			 * Entry in sending interface
			 */
			//SendingInterfaceManager simanager=new SendingInterfaceManager(); 
			Set<Sendinginterface> setsendinginterface=spp.getSendinginterfaces();
		   
			
			Sendinginterface si=setsendinginterface.iterator().next();
			si.setInterfacename(name);
			si.setKannelname(kannelname);
			si.getMnosmscproperties().clear();
			si.setName(name);
			si.getMnosmscproperties().clear();
			/*
			 * Add the mno smscost,replycost,and source number
			 */
			
			MnoManager mnom=new MnoManager();
			 Criteria crit = hiberSession.createCriteria(Mno.class);
            
			List<Mno> list=crit.list();
		   
			Iterator<Mno> it=list.iterator();
//		   
		    
			
		    
		    Set<Mnosmscproperty> set=new HashSet<Mnosmscproperty>();
		    while(it.hasNext()){
		        Mno mno=(Mno)it.next();
		     
		        Mnosmscproperty msc=  new Mnosmscproperty();
		      
		       msc.setMno(mno);
		       
		       msc.setSmscost(Double.valueOf(request.getParameter(mno.getName().trim())));
		       String rep= "r"+mno.getName().trim();
		       
		       msc.setReplycost(Double.valueOf(request.getParameter(("r"+mno.getName()).trim())));
		       msc.setSourcenumber(request.getParameter(("sa"+mno.getName()).trim()));
		      
		       msc.setSendinginterface(si);
		       
		       hiberSession.save(msc);
		       set.add(msc);
		       
		       }
		       si.setMnosmscproperties(set);
		       hiberSession.update(spp);
		       hiberSession.update(si);
		       tr.commit();
		      
			}	
			catch(Exception e){
				if(tr!=null){
				tr.rollback();
				}
				e.printStackTrace();
			}
			finally{
				hiberSession.flush();
				hiberSession.close();
				hiberSession=null;
				tr=null;
				
			
			}
			errmsg="Successfully updated ";
			log.info("Successfully Inserted ");
			rd=request
					.getRequestDispatcher("admin/smscproxyinterface.jsp");
			request.setAttribute("errorMessage", errmsg);
		}else{
			errmsg="please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd=request
					.getRequestDispatcher("admin/smscproxyinterface.jsp");
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
