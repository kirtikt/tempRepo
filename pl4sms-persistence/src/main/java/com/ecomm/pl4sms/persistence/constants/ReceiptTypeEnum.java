package com.ecomm.pl4sms.persistence.constants;

public enum ReceiptTypeEnum {

ACK( "ACK"), SENT ("SENT");

private String label;

ReceiptTypeEnum(String label) {
this.label = label;
}



}