package com.ecomm.ultimatesms.messaging.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageParamValidator {

	Logger log=LoggerFactory.getLogger(MessageParamValidator.class);
	public String validateParams(String reciever, String sender,
			String message, String footer, String datetime, String replies) {
		String error = ""; // "" means no error
		if (reciever == null || reciever.length() == 0) {
			error = "Receiver is not set \n";
		}
		String[] rec_arr = reciever.split(",");
		for (int i = 0; i <= rec_arr.length - 1; i++) {
			if (!rec_arr[i].matches("[0-9]*")) {
				error ="Sorry please enter Numbers only in Cell Number  \n";
			}
		}
		
		
		//String validmessage=check(message);
		//if(!validmessage)
		//	error="Please enter standard SMS characters  ";
		//log.info("message "+validmessage);
		
		/*char mesgArray[]=message.toCharArray();
		if(mesgArray.length>160){
			log.info("Message is more than 160 char bits.");
			error="Not allowing more than 160 char in message text";
		}*/
		return error;
	}
	public String check(String message)
	{
	
		String validmessage="";
		char[] m=message.toCharArray();
		
		for(int i=0;i<m.length;i++)
		{
			if(!((int)m[i]>=32&&(int)m[i]<126))
			{
				m[i]=' ';
				// code change for the purpose of changing the validation for & bit characters
				//validmessage=false;
				//break;
			}
			
		}
		
		validmessage=String.valueOf(m);
		return validmessage;
		
	}
//	public static void main(String args[])
//	{
//		String message="hello ind™¾® ia";
//		String validmessage=check(message);
//		System.out.print(validmessage);
//		
//	}
	
	public String validateParams(List reciever, String sender, String message, String footer, String datetime, String replies) {
		String error = ""; // "" means no error
		if (reciever == null) {
			error = "Reciever is not set";
		}
		int listSize=reciever.size();
		if(listSize==0){
			error = "Reciever is not set";
		}
		/*char mesgArray[]=message.toCharArray();
		if(mesgArray.length>160){
			log.info("Message is more than 160 char bits.");
			error="Not allowing more than 160 char in message text";
		}*/
		String validmessage=check(message);
	//	if(!validmessage)
		//	error=" Please enter standard SMS characters  ";
	
		/*for (int i = 0; i <= reciever.size()- 1; i++) {
			String number=reciever.get(0).toString();
			if (!number.matches("[0-9]*")) {
				error = "Sorry please enter Numbers only";
			}
		}*/
		
		List<String>recieverlist=reciever;
		
		for(String number1:recieverlist)
		{
			
			if (!number1.matches("[0-9]*")) {
				error = "Sorry please enter Numbers only";
				log.info("error "+error);
			}
		}
		return error;
	}

//	public boolean validateParamsCount(Enumeration en) {
//		int enCount = 0;
//		while (en.hasMoreElements()) {
//			enCount++;
//		}
//		if (enCount == 6) {
//			return true;
//		}
//		return false;
//	}
}
