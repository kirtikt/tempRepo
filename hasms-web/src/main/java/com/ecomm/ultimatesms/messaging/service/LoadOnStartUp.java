package com.ecomm.ultimatesms.messaging.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;




/**
 * Servlet implementation class CreditPerClient
 */
public class LoadOnStartUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadOnStartUp() {
        super();
        // TODO Auto-generated constructor stub
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		Logger log=LoggerFactory.getLogger(SendMultipleMessage.class);
		super.init();
		/*
		 * This will run the thread. and thread will check any future sms loaded in db.
		 *  if so, then it will send that sms to kannel.
		 */
		
		FutureSms sms=new FutureSms();
	}

}
