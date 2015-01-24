package com.ecomm.pl4sms.receipts;

import com.ecomm.pl4sms.common.dto.SMSReceiptDTO;
import com.ecomm.pl4sms.persistence.constants.ReceiptTypeEnum;
import com.ecomm.pl4sms.persistence.entities.SMSReceipt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SMSReceiptServlet extends HttpServlet {

    public static final String DELIVERY_INFO = "delivInfo";

    public static final String ACCOUNT_INFO ="accountInfo";
    public static final String CHARGE ="charge";
    public static final String SMS_ID ="smsId";
    public static final String DESTINATION ="destination";
    public static final String MSGID ="msgid";
    public static final String SOURCE ="source";
    public static final String TIMEDATE ="timeDate";
    public static final String SMS_CENTRE ="smsc";
    public static final String STATUS ="status";

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
			e.printStackTrace();
		}
		System.out.println("=======================================");
		System.out.println("=============REPLIES SERVLET============");
		System.out.println("=======================================");
		System.out.println("Reading All Request Parameters");

		Enumeration<String> paramNames = request.getParameterNames();

		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			System.out.println("Parameter Name :" + paramName);
			String[] paramValues = request.getParameterValues(paramName);

				for (int i = 0; i < paramValues.length; i++) {
					System.out.println("Parameter Value :" + paramValues[i]);
				}
		}

        SMSReceiptDTO receiptDTO = new SMSReceiptDTO();

        receiptDTO.setDeliveryInfo(request.getParameter(DELIVERY_INFO));
        receiptDTO.setAccountInfo(request.getParameter(ACCOUNT_INFO));
        receiptDTO.setCharge(request.getParameter(CHARGE));
        receiptDTO.setSmsId(request.getParameter(SMS_ID));
        receiptDTO.setDestination(request.getParameter(DESTINATION));
        receiptDTO.setMessageId(request.getParameter(MSGID));
        receiptDTO.setSource(request.getParameter(SOURCE));
        receiptDTO.setTimeDate(request.getParameter(TIMEDATE));
        receiptDTO.setSmsCentre(request.getParameter(SMS_CENTRE));
        receiptDTO.setStatus(request.getParameter(STATUS));



	}

}

