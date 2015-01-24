package com.ecomm.pl4sms.persistence.entities;

import javax.persistence.*;

@Entity
@Table(name="\"SMS_MESSAGE\"")
public class PL4_SMSMessage extends PL4Base{

    @Column(name = "\"MESSAGE_TEXT\"")
    private String messageText;

    @Column(name = "\"MESSAGE_RECIPIENT\"")
    private String messageRecipient;

    @ManyToOne()
    @JoinColumn(name = "\"MESSAGE_BATCH_ID\"", nullable = false)
    private SMSBatch messageBatchId;

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageRecipient() {
        return messageRecipient;
    }

    public void setMessageRecipient(String messageRecipient) {
        this.messageRecipient = messageRecipient;
    }

    public SMSBatch getMessageBatchId() {
        return messageBatchId;
    }

    public void setMessageBatchId(SMSBatch messageBatchId) {
        this.messageBatchId = messageBatchId;
    }
}
