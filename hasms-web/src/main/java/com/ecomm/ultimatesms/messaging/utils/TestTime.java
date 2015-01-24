 package com.ecomm.ultimatesms.messaging.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTime {

	Logger log=LoggerFactory.getLogger(TestTime.class);
	public static String convertToGMTOffsetToSATime(long currentmills){
		long ISTOffset = 19800000L;
		long twoHrs = 7200000L;
		long currSATime = currentmills -ISTOffset + twoHrs;
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
	
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currSATime);
		
		
		TimeZone tz = Calendar.getInstance().getTimeZone();
		
		return formatter.format(cal.getTime());
	}
//	public static void main(String[] a){
//	//	TestTime.convertToGMTOffsetToSATime(System.currentTimeMillis());
//	}
}

