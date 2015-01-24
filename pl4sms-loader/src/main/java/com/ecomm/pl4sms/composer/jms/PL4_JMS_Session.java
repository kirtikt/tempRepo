package com.ecomm.pl4sms.composer.jms;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Session;

public class PL4_JMS_Session {

    private Session jmsSession;

    private Queue jmsQueue;

    private Destination destination;


    public PL4_JMS_Session(Session jmsSession, Queue jmsQueue) {
        this.jmsSession = jmsSession;
        this.jmsQueue = jmsQueue;
    }

    public Session getJmsSession() {
        return jmsSession;
    }

    public void setJmsSession(Session jmsSession) {
        this.jmsSession = jmsSession;
    }

    public Queue getJmsQueue() {
        return jmsQueue;
    }

    public void setJmsQueue(Queue jmsQueue) {
        this.jmsQueue = jmsQueue;
    }
}
