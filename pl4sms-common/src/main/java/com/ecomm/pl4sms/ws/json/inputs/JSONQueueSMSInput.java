package com.ecomm.pl4sms.ws.json.inputs;


public class JSONQueueSMSInput extends JSONInputBaseClass {

    private String batchId;

    private String textMessage;
    private String targetNumber;
    private String smsCentre;

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(String targetNumber) {
        this.targetNumber = targetNumber;
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
}
