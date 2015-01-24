package com.ecomm.pl4sms.ws.json.outputs;


public class CreateBatchReturnType extends JSON_Output_BaseClass{

    private String batchIdentifier;

    public String getBatchIdentifier() {
        return batchIdentifier;
    }

    public void setBatchIdentifier(String batchIdentifier) {
        this.batchIdentifier = batchIdentifier;
    }
}
