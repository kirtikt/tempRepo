package com.ecomm.pl4sms.persistence.dao;


import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class PL4BaseDAO {


    private static Logger LOG= Logger.getLogger(PL4BaseDAO.class);
    @PersistenceContext(unitName="pl4sms-persistence")
    private EntityManager em;


    public EntityManager getEm() {

        if (em == null) {
            LOG.debug( "EM is NULL!!!");
        }
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
