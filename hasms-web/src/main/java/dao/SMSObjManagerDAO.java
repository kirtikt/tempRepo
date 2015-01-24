package main.java.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Smsobj;


public class SMSObjManagerDAO extends GenericHibernateDAO<Smsobj,Long>{

	public List getSmsObjectList(String date){
		SessionFactory sessionFactory=null;
		Session session=null;
		List list=null;
		try{
			sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Criteria crt=session.createCriteria(Smsobj.class);
		//	crt.add(Restrictions.between("futuredate", new Date(from),new Date(to)));
			crt.add(Restrictions.le("futuredate", new Date(date.trim())));
			crt.add(Restrictions.eq("status", "l"));
			list=crt.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			session=null;
		}
		return list;
	}
	
	public List getFutureSmsList(long clientmanagerid){
		SessionFactory sessionFactory=null;
		Session session=null;
		List<Smsobj> list=null;
		try{
			sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Criteria crt=session.createCriteria(Smsobj.class);
		//	crt.add(Restrictions.between("futuredate", new Date(from),new Date(to)));
			crt.add(Restrictions.eq("clientmanager", clientmanagerid));
			crt.add(Restrictions.eq("status", "l"));
			list=crt.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			session=null;
		}
		return list;
	}
	
	
	
	public List getSmsObjectList123(String from,String to){
		SessionFactory sessionFactory=null;
		Session session=null;
		List list=null;
		try{
			sessionFactory=HibernateUtil.getSessionFactory();
			session=sessionFactory.openSession();
			Criteria crt=session.createCriteria(Holdingaccount.class);
			crt.add(Restrictions.le("timedate", new Date("05/11/2011")));
			//crt.add(Restrictions.between("futuredate", new Date(from),new Date(to)));
			//crt.add(Restrictions.eq("status", "l"));
			list=crt.list();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
			session=null;
		}
		
		return list;
	}
	
}
