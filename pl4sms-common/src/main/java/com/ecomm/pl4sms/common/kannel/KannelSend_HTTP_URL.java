package com.ecomm.pl4sms.common.kannel;

import com.ecomm.pl4sms.ws.DlrUrlDTO;

public class KannelSend_HTTP_URL {

    private String serverName;
    private String portNumber;
    private String urlContext;

    private String username;
    private String password;

    private String targetMSDN;
    private String messageText;

    private String from;
    private String smsCentre;

    private String dlrMask="31";

    private DlrUrlDTO dlrUrlDTO;


    public String toString() {

        StringBuffer toString = new StringBuffer("http://")
        .append(getServerName()).append(":").append(getPortNumber()).append("/").append( getUrlContext() )
        .append("?username=").append(getUsername())
        .append("&password=").append(getPassword())
                .append("&to=").append(getTargetMSDN())
                .append("&text=").append(getMessageText())
                .append("&from=").append(getFrom())
                .append("&smsc=").append(getSmsCentre());


        toString.append("&dlr-mask=").append(getDlrMask());

        toString.append("&dlr-url=").append(buildDLRURL());
        return toString.toString();

    }


    private String buildDLRURL() {

        //&dlr-url=http%3A%2F%2F41.185.26.92%3A8080%2Freceipts-1.0-SNAPSHOT%2FSMSReceiptServlet%3FsmsId%3D1%26status%3D%25d%26smsc%3D%25i%26timeDate%3D%25t%26destination%3D%25p%26delivInfo%3D%25a%26source%3D%25P%26msgid%3D%25k%26charge%3D%25B%26accountInfo%3D%25o

        StringBuffer dlrURL = new StringBuffer("&dlr-url=");
        dlrURL.append(getServerName()).append(":").append(getPortNumber()).append("/").append(getUrlContext())
        .append("?smsId=").append(getDlrUrlDTO().getSmsId())
        .append("&status=").append(getDlrUrlDTO().getStatus())
                .append("&smsc=").append(getDlrUrlDTO().getSmsc())
                .append("&timeDate=").append(getDlrUrlDTO().getTimeDate())
                .append("&destination=").append(getDlrUrlDTO().getDestination())
                .append("&delivInfo=").append(getDlrUrlDTO().getDelivInfo())
                .append("&source=").append(getDlrUrlDTO().getSource())
                .append("&msgid=").append(getDlrUrlDTO().getMsgid())
                .append("&charge=").append(getDlrUrlDTO().getCharge())
                .append("&accountInfo=").append(getDlrUrlDTO().getAccountInfo());

        return dlrURL.toString();
    }


    public DlrUrlDTO getDlrUrlDTO() {
        if ( dlrUrlDTO == null ) {
            dlrUrlDTO = new DlrUrlDTO();
        }
        return dlrUrlDTO;
    }

    public void setDlrUrlDTO(DlrUrlDTO dlrUrlDTO) {
        this.dlrUrlDTO = dlrUrlDTO;
    }

    public String getDlrMask() {
        return dlrMask;
    }

    public void setDlrMask(String dlrMask) {
        this.dlrMask = dlrMask;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTargetMSDN() {
        return targetMSDN;
    }

    public void setTargetMSDN(String targetMSDN) {
        this.targetMSDN = targetMSDN;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSmsCentre() {
        return smsCentre;
    }

    public void setSmsCentre(String smsCentre) {
        this.smsCentre = smsCentre;
    }
}



