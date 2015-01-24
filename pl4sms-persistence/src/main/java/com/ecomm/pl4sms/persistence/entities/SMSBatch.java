package com.ecomm.pl4sms.persistence.entities;

import com.ecomm.pl4sms.persistence.dbutils.BatchStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NamedQueries ( {
  @NamedQuery(name="SMSBatch.findByIdentifier", query="select o from SMSBatch o where o.batchIdentifier=:batchIdentifier")

})
@Entity
@Table(name="\"SMS_BATCH\"")
public class SMSBatch extends PL4Base {


    @Column(name="\"BATCH_IDENTIFIER\"")
    private UUID batchIdentifier;

    @Column(name="\"BATCH_STATUS\"")
    private BatchStatus batchStatus;

    @Column(name="\"BATCH_CREATE_DATE\"")
    private Date batchCreateDate;

    @Column(name="\"BATCH_COMPLETE_DATE\"")
    private Date batchCompleteDate;

    @ManyToOne
    @PrimaryKeyJoinColumn
    private MessagingClient messagingClient;

    @OneToMany(mappedBy="messageBatchId")
    private Set<PL4_SMSMessage> smsMessages = new HashSet<PL4_SMSMessage>(0);


    public UUID getBatchIdentifier() {
        return batchIdentifier;
    }

    public void setBatchIdentifier(UUID batchIdentifier) {
        this.batchIdentifier = batchIdentifier;
    }

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Date getBatchCreateDate() {
        return batchCreateDate;
    }

    public void setBatchCreateDate(Date batchCreateDate) {
        this.batchCreateDate = batchCreateDate;
    }

    public Date getBatchCompleteDate() {
        return batchCompleteDate;
    }

    public void setBatchCompleteDate(Date batchCompleteDate) {
        this.batchCompleteDate = batchCompleteDate;
    }

    public Set<PL4_SMSMessage> getSmsMessages() {
        return smsMessages;
    }

    public void setSmsMessages(Set<PL4_SMSMessage> smsMessages) {
        this.smsMessages = smsMessages;
    }
}
