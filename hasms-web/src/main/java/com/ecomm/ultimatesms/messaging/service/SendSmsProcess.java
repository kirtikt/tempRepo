package com.ecomm.ultimatesms.messaging.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.designpattern.BulkSmsManager;
import com.ecomm.ultimatesms.designpattern.Observable;
import com.ecomm.ultimatesms.designpattern.ObservableImpl;
import com.ecomm.ultimatesms.designpattern.ObserverImpl;
import com.ecomm.ultimatesms.messaging.model.TextMessage1;
import com.ecomm.ultimatesms.messaging.utils.Helper;

public class SendSmsProcess extends Thread {

	Logger log = LoggerFactory.getLogger(SendSmsProcess.class);
	List<TextMessage1> listTestmsgObj;

	public SendSmsProcess(List<TextMessage1> listTestmsgObj) {
		this.listTestmsgObj = listTestmsgObj;
	}

	@Override
	public void run() {
		//System.out.println("inside run.."+listTestmsgObj.size());
		if(listTestmsgObj.size()>0){
		synchronized (listTestmsgObj) {
			
			int smsPerSec=((TextMessage1)listTestmsgObj.get(0)).getSmsPerSec();
			log.info("Sms per sec :: "+smsPerSec);
				for (int i = 0; i < listTestmsgObj.size(); i++) {
					System.out.println("inside loop");
					ObserverImpl observer = new ObserverImpl(
							(TextMessage1) listTestmsgObj.get(i));
					Observable observableImpl = new ObservableImpl();
					observableImpl.add(observer);
					observableImpl.notifyOperations();
					//To Minimize the load on server,sleep the thread for while 
					if ((smsPerSec !=0) && (i % smsPerSec == 0)) {
						// wait for 1 sec after sending 20 sms
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		}
		}
	}

}
