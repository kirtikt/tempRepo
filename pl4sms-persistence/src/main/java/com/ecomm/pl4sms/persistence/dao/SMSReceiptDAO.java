package com.ecomm.pl4sms.persistence.dao;


import com.ecomm.pl4sms.common.dto.SMSReceiptDTO;
import com.ecomm.pl4sms.persistence.constants.ReceiptTypeEnum;
import com.ecomm.pl4sms.persistence.entities.PL4Base;
import com.ecomm.pl4sms.persistence.entities.SMSReceipt;

public class SMSReceiptDAO extends PL4BaseDAO{


    public void persistSMSReceipt(SMSReceiptDTO receiptDTO) {
        SMSReceipt smsReceiptEntity = new SMSReceipt();

        if ( receiptDTO.getDeliveryInfo().contains("ACK")) {

            smsReceiptEntity.setReceiptType(ReceiptTypeEnum.ACK);
            smsReceiptEntity.setDeliveryInfo(receiptDTO.getDeliveryInfo());
        }
        smsReceiptEntity.setAccountInfo(receiptDTO.getAccountInfo());
        smsReceiptEntity.setCharge(receiptDTO.getCharge());
        smsReceiptEntity.setSmsId(receiptDTO.getSmsId());
        smsReceiptEntity.setDestination(receiptDTO.getDestination());
        smsReceiptEntity.setMessageId(receiptDTO.getMessageId());
        smsReceiptEntity.setSource(receiptDTO.getSource());
        smsReceiptEntity.setTimeDate(receiptDTO.getTimeDate());
        smsReceiptEntity.setSmsCentre(receiptDTO.getSmsCentre());
        smsReceiptEntity.setStatus(receiptDTO.getStatus());

        getEm().persist(smsReceiptEntity);

    }
}
