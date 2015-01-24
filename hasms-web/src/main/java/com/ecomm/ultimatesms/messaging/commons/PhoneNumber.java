package com.ecomm.ultimatesms.messaging.commons;

public class PhoneNumber {

	public String fixNumber(String mobileNumber) {
		
		
		String fixedNumber = null;
		
		if (mobileNumber.startsWith("0") && mobileNumber.length() == 10) {
			fixedNumber = mobileNumber.substring(1);
			
			fixedNumber = "27" + fixedNumber;
			
			return fixedNumber;
		}
		
		if (mobileNumber.length() == 9 && mobileNumber.startsWith("0")) {
			
			return null;
		}
		
		if (mobileNumber.length() == 9) {
			
			fixedNumber = "27" + mobileNumber;
			return fixedNumber;
		}
		if(mobileNumber.startsWith("27") && mobileNumber.length() == 11){
			return mobileNumber;
		}
		return null;
	}
//	public static void main(String arr[]){
//		new PhoneNumber().fixNumber("124676783");
//	}
}
