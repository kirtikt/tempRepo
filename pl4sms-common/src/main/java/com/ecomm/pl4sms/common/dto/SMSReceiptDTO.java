package com.ecomm.pl4sms.common.dto;

public class SMSReceiptDTO {

    //token: accountInfo
    private String accountInfo;
    //token: charge
    private String charge;
    //token: smsId
    private String smsId;
    //token: delivInfo
    private String deliveryInfo;
    //token: destination
    private String destination;
    //token: msgd
    private String messageId;
    //token: source
    private String source;
    //token: timeDate
    private String timeDate;
    //token: smsc
    private String smsCentre;
    //token: status
    private String status;

    public SMSReceiptDTO() {

    }
//hello
    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getSmsCentre() {
        return smsCentre;
    }

    public void setSmsCentre(String smsCentre) {
        this.smsCentre = smsCentre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
