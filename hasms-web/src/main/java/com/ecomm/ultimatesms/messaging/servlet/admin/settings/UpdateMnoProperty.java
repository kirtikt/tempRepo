package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnoManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnodirectpropertyManager;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.StartnumberManager;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnodirectproperty;

/**
 * Servlet implementation class RegisterUser
 */
public class UpdateMnoProperty extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateMnoProperty() {
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
		if (request.getSession(false) == null
				|| request.getSession(false).getAttribute("isActive") == null
				|| "null".equals(request.getSession(false).getAttribute(
						"isActive"))) {
			response.sendRedirect("/payless4sms/adminLogin.jsp?errorMessage=please login");
			return;
		}
		org.slf4j.Logger log = LoggerFactory.getLogger(UpdateMnoProperty.class);

		HttpSession session = request.getSession(false);
		String reply = request.getParameter("reply");
		String alphasourceadd = request.getParameter("alphasourceadd");
		String prefixes = request.getParameter("prefixes");
		String concatenation = request.getParameter("concatenation");
		String interfacetype = request.getParameter("interfacetype");
		String mnostr = request.getParameter("mno");
		String sourceadd = request.getParameter("sourceadd");
		String deliveryreceipt = request.getParameter("deliveryreceipt");
		String smscost = request.getParameter("smscost");
		String replycost = request.getParameter("replycost");
		String smscname = request.getParameter("smscname");
		String name = request.getParameter("name");

		boolean replybol = false;
		boolean alphasourceaddbol = false;
		boolean concatenationbol = false;
		boolean deliveryreceiptbol = false;
		String errmsg = "";

		// log.info("reply "+reply);
		// log.info("alphasourceadd "+alphasourceadd);
		// log.info("concatenation "+concatenation);
		// log.info("interfacetype "+interfacetype);
		// log.info("name "+mnostr);
		// log.info("sourceadd "+sourceadd);
		// log.info("smscost "+smscost);
		// log.info("replycost "+replycost);
		// log.info("deliveryreceipt "+deliveryreceipt);
		//

		RequestDispatcher rd = null;

		if (smscost != null && sourceadd != null && !("".equals(smscost))
				&& !("".equals(sourceadd)) && !("null".equals(smscost))
				&& !("null".equals(sourceadd))) {

			// Session sess=null;
			// SendingInterfaceManager sendinginterfacemanager=new
			// SendingInterfaceManager();
			MnoManager mm = new MnoManager();
			MnodirectpropertyManager mdmanager = new MnodirectpropertyManager();
			StartnumberManager snm = new StartnumberManager();

			if ("yes".equalsIgnoreCase(concatenation)) {
				concatenationbol = true;
			}
			if ("yes".equalsIgnoreCase(alphasourceadd)) {
				alphasourceaddbol = true;
			}
			if ("yes".equalsIgnoreCase(deliveryreceipt)) {
				deliveryreceiptbol = true;
			}
			Long mnolong = Long.parseLong(mnostr);

			SessionFactory sessionFactory = null;
			Session hibersession = null;
			try {

				sessionFactory = HibernateUtil.getSessionFactory();

				hibersession = sessionFactory.openSession();
				Transaction tr = hibersession.beginTransaction();

				// Mno mno=mm.findById(mnolong);
				Mno mno = (Mno) hibersession.get(Mno.class, mnolong);

				mno.setName(name);
				mno.setSmscost(Double.valueOf(smscost.trim()));
				mno.setReplycost(Double.valueOf(replycost.trim()));
				mno.setSourcenumber(sourceadd.trim());
				hibersession.update(mno);
				tr.commit();
				// mm.update(mno);

				String mnopropid = request.getParameter("mnopropid");
				if (mnopropid != null && mnopropid != "") {
					long longpropid = Long.parseLong(mnopropid);
					// Mnodirectproperty mdp=mdmanager.findById(longpropid);
					Mnodirectproperty mdp = (Mnodirectproperty) hibersession
							.get(Mnodirectproperty.class, longpropid);

					Transaction tr1 = hibersession.beginTransaction();
					mdp.setConcatenation(concatenationbol);
					mdp.setDeliveryreciepts(deliveryreceiptbol);
					
					mdp.setOfferedinterface(interfacetype.trim());
					mdp.setMno(mno);
					mdp.setSmscost(Double.valueOf(smscost.trim()));
					mdp.setReplycost(Double.valueOf(replycost.trim()));

					mdp.setReplypath(reply.trim());
					mdp.setKannelsmscname(smscname.trim());
					// mdmanager.update(mdp);
					hibersession.update(mdp);
					tr1.commit();
					mdp = null;
					
				}
				errmsg = "Successfully Updated ";
			} catch (Exception e) {
				e.printStackTrace();
				errmsg = "Not Updated";
			} finally {
				hibersession.close();
				hibersession = null;
			}
			

			rd = request.getRequestDispatcher("admin/interfacemno.jsp");
			request.setAttribute("errorMessage", errmsg);
		} else {
			errmsg = "please enter all parameters";
			log.info("user not added to database. parameters are null.");
			rd = request.getRequestDispatcher("admin/interfacemno.jsp");
			request.setAttribute("errorMessage", errmsg);
		}

		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
}
