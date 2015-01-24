package com.ecomm.pl4sms.ws.json.inputs;

import java.io.Serializable;

public class SMSMessage implements Serializable{
	
	private String msisdn;
	private String messageText;



	public SMSMessage () {
	
	} 
	
	public SMSMessage( String msisdn, String messageText) {
		this.msisdn = msisdn;
		this.messageText = messageText;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
