package main.java.unentity;

import javax.persistence.Column;
import javax.persistence.Entity;


public class SMS_Status extends PL4Base {

    @Column(name="SMS_MESSAGE")
    private PL4_SMSMessage smsMessage;
}
