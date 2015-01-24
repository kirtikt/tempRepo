package com.ecomm.ultimatesms.messaging.ws.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.ultimatesms.messaging.ws.businesslogic.SendGroupSMS;
import com.ecomm.ultimatesms.messaging.ws.input.JSONSendSMSInput;
import com.google.gson.Gson;

public class Test {

//@org.testng.annotations.Test
public void test() {

	
	

	
	String a="{"+
			  "\"username\":\"varsha\","+
			  "\"password\":\"varsha\","+
			  "\"clientRef\":\"varsha\","+
			  "\"messageList\":[{\"message\":\"Test Message1\",\"msisdn\":\"0828501754\",\"identifier\":\"Johan Smith 1\"},"+
			                 "{\"message\":\"Test Message4\",\"msisdn\":\"0829333434\",\"identifier\":\"Jetish Naido\"}],"+ 
			                   
			  "\"groupMessageList\":{\"message\":\"Test Message 1\","+
			                                "\"groupmessage\":[{\"msisdn\":\"0829333434\",\"identifier\":\"Johan Smith 1\"},"+
			                                              "{\"msisdn\":\"0829333434\",\"identifier\":\"Johan Smith 1\"}]"+
			                     "},"+
			  "\"clienttransactionreference\":\"aerbc1211\","+
			  "\"futuredate\":\"12-12-2013 \","+
			  "\"callbackURL\":\"callbackURL\""+
			"}";


	
	//	System.out.println(a);
		
		JSONSendSMSInput input = new Gson().fromJson(a, JSONSendSMSInput.class);
		
		SendGroupSMS sendGroupSMS=new SendGroupSMS();
		sendGroupSMS.sendSMS(input);
		
}


//@org.testng.annotations.Test
public void tst(){
	
	HoldingaccountManager holdingaccountManager=new HoldingaccountManager();
	System.out.println(holdingaccountManager.isHoldingaccountByClntTransRefExist("abc1211"));
}


}
