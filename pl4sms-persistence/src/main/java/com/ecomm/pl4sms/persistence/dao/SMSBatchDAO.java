package com.ecomm.pl4sms.persistence.dao;

import com.ecomm.pl4sms.persistence.dbutils.BatchStatus;
import com.ecomm.pl4sms.persistence.entities.SMSBatch;

import javax.persistence.Query;
import java.util.Date;
import java.util.UUID;

public class SMSBatchDAO extends PL4BaseDAO {


    public SMSBatch findByIdentifier(String identifier) {

        Query query = getEm().createNamedQuery("SMSBatch.findByIdentifier");
        query.setParameter("batchIdentifier", identifier);

        return (SMSBatch) query.getResultList().get(0);
    }


    public SMSBatch createNewBatch(String description) {

        SMSBatch smsBatch = new SMSBatch();
        smsBatch.setBatchIdentifier(UUID.randomUUID());
        smsBatch.setBatchCreateDate(new Date());
        smsBatch.setBatchStatus(BatchStatus.CREATED);

        getEm().persist(smsBatch);

        return smsBatch;
    }
}
