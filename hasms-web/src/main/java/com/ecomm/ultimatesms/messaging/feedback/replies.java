package com.ecomm.ultimatesms.messaging.feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class replies extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public replies() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("=======================================");
		System.out.println("=============REPLIES MODULE============");
		System.out.println("=======================================");
		System.out.println("Reading All Request Parameters");
		// System.out.println("<HTML>");
		// System.out.println("<BODY>");
		// System.out.println("<TABLE>");
		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			System.out.println("Parameter Name :" + paramName);
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() == 0)
					System.out.println("No Value");
				else
					out.print(paramValue);
			} else {
				// System.out.println("<UL>");
				for (int i = 0; i < paramValues.length; i++) {
					System.out.println("Parameter Value :" + paramValues[i]);
				}
				// System.out.println("</UL>");
			}
		}

		// System.out.println("</TABLE>");
		/*
		 * Parameters recieved status=%d& smsc=%i& timeDate=%t& destination=%p&
		 * delivInfo=%a& source=%P& msgid_sending=%F& msgid_delivery=%k&
		 * charge=%B& accountInfo=%o& smsid="+ id + "&
		 * message=" + URLEncoder.encode(tempmsg, "UTF-8");
		 */

		// System.out.println("</BODY></HTML>");

	}

}

// public class replies extends HttpServlet {
// private static final long serialVersionUID = 1L;
//
// public replies() {
// super();
// }
//
// protected void doGet(HttpServletRequest request,
// HttpServletResponse response) throws ServletException, IOException {
// processRequest(request, response);
// }
//
// protected void doPost(HttpServletRequest request,
// HttpServletResponse response) throws ServletException, IOException {
// processRequest(request, response);
// }
//
// public void processRequest(HttpServletRequest request,
// HttpServletResponse response) {
//
//
// }
// }
