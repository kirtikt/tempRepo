package com.ecomm.ultimatesms.messaging.model;

import java.io.Serializable;

public class TextMessage1 implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1677516765998057693L;
	/**
	 * Generated Serial version UUID
	 */
	private String messageID;
	private String smsc;
	private String reciever; 
	private String sender ;
	private String message;
	private String footer;
	private String operator;
	private String datetime;
	private long sysuserId;
	private long clientmanagerId;
	private int smscount;
	private long holdingaccountId;
	private String deliveryinfo;
	private String futureDate;
	private int smsPerSec;
	
	public int getSmsPerSec() {
		return smsPerSec;
	}

	public void setSmsPerSec(int smsPerSec) {
		this.smsPerSec = smsPerSec;
	}

	public String getFutureDate() {
		return futureDate;
	}

	public void setFutureDate(String futureDate) {
		this.futureDate = futureDate;
	}

	public String toString() {
		return ("Reciever: " + reciever + "\n" +
		"Sender :" + sender + "\n " +
		"Message :" + message + "\n " +
		"Footer :" + footer + "\n " +
		"Operator :" + operator + "\n " +
		"DateTime :" + datetime + "\n " +
		"userid :" + sysuserId + "\n "
		);
	}
	
	public String getSmsc() {
		return smsc;
	}
	public void setSmsc(String smsc) {
		this.smsc = smsc;
	}
	
	
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
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
	public void setDatetime(String l) {
		this.datetime = l;
	}
	public long getSysuserId() {
		return sysuserId;
	}
	public void setSysuserId(long sysuserId) {
		this.sysuserId = sysuserId;
	}
	public long getClientmanagerId() {
		return clientmanagerId;
	}
	public void setClientmanagerId(long clientmanagerId) {
		this.clientmanagerId = clientmanagerId;
	}
	public int getSmscount() {
		return smscount;
	}
	public void setSmscount(int smscount) {
		this.smscount = smscount;
	}
	public String getMessageID() {
		return messageID;
	}
	
	
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getDeliveryinfo() {
		return deliveryinfo;
	}
	public void setDeliveryinfo(String deliveryinfo) {
		this.deliveryinfo = deliveryinfo;
	}
	public long getHoldingaccountId() {
		return holdingaccountId;
	}
	public void setHoldingaccountId(long holdingaccountId) {
		this.holdingaccountId = holdingaccountId;
	}
	
	
}
