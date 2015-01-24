package com.ecomm.pl4sms.common.kannel;


public class KannelReceiptURL {

    private String dlrMask;
    private String receiptServletHttpUrl;

    private String smsId;
    private String status;
    private String smsc;
    private String timeDate;
    private String destination;
    private String delivInfo;
    private String source;
    private String msgId;
    private String charge;
    private String accountInfo;

    public KannelReceiptURL() {


    }


    public String toString() {

        StringBuffer receiptURLString = new StringBuffer();
        receiptURLString.append( "&dlr-mask").append("")
        .append("&dlr-url").append(receiptServletHttpUrl)
        .append("&smsId").append(smsId)
                .append("&status").append(status)
                .append("&smsc").append(smsc)
                .append("&timeDate").append(timeDate)
                .append("&destination").append(destination)
                .append("&delivInfo").append(delivInfo)
                .append("&source").append(source)
                .append("&msgId").append(msgId)
                .append("&charge").append(charge)
                .append("&accountInfo").append(accountInfo);
        return receiptURLString.toString();
    }
}

