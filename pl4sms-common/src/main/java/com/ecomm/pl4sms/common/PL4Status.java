package com.ecomm.pl4sms.common;


public enum PL4Status {

    SUCCESS("Success"), ERROR("Error");

    private String statusMessage;

    PL4Status(String statusMessage) {
        this.statusMessage = statusMessage;

    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
