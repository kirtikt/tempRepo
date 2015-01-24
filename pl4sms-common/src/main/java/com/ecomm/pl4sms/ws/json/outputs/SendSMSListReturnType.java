package com.ecomm.pl4sms.ws.json.outputs;
import java.util.Date;

import java.util.Date;


public class SendSMSListReturnType  extends JSON_Output_BaseClass{

	private String status;

    public SendSMSListReturnType() {

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}