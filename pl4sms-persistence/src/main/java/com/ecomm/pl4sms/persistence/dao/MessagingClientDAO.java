package com.ecomm.pl4sms.persistence.dao;

import com.ecomm.pl4sms.persistence.entities.MessagingClient;


import javax.persistence.Query;
import java.util.UUID;

public class MessagingClientDAO extends PL4BaseDAO {

    public MessagingClient findById(String id) {

        Query query = getEm().createNamedQuery("MessagingClient.findById");
        query.setParameter("id", id);

        return (MessagingClient) query.getResultList().get(0);
    }

    public MessagingClient findByIdentifier(String clientIdentifier) {
        Query query = getEm().createNamedQuery("MessagingClient.findByIdentifier");
        query.setParameter("clientIdentifier", clientIdentifier);

        MessagingClient messagingClient = (MessagingClient) query.getResultList().get(0);

        return messagingClient;
    }

    public MessagingClient createClient(String clientName) {

        MessagingClient messagingClient = new MessagingClient();
        messagingClient.setClientName(clientName);
        messagingClient.setClientIdentifier(UUID.randomUUID());

        getEm().persist(messagingClient);

        return messagingClient;
    }

}
