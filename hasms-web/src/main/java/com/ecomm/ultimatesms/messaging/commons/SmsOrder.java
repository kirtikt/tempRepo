package com.ecomm.ultimatesms.messaging.commons;

import java.util.Comparator;


import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

public class SmsOrder implements Comparator<Sms>{
	 
    @Override
    public int compare(Sms o1, Sms o2) {
        return ((o1.getPksmsid()<o2.getPksmsid()) ? -1 : ((o1.getPksmsid()<o2.getPksmsid()) ? 0 : 1));
    }
}