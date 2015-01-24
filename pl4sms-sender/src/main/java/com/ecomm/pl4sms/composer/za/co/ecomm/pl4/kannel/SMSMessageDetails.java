package com.ecomm.pl4sms.composer.za.co.ecomm.pl4.kannel;

public class SMSMessageDetails {

	private String messageID;
	private String smsCentreIdentifier;
	private String receiver;
	private String sender;
	private String message;
	private String footer;
	private String operator;
	private String datetime;
	private String sysUserId;
	private String clientManagerId;
	private String holdingAccountId;
	private String deliveryInfo;
	private String futureDate;
	private int smsPerSec;
    private int smsCount;
	
    public SMSMessageDetails() {

    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSmsCentreIdentifier() {
        return smsCentreIdentifier;
    }

    public void setSmsCentreIdentifier(String smsCentreIdentifier) {
        this.smsCentreIdentifier = smsCentreIdentifier;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getClientManagerId() {
        return clientManagerId;
    }

    public void setClientManagerId(String clientManagerId) {
        this.clientManagerId = clientManagerId;
    }

    public String getHoldingAccountId() {
        return holdingAccountId;
    }

    public void setHoldingAccountId(String holdingAccountId) {
        this.holdingAccountId = holdingAccountId;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getFutureDate() {
        return futureDate;
    }

    public void setFutureDate(String futureDate) {
        this.futureDate = futureDate;
    }

    public int getSmsPerSec() {
        return smsPerSec;
    }

    public void setSmsPerSec(int smsPerSec) {
        this.smsPerSec = smsPerSec;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }
}
