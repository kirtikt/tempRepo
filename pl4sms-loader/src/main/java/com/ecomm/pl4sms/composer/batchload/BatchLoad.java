package com.ecomm.pl4sms.composer.batchload;


import com.ecomm.pl4sms.common.exception.NoClientFoundException;
import com.ecomm.pl4sms.common.SMSQueueMessageDTO;

import com.ecomm.pl4sms.persistence.dao.MessagingClientDAO;
import com.ecomm.pl4sms.persistence.dao.SMSBatchDAO;
import com.ecomm.pl4sms.persistence.entities.MessagingClient;
import com.ecomm.pl4sms.persistence.entities.SMSBatch;
import org.apache.log4j.Logger;

import java.util.List;

public class BatchLoad {

    private static Logger LOG = Logger.getLogger(BatchLoad.class);

    public SMSBatch createBatchForClient( String batchDescription, String messagingClientIdentifier) throws NoClientFoundException {

        MessagingClient messagingClient = new MessagingClientDAO().findByIdentifier(messagingClientIdentifier);

        if ( messagingClient == null ) {
            throw new NoClientFoundException("Client with identifier [" + messagingClientIdentifier + "] not found.");
        }

        SMSBatch smsBatch = new SMSBatchDAO().createNewBatch(batchDescription);

        LOG.debug( "SMS Batch with identifier [" + smsBatch.getBatchIdentifier() + "] created.");

        return smsBatch;

    }

    public MessagingClient createClient( String clientName ) {

        return new MessagingClientDAO().createClient(clientName);


    }

    public void addMessagesToBatch(List<SMSQueueMessageDTO> smsMessage, String messagingClientIdentifier) {

        // Check if client exists



       // new SMSBatchDAO().createNewBatch();
    }
}
