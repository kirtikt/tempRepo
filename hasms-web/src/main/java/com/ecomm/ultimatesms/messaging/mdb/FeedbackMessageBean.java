 package com.ecomm.ultimatesms.messaging.MDB;

import java.io.Serializable;

public class FeedbackMessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3905589203709656005L;

	private String status;
	private String SMSC;
	private String timeDate;
	private String destination;
	private String deliveryInfo ;
	private String source ;
	private String messageSendingID ;
	private String messageDeliveryID;
	private String accountInfo;
	private String charge ;
	private String smsID;
	private String message;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSMSC() {
		return SMSC;
	}
	public void setSMSC(String sMSC) {
		SMSC = sMSC;
	}
	public String getTimeDate() {
		return timeDate;
	}
	public void setTimeDate(String timeDate) {
		this.timeDate = timeDate;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDeliveryInfo() {
		return deliveryInfo;
	}
	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMessageSendingID() {
		return messageSendingID;
	}
	public void setMessageSendingID(String messageSendingID) {
		this.messageSendingID = messageSendingID;
	}
	public String getMessageDeliveryID() {
		return messageDeliveryID;
	}
	public void setMessageDeliveryID(String messageDeliveryID) {
		this.messageDeliveryID = messageDeliveryID;
	}
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
	public String getSmsID() {
		return smsID;
	}
	public void setSmsID(String smsID) {
		this.smsID = smsID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
