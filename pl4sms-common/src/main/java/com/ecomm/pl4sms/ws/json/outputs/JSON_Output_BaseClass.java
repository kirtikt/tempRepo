package com.ecomm.pl4sms.ws.json.outputs;

import java.io.Serializable;

public class JSON_Output_BaseClass implements Serializable {

    private String status;

    private String statusMessage;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
