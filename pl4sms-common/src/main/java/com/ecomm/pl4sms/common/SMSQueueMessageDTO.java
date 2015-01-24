package com.ecomm.pl4sms.common;

import java.io.Serializable;

public class SMSQueueMessageDTO implements Serializable{

    private String batchId;

    private String messageText;

    private String msisdn;

    private String smsCentre;


    public SMSQueueMessageDTO(String batchId, String messageText, String msisdn, String smsCentre) {
        this.batchId = batchId;
        this.messageText = messageText;
        this.msisdn = msisdn;
        this.smsCentre = smsCentre;

    }

    public String getSmsCentre() {
        return smsCentre;
    }

    public void setSmsCentre(String smsCentre) {
        this.smsCentre = smsCentre;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
}
