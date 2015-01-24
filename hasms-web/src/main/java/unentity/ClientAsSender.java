package main.java.unentity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


public class ClientAsSender extends PL4Base {

    @Column(name="CLIENT_ID")
    private String clientId;

    @Column(name="CLIENT_NAME")
    private String clientName;

    @OneToOne
    @JoinColumn(name="CLIENT_CREDITS")
    private ClientAsSenderCredits clientCredits;

    @OneToMany
    @JoinColumn(referencedColumnName = "SENDING_CLIENT")
    private Set<SMSBatch> smsBatchSet = new HashSet<SMSBatch>();


}
