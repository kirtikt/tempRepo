package com.ecomm.pl4sms.composer.za.co.ecomm.pl4.kannel;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;




public class CreateUrl {
	private static Logger LOG = Logger.getLogger(CreateUrl.class);


	public String sendSMS(String host, String port, String username,
			String password, String from, String to, String text, String smsc,
			String dlrmask, String dlrurl) throws
			Exception {


		String targetURL = "http://" + host + ":" + port	+ "/cgi-bin/sendsms";

		PostMethod post = new PostMethod(targetURL);
		post.setRequestHeader("Content-Type", "text/plain; iso-8859-1");
		post.setRequestHeader("X-Kannel-Username", username);
		post.setRequestHeader("X-Kannel-Password", password);
		post.setRequestHeader("X-Kannel-To", to);
		post.setRequestHeader("X-Kannel-From", from);
		post.setRequestHeader("X-Kannel-DLR-Mask", dlrmask);
		post.setRequestHeader("X-Kannel-DLR-Url", dlrurl);
		post.setRequestHeader("X-Kannel-Coding", "0");
		post.setRequestHeader("X-Kannel-SMSC", smsc);
		post.setRequestBody(text);

		
		HttpClient httpclient = new HttpClient();
		String response="";
		try {

			int result = httpclient.executeMethod(post);

			LOG.info("Response Status Code: "+result+"  Response: "+post.getResponseBodyAsString());
			response = post.getResponseBodyAsString();
			if ("".equals(response)) {
				String resultCode = response.substring(0, response.indexOf(":"));
				if (!resultCode.equals("0")) {
					throw new Exception(resultCode);
				}
			} 
		} 
		catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		} finally {

			post.releaseConnection();
			HttpConnectionManager mgr = httpclient.getHttpConnectionManager();
			if (mgr instanceof SimpleHttpConnectionManager) {
			    ((SimpleHttpConnectionManager)mgr).shutdown();
			}
			post=null;
			httpclient=null;	
		} 
		return response.toString();
	} 
}