package com.ecomm.ultimatesms.messaging.commons;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Startnumber;

/*
 For Vodacom correct cell numbers start with 082,072,076,079,0711,0712,0713,0714, 0715, 0716 or 2782, 2772, 2776,2779, 27711,27712,27713,27714, 27715, 27716  or  +2782,+2772,+2776,+2779, +27711,+27712,+27713,+27714, +27715, +27716
 For MTN they start with:  083,073,078, 0710, 0717, 0718, 0719 or 2783,2773,2778, 27710, 27717, 27718, 27719 or +2783,+2773,+2778, +27710, +27717, +27718, +27719
 For CellC they start with: 084,074 or 2784,2774 or +2784,+2774

 mtn      2783930026100547
 vodacom  2782004871000715
 cellc    2784000123900011
 */
public class Commons {
	org.slf4j.Logger log = LoggerFactory.getLogger(Commons.class);
	public String searchForSender(String mobileNumber) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		Transaction transaction = null;
		List<Startnumber> smslist = null;
		String source = "";
		Session session =null;
		String sourcenumber=null;
		try {
			 session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria crt=session.createCriteria(Startnumber.class);
			//String SQL_QUERY = "from Startnumber";
			//Query query = session.createQuery(SQL_QUERY);
			smslist = crt.list();
			Iterator<Startnumber> It = smslist.iterator();
		
			while (It.hasNext()) {
				Startnumber sobj = (Startnumber) It.next();
				sourcenumber=sobj.getNumber();
				//log.info("sourcenumber  "+sourcenumber);
				//log.info(sobj.getPkstartnumberid() + "      "+ sobj.getNumber() + "       " + sobj.getMno().getSourcenumber());
				if (mobileNumber.startsWith(sourcenumber.trim())) {
					//log.info("Inside if block mobileNumber.startsWith(sobj.getStrtnumber()");
					source = sobj.getMno().getSourcenumber().toString();
				//	log.info("Source :" + source);
				//	log.info("Hit the brakes !!!");
					break;
				}
			}
//			log.info("Source Number found : " + source);
//			log.info(" transaction: " + transaction);
			transaction.commit();
			
			session.flush();
			
		} catch (HibernateException e) {
			
			if(transaction!=null){	
			transaction.rollback();}
			
			e.printStackTrace();
		} finally {
			if(session!=null){
			session.close();
			}
			session = null;
			transaction = null;
		}
		return source;
	}
	
	public Mno searchForMno(String mobileNumber) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		
		Transaction transaction = null;
		List<Startnumber> smslist = null;
		Mno mno = null;
		Session session =null;
		String sourcenumber=null;
		try {
			 session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Criteria crt=session.createCriteria(Startnumber.class);
			smslist = crt.list();
			Iterator<Startnumber> It = smslist.iterator();
		
			while (It.hasNext()) {
				Startnumber sobj = (Startnumber) It.next();
				sourcenumber=sobj.getNumber();
				if (mobileNumber.startsWith(sourcenumber.trim())) {
					
					mno = sobj.getMno();
				//	log.info("Source :" + source);
				//	log.info("Hit the brakes !!!");
					break;
				}
			}
//			log.info("Source Number found : " + source);
//			log.info(" transaction: " + transaction);
			transaction.commit();
			
			session.flush();
			
		} catch (HibernateException e) {
			
			if(transaction!=null){	
			transaction.rollback();}
			
			e.printStackTrace();
		} finally {
			if(session!=null){
			session.close();
			}
			session = null;
			transaction = null;
		}
		return mno;
	}

	
	public boolean isVodacomNumber(String mobileNumber) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Startnumber> smslist = null;
		String name = "";
		String sourcenumber=null;
		boolean isVodacomNumber=false;
		try {
			transaction = session.beginTransaction();
			Criteria crt=session.createCriteria(Startnumber.class);
			//String SQL_QUERY = "from Startnumber";
			//Query query = session.createQuery(SQL_QUERY);
			smslist = crt.list();
			Iterator<Startnumber> It = smslist.iterator();
			
			while (It.hasNext()) {
				Startnumber sobj = (Startnumber) It.next();
				sourcenumber=sobj.getNumber();
				
				if(sobj.getMno()!=null){
				if (mobileNumber.startsWith(sourcenumber.trim())) {
					name = sobj.getMno().getName();
					if("vodacom".equalsIgnoreCase(name.trim())){
						isVodacomNumber=true;
					}
					
					break;
				}
				}
			}
			log.info("Source Number found : " + name);
			transaction.commit();
		} catch (HibernateException e) {
			if(transaction!=null){
			transaction.rollback();}
			e.printStackTrace();
		} finally {
			if(session!=null){
			session.close();}
			session = null;
			transaction = null;
		}
		return isVodacomNumber;
	}
	
	
	
	
	
}
