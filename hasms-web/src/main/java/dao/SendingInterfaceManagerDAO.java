package main.java.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;

public class SendingInterfaceManagerDAO extends GenericHibernateDAO<Sendinginterface, Long> {

	public List<Sendinginterface> getSendingInterface()
	{		 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session=sessionFactory.openSession();
		Transaction transaction=null;
		transaction =session.beginTransaction();
		List<Sendinginterface> interfacelist=session.createCriteria(Sendinginterface.class).list();
		if(transaction!=null){
		transaction.commit();
		
		}
		session.flush();
		session.close();
		session = null;
		transaction = null;
		
		
		return interfacelist;
		
	}
}