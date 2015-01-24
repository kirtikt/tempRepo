package com.ecomm.pl4sms.ws;


public class DlrUrlDTO {

    private String serverName;
    private String portNumber;
    private String urlContext;

    private String smsId="1";
    private String status="%d";
    private String smsc="%i";
    private String timeDate="%t";
    private String destination="%p";
    private String delivInfo="%a";
    private String source="%P";
    private String msgid="%k";
    private String charge="%B";
    private String accountInfo="%o";


    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getUrlContext() {
        return urlContext;
    }

    public void setUrlContext(String urlContext) {
        this.urlContext = urlContext;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSmsc() {
        return smsc;
    }

    public void setSmsc(String smsc) {
        this.smsc = smsc;
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

    public String getDelivInfo() {
        return delivInfo;
    }

    public void setDelivInfo(String delivInfo) {
        this.delivInfo = delivInfo;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMsgid() {
        return msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
}
