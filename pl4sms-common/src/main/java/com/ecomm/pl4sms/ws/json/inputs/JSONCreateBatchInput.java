package com.ecomm.pl4sms.ws.json.inputs;


public class JSONCreateBatchInput extends JSONInputBaseClass {

    private String batchDescription;
    private String clientIdentifier;


    public String getBatchDescription() {
        return batchDescription;
    }

    public void setBatchDescription(String batchDescription) {
        this.batchDescription = batchDescription;
    }


    public String getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(String clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }
}
