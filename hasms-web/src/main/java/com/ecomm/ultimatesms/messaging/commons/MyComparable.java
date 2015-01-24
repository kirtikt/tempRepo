package com.ecomm.ultimatesms.messaging.commons;

import java.util.Comparator;

import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;

public class MyComparable implements Comparator<Holdingaccount>{
	 
    @Override
    public int compare(Holdingaccount o1, Holdingaccount o2) {
        return ((o1.getPkeyholdingaccountid()>o2.getPkeyholdingaccountid()) ? -1 : ((o1.getPkeyholdingaccountid()>o2.getPkeyholdingaccountid()) ? 0 : 1));
    }
}
