package com.ecomm.ultimatesms.messaging.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsobjManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smsobj;

public class FutureSms {

	Timer timer;

	public FutureSms() {

		timer = new Timer();
		timer.schedule(new RemindTask(), 0, // initial delay
				1 * 30000); // subsequent rate
	}

	class RemindTask extends TimerTask {
		public void run() {
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

			String dd = dateFormat.format(date).toString();
			SmsobjManager som = new SmsobjManager();

			synchronized (this) {
				//System.out.println("inside Future SMS...........");
				List textMessageList = new LinkedList();
				List list = som.getSmsObjectList(dd);
				SmsManager sm=new SmsManager();
				//System.out.println(""+list);
					if (list != null) {
					for (int i = 0; i < list.size(); i++) {
						Smsobj smsobj = (Smsobj) list.get(i);
						smsobj.setStatus("s"); // S for sent
						som.update(smsobj);
						
						Sms sms=sm.getSmsObj(smsobj.getSmsid(),"00");
						sms.setFuturestatus(false);
						sm.update(sms);
						
						TextMessage1 textMessage = new TextMessage1();
						
						textMessage.setSmscount(smsobj.getSmscount());
						textMessage.setSender(smsobj.getSender());
						textMessage.setReciever(smsobj.getReciever());
						textMessage.setMessage(smsobj.getMessage());
						textMessage.setFooter(smsobj.getFooter());
						textMessage.setMessageID(smsobj.getSmsid());
						textMessage.setOperator(smsobj.getOperator());
						textMessage.setSmsc(smsobj.getSmsc());
						
						textMessage.setDatetime(dd);
						textMessage.setSysuserId(smsobj.getSysuserid());
						textMessage.setClientmanagerId(smsobj
								.getClientmanager());
						textMessage.setHoldingaccountId(smsobj
								.getHoldingaccountid());
						textMessage.setDeliveryinfo(smsobj.getDeliverinfo());
						
						textMessageList.add(textMessage);
						
					}
					Thread t = new SendSmsProcess(textMessageList);
					t.start();
				}
			}
		}
	}
}