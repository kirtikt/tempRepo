 package com.ecomm.ultimatesms.messaging.ws.client;

import java.net.URLEncoder;
import java.util.ArrayList;

import com.ecomm.ultimatesms.messaging.ws.input.GroupRecipient;
import com.ecomm.ultimatesms.messaging.ws.input.JSONCreditCheckInput;
import com.ecomm.ultimatesms.messaging.ws.input.JSONSendSMSInput;
import com.ecomm.ultimatesms.messaging.ws.input.SMSGroupMessage;
import com.ecomm.ultimatesms.messaging.ws.output.CreditCheckReturnType;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class BulkSenderClient {

	private static String BASE_URL = "http://localhost:8080/payless4sms/ws/smsbulksender";
	private static String CREDIT_CHECK_URL = "/j_credit_check";
	private static String SEND_SMS_URL = "/j_postsend_sms_list";
	private static String SEND_FUTUREDATES_SMS_URL = "/j_postsend_sms_list_futuredated";
	private static String GET_RECEIPT_URL = "/j_get_sms_receipt";
	private static String GET_REPLIES_URL = "/j_get_sms_replies";
	


	public CreditCheckReturnType checkCredits(JSONCreditCheckInput input) {
		CreditCheckReturnType returnType = new CreditCheckReturnType();
		HttpClient httpClient = new DefaultHttpClient();

		try {
			
			//RESTBulkSender/ws/smsbulksender/j_credit_check/ {"username":"username","password":"password","clientRef":"clientref"}
			
			//String url="http://localhost:8080/payless4sms/ws/smsbulksender/j_credit_check/{\"username\":\"sysuser\",\"password\":\"pass\",\"clientRef\":\"sysuser\"}";
			HttpGet request = new HttpGet(BASE_URL + CREDIT_CHECK_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
			request.addHeader("content-type", "application/x-www-form-urlencoded");
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				//System.out.println(response.toString());
				String responseString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			//	System.out.println("-------"+responseString);
				
				
				
				
				
				
				
				
				
				returnType = new Gson().fromJson(responseString, CreditCheckReturnType.class);

			}else { 
				System.out.println( "ERROR - CODE [" + response.getStatusLine().getStatusCode() + "]");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}

		return returnType;
	}

	
	public static void main(String[] args) {
	
	
	
	BulkSenderClient client = new BulkSenderClient();
	

		
		JSONCreditCheckInput input3 = new JSONCreditCheckInput();
		input3.setClientRef("sysuser");
		input3.setPassword("pass");
		input3.setUsername("sysuser");
		
	
	
		/*CreditsCheckInput input3 = new CreditsCheckInput();
		input3.setClientRef("jamshed");
		input3.setPassword("jamshed");
		input3.setUsername("jamshed");*/
		
		
		
		
		
			
		System.out.println(client.checkCredits(input3).getCredits_remaining());
		
		
	}
	
}
