package main.java.dao;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;

public class SysUserManagerDAO extends GenericHibernateDAO<Sysuser,Long>{

	Logger log=LoggerFactory.getLogger(SysUserManagerDAO.class);
	
	public Clientmanager getClientmanagerByUnamePassClRef(String username,String password,String clientref) {
		 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
			Sysuser sysuser=null;
	
			Clientmanager clientmanager=null;
			Transaction transaction = null;
			try {
				transaction = session.beginTransaction();
			
			String hql = "from Sysuser where username = :username and password = :password and clientref = :clientref";
		    Query query = session.createQuery(hql);
		    query.setParameter("username",username);
		    query.setParameter("password",password);
		    query.setParameter("clientref",clientref);
		    if(query.list().size()>0){
		    	sysuser= (Sysuser)query.list().get(0);
		    	Set<Clientmanager> set=sysuser.getClientmanagers();
		    	Iterator<Clientmanager> it =set.iterator();
				Clientmanager  clientmanager1=null;
		        if (it.hasNext()) {
		        	clientmanager=it.next();
		        } 
		    	
		    }
			transaction.commit();
			
			} catch (HibernateException e) {
				log.error(e.toString());
				if(transaction!=null){
				transaction.rollback();}
			} finally {
				session.flush();
				session.close();
				session = null;
				transaction = null;
			}
		return clientmanager;
	}
	
	
}
