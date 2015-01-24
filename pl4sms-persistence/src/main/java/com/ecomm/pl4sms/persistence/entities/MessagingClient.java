package com.ecomm.pl4sms.persistence.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@NamedQueries ( {
        @NamedQuery(name="MessagingClient.findById", query="select o from MessagingClient o where o.id=:id"),
        @NamedQuery(name="MessagingClient.findByIdentifier", query="select o from MessagingClient o where o.clientIdentifier=:clientIdentifier")
})
@Entity
@Table(name="\"MESSAGING_CLIENT\"")
public class MessagingClient extends PL4Base {


    @Column(name="\"CLIENT_NAME\"")
    private String clientName;

    @Column(name="\"CLIENT_IDENTIFIER\"")
    private UUID clientIdentifier;

    @OneToMany(mappedBy="messagingClient")
    private Set<SMSBatch> smsBatchSet = new HashSet<SMSBatch>();


    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }


    public UUID getClientIdentifier() {
        return clientIdentifier;
    }

    public void setClientIdentifier(UUID clientIdentifier) {
        this.clientIdentifier = clientIdentifier;
    }

    public Set<SMSBatch> getSmsBatchSet() {
        return smsBatchSet;
    }

    public void setSmsBatchSet(Set<SMSBatch> smsBatchSet) {
        this.smsBatchSet = smsBatchSet;
    }
}
