package com.ecomm.pl4sms.common.constants;


public enum KannelDeliveryStatus {

     DELIVERY_SUCCESS("1"),
     DELIVERY_FAILURE("2"),
     MESSAGE_BUFFERED("4"),
     SMSC_SUBMIT("8"),
     SMSC_REJECT("16"),
     SMSC_INTERMEDIATE("32");

    private String token;

    KannelDeliveryStatus(String token) {
        this.token = token;
    }
}
