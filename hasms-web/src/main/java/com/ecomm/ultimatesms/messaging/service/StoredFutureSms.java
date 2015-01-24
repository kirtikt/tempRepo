package com.ecomm.ultimatesms.messaging.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsobjManager;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smsobj;

public class StoredFutureSms extends Thread {
	Logger log = LoggerFactory.getLogger(StoredFutureSms.class);
	List<TextMessage1> listTestmsgObj;

	public StoredFutureSms(List<TextMessage1> listTestmsgObj) {
		this.listTestmsgObj = listTestmsgObj;
	}

	@Override
	public void run() {
		SmsobjManager som = new SmsobjManager();
		synchronized (listTestmsgObj) {
			
			for (int i = 0; i < listTestmsgObj.size(); i++) {
				TextMessage1 txtMsg = listTestmsgObj.get(i);
				Smsobj so = new Smsobj();
				so.setClientmanager(txtMsg.getClientmanagerId());
				so.setDeliverinfo(txtMsg.getDeliveryinfo());
				so.setFooter(txtMsg.getFooter());
				log.info("Future Date from text message"
						+ txtMsg.getFutureDate());

				DateFormat dateFormat = new SimpleDateFormat(
						"MM/dd/yyyy hh:mm:ss");

				Date dd;
				try {
					dd = dateFormat.parse(txtMsg.getFutureDate() + " 13:00:00");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					dd = new Date();
					e.printStackTrace();
				}
				

				so.setFuturedate(dd);

				so.setHoldingaccountid(txtMsg.getHoldingaccountId());
				so.setLoadeddate(new Date());
				so.setMessage(txtMsg.getMessage());
				so.setOperator(txtMsg.getOperator());
				so.setReciever(txtMsg.getReciever());
				so.setSender(txtMsg.getSender());
				so.setSmsc(txtMsg.getSmsc());
				so.setSmscount(txtMsg.getSmscount());
				so.setSmsid(txtMsg.getMessageID());
				so.setStatus("l");// l for loaded
				so.setSysuserid(txtMsg.getSysuserId());
				som.add(so);
			}
			
		}
	}
}
