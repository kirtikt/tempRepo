package com.ecomm.pl4sms.composer.ws.service;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

 public class BulkSenderApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
 
	public BulkSenderApplication() {
		singletons.add(new BulkSenderService());
	}
 
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}