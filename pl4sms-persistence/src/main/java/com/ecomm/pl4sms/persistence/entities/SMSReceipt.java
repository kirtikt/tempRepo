package com.ecomm.pl4sms.persistence.entities;


import com.ecomm.pl4sms.persistence.constants.ReceiptTypeEnum;

import javax.persistence.*;


@Entity
@Table(name="\"SMS_RECEIPT\"")
public class SMSReceipt extends PL4Base {

    @OneToOne
    @PrimaryKeyJoinColumn
    private PL4_SMSMessage smsMessage;
    @Column(name="\"RECEIPT_TYPE\"")
    private ReceiptTypeEnum receiptType;
    @Column(name="\"ACCOUNT_INFO\"")
    private String accountInfo;
    @Column(name="\"CHARGE\"")
    private String charge;
    @Column(name="\"SMS_ID\"")
    private String smsId;
    @Column(name="\"DELIVERY_INFO\"")
    private String deliveryInfo;
    @Column(name="\"DESTINATION\"")
    private String destination;
    @Column(name="\"MESSAGE_ID\"")
    private String messageId;
    @Column(name="\"SOURCE\"")
    private String source;
    @Column(name="\"TIME_DATE\"")
    private String timeDate;
    @Column(name="\"SMS_CENTRE\"")
    private String smsCentre;
    @Column(name="\"STATUS\"")
    private String status;

    public SMSReceipt () {

    }

    public PL4_SMSMessage getSmsMessage() {
        return smsMessage;
    }

    public void setSmsMessage(PL4_SMSMessage smsMessage) {
        this.smsMessage = smsMessage;
    }

    public ReceiptTypeEnum getReceiptType() {
        return receiptType;
    }

    public void setReceiptType(ReceiptTypeEnum receiptType) {
        this.receiptType = receiptType;
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

    public void setTimeDate(String timeDatE) {
        this.timeDate = timeDatE;
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
