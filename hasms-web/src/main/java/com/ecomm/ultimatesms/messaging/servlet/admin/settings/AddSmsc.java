package com.ecomm.ultimatesms.messaging.admin.settings;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class addSMSCservlet
 */
public class AddSmsc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddSmsc() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		processRequest(request, response);
	}

	/*public void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		 * http://69.89.2.245:8080/payless4sms/addSMSCservlet?
		 * add_modify_smsc=modify& activeSMSC=1& smsc_name=& smsc_type=&
		 * submit=submit
		 

		PrintWriter out = response.getWriter();
		if ("modify".equals(request.getParameter("add_modify_smsc"))) {
			// Modify SMSC
			String active_SMSC = request.getParameter("activeSMSC");
			// Now we know which smsc has to be set as active
			// code to set the smsc as active and all other SMSCs as inactive.
			if (!active_SMSC.equals("") || active_SMSC != null) {
				DatabaseHelper hiberObj = new DatabaseHelper();
				hiberObj.updateActiveSMSC(active_SMSC);
			} else {
				out.println("Incorrect SMSC");
			}
		} else if ("add".equals(request.getParameter("add_modify_smsc"))) {
			// Add SMSC
			String smsc_name = request.getParameter("smsc_name");
			String smsc_type = request.getParameter("smsc_type");
			String active_inactive = request.getParameter("active_inactive");
			System.out.println("******************************smsc_name:"+smsc_name+"smsc_type:"+smsc_type+"active_inactive:"+active_inactive+"*****************");
			boolean flag = true;

			if (smsc_name.equals("") || smsc_name == null) {
				flag = false;
			}

			if (smsc_type.equals("") || smsc_type == null) {
				flag = false;
			}

			if (active_inactive.equals("") || active_inactive == null) {
				flag = false;
			}
			
			 * http://69.89.2.245:8080/payless4sms/addSMSCservlet?
			 * activeSMSC=3& smsc_name=demosmsc_an& smsc_type=an&
			 * active_inactive=active& submit=submit
			 

			if (flag) {
				DatabaseHelper hiberObj = new DatabaseHelper();
				hiberObj.addSMSC(smsc_name, smsc_type, active_inactive);

			} else {
				out.print("Incorrect Parameters");

			}

		} else {
			out.println("Incorrect Parameters");
		}

RequestDispatcher rd  = request.getRequestDispatcher("admin/addSMSC.jsp");
try {
	rd.forward(request, response);
} catch (ServletException e) {
	
}*/
//	}

}
