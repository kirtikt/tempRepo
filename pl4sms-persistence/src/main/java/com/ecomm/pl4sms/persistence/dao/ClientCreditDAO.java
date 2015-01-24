package com.ecomm.pl4sms.persistence.dao;


import com.ecomm.pl4sms.persistence.entities.ClientCredit;
import com.ecomm.pl4sms.persistence.entities.SMSBatch;

import javax.persistence.Query;

public class ClientCreditDAO extends PL4BaseDAO {

    public ClientCredit findById(String id) {

        Query query = getEm().createNamedQuery("ClientCredit.findById");
        query.setParameter("identifier", id);

        return (ClientCredit) query.getResultList().get(0);
    }
}
