package com.ecomm.ultimatesms.messaging.feedback;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxyManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class feedbacktest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public feedbacktest() {
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
		Logger log = LoggerFactory.getLogger(feedbacktest.class);
		
		String smsId=request.getParameter("smsId");
		String status=request.getParameter("status");
		String smsc=request.getParameter("smsc");
		String timeDate=request.getParameter("timeDate");
		String destination=request.getParameter("destination");
		String delivInfo=request.getParameter("delivInfo");
		String source=request.getParameter("source"); 
		String msgid=request.getParameter("msgid");
		String charge=request.getParameter("charge");
		String accountInfo=request.getParameter("accountInfo");
		
		
		String content="-----------------------------------------------------------------"+
						"\n smsId :"+smsId+
						"\n status :"+status+
						"\n smsc :"+smsc+
						"\n timeDate :"+timeDate+
						"\n destination :"+destination+
						"\n delivInfo :"+delivInfo+
						"\n source :"+source+
						"\n msgid :"+msgid+
						"\n charge :"+charge+
						"\n accountInfo :"+accountInfo+
						"-----------------------------------------------------------------";
		log.info(content);
		SmsManager smsManager=new SmsManager();
		
		ClientmanagersManager clientmanagersManager=new ClientmanagersManager();
		Clientmanager clientmanager=clientmanagersManager.getclientManager(4);
		

		

		
		Sms smsObj=new Sms();
		smsObj.setSmsid(smsId);
		smsObj.setStatus(status);
		smsObj.setDatetime(new Date());
		smsObj.setDestination(destination);
		smsObj.setDeliveryinfo(delivInfo);
		smsObj.setSource(source);
		smsObj.setMsgid(msgid);
		smsObj.setClientmanager(clientmanager);
		smsObj.setSendinginterface(clientmanager.getSendinginterface());
		smsManager.add(smsObj);
		

		
		
		
		
		
		
		
		
		//smsManager.add();
		
		
		
		
		
		
		
		
		
		
		
	}
	

}
