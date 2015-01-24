package main.java.dao;


import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Startnumber;


public class MNOManagerDAO extends GenericHibernateDAO<Mno,Long>{
	
	Logger log=LoggerFactory.getLogger(MNOManagerDAO.class);
	public String getMnoName(String mobileNumber) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Startnumber> smslist = null;
		String mnoName = null;
		String sourcenumber=null;
		try {
			transaction = session.beginTransaction();
			String SQL_QUERY = "from Startnumber";
			Query query = session.createQuery(SQL_QUERY);
			smslist = query.list();
			Iterator<Startnumber> It = smslist.iterator();
			//log.info("mobileNumber  "+mobileNumber);
			while (It.hasNext()) {
				Startnumber sobj = (Startnumber) It.next();
				sourcenumber=sobj.getNumber();
				//log.info("sourcenumber  "+sourcenumber);
				//log.info(sobj.getPkstartnumberid() + "      "+ sobj.getNumber() + "       " + sobj.getMno().getSourcenumber());
				if (mobileNumber.startsWith(sourcenumber.trim())) {
				//	log.info("Inside if block mobileNumber.startsWith(sobj.getStrtnumber()");
					mnoName = sobj.getMno().getName();
					//log.info("Source :" + mnoName);
					//log.info("Hit the brakes !!!");
					break;
				}
			}
			//log.info("Source Number found : " + mnoName);
			transaction.commit();
			
		} catch (HibernateException e) {
			if(transaction!=null){
			transaction.rollback();}
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
			transaction = null;
		}
		return mnoName;
	}

	
	
}
