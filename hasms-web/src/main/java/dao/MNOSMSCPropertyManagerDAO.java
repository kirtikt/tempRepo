package main.java.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnosmscproperty;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smscproxyproperty;


public class MNOSMSCPropertyManagerDAO extends GenericHibernateDAO<Mnosmscproperty,Long>{

	public Mnosmscproperty getMnosmscproperty(Mno mno,Smscproxyproperty spp){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Mnosmscproperty msp = null;
		try {
			//transaction = session.beginTransaction();
			Smscproxyproperty spp1=(Smscproxyproperty)	session.get(Smscproxyproperty.class, spp.getPksmscpropid());
			Sendinginterface si=spp1.getSendinginterfaces().iterator().next();
			
			Criteria crt = session.createCriteria(Mnosmscproperty.class);
			crt.add(Restrictions.eq("mno", mno));
			crt.add(Restrictions.eq("sendinginterface", si));
			
			List<Mnosmscproperty> listSms = crt.list();
			if (listSms != null && listSms.size() != 0) {
				msp = listSms.get(0);
			}
		//	transaction.commit();
		} catch (HibernateException e) {
			
			e.printStackTrace();
		} finally {

			transaction = null;
			session.flush();
			session.close();
			session = null;
		}
		return msp;
	}

	public Mnosmscproperty getMnoSmscProperty(Mno mno,Sendinginterface si){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Mnosmscproperty msp=null;
			long clientManagerId = 0;
			Transaction transaction = null;
			 Clientmanager clientmanager = null;
			try {
				Criteria crt=session.createCriteria(Mnosmscproperty.class);
				crt.add(Restrictions.eq("mno", mno));
				crt.add(Restrictions.eq("sendinginterface", si));
				crt.setMaxResults(1);
				List list=crt.list();
			     if(list!=null && list.size()!=0 ){
			    	 msp=(Mnosmscproperty)list.get(0);
			     }
			} catch (HibernateException e) {
				e.printStackTrace();
			} finally {
				session.flush();
				session.close();
				session = null;
				transaction = null;
			}
		return msp;
	}
}
