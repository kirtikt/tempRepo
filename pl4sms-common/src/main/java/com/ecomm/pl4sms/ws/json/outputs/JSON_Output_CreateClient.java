package com.ecomm.pl4sms.ws.json.outputs;


public class JSON_Output_CreateClient extends JSON_Output_BaseClass{

    private String clientName;
    private String clientIdentifier;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}
