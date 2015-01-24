package com.ecomm.mdb.ejb.kannel;

import java.net.URLEncoder;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ecomm.mdb.ejb.exception.SMSPushRequestException;



public class CreateUrlTest {

	//@Test
	public void sendSMS() {
		String x = "http://68.169.52.16:8080/payless4sms/feedback?status=%d&smsc=%i&timeDate=%t&destination=%p&delivInfo=%a&source=%P&msgid_sending=%F&msgid_delivery=%k&charge=%B&accountInfo=%o&smsid=123456&message=this+is+test+%40";
//		String feeed=URLEncoder.encode("http://68.169.52.16:8080/payless4sms/feedback?status=%d&smsc=%i&timeDate=%t&destination=%p&delivInfo=%a&source=%P&msgid_sending=%F&msgid_delivery=%k&charge=%B&accountInfo=%o&smsid=123456&message=this+is+test+%40");
		String response="";
		try {
			response = new CreateUrl().sendSMS("68.169.52.16", "13013", "tester",
					"foobar", "default",
					"277843639524", "!@#$%^&*() this is a test ",
					"silverstreettest", "31",x);
		} catch (SMSPushRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertEquals(response, "Sent.");
	}
}