package com.ecomm.mdb.ejb.kannel;

import org.slf4j.LoggerFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.ecomm.mdb.ejb.exception.SMSPushRequestException;

public class CreateUrl {
	org.slf4j.Logger log = LoggerFactory.getLogger(CreateUrl.class);
	/**
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param from
	 * @param to
	 * @param text
	 * @param smsc
	 * @param dlrmask
	 *            <project xmlns="http://maven.apache.org/POM/4.0.0"
	 *            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	 *            xsi:schemaLocation=
	 *            "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"
	 *            > <modelVersion>4.0.0</modelVersion> <groupId>mdb</groupId>
	 *            <artifactId>mdb</artifactId> <version>0.0.1-SNAPSHOT</version>
	 * 
	 *            <dependency> <id>jarid</id> <version>jarversion</version>
	 *            <properties> <ejb.bundle>true</ejb.bundle> </properties>
	 *            </dependency>
	 * 
	 * 
	 * 
	 *            </project>
	 * @param dlrurl
	 * @return
	 * @throws SMSPushRequestException
	 * @throws Exception
	 */
	public String sendSMS(String host, String port, String username,
			String password, String from, String to, String text, String smsc,
			String dlrmask, String dlrurl) throws SMSPushRequestException,
			Exception {

		// Get target URL
		String strURL = "http://" + host + ":" + port	+ "/cgi-bin/sendsms";
		// Prepare HTTP post
		PostMethod post = new PostMethod(strURL);

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
		
//		NameValuePair[] nvp = new NameValuePair[1];
//		nvp[0] = new NameValuePair("text", text);
//		post.setRequestBody(nvp);
//		post.addParameter(new NameValuePair("text", text));
//		post.setRequestEntity(new StringRequestEntity(text));
		
		HttpClient httpclient = new HttpClient();
		//httpclient.getHttpConnectionManager().get
		// Execute request
		String response="";
		try {

			int result = httpclient.executeMethod(post);
			// Display status code
			// Display response
			log.info("Response Status Code: "+result+"  Response: "+post.getResponseBodyAsString());
			response = post.getResponseBodyAsString();
			if ("".equals(response)) {
				String resultCode = response.substring(0, response.indexOf(":"));
				if (!resultCode.equals("0")) {
					throw new SMSPushRequestException(resultCode);
				}
			} 
		} 
		catch (Throwable e) {
			e.getCause();
			e.printStackTrace();
		} finally {
			// Release current connection to the connection pool 
			// once you are done
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