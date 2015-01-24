package com.ecomm.ultimatesms.messaging.ws.helper;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class CreditsCheckHelperTest {

	
//@Test
	public void tests1() {
	
String a="http://68.169.52.16:8080/payless4sms/ws/smsbulksender/j_postsend_sms_list/{\"username\":\"varsha\",\"password\":\"varsha\",\"clientRef\":\"varsha\",\"messageList\":[{\"msisdn\":\"27828501754\",\"message\":\"Test Message 1\"},{\"msisdn\":\"27828501754\",\"message\":\"Test Message 1\"}],\"sessionRef\":\"sessionref\",\"transactionRef\":\"transactionref\",\"callbackURL\":\"callback\",\"getResultsURL\":\"getresults\"}";



Sms sms =new Sms();

sms.setClientmanager(null);

//insert into sms values(200000,"86d86dad-b60d-4132-8b5b-e4f2e2a5a2e4","rwerweewrewr","wrwe","27828501754","00","2011-11-24 01:37:05.218+05:30",1,null,0,0,66,2,327,65,"2011-11-24 01:37:05.218+05:30",null,null,null,null,FALSE)




System.out.println(a);
	
	
	}
	
	
}
