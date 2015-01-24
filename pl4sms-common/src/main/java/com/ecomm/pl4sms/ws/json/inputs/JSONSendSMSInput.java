package com.ecomm.pl4sms.ws.json.inputs;
import java.io.Serializable;
import java.util.ArrayList;

public class JSONSendSMSInput extends JSONInputBaseClass implements Serializable{


	private ArrayList<SMSMessage> messageList;

    public JSONSendSMSInput() {}

    public ArrayList<SMSMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<SMSMessage> messageList) {
        this.messageList = messageList;
    }
}
