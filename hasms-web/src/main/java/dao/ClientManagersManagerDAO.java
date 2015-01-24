package main.java.dao;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Role;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sysuser;


public class ClientManagersManagerDAO extends GenericHibernateDAO<Clientmanager,Long>{

	Logger log=LoggerFactory.getLogger(ClientManagersManagerDAO.class);
	
	
	
	
	
	
	public long getclientManagerId(long userId)
	{
		long clientManagerId = 0;
		 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session=sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//log.info("class:::"+getClass());
			
			
			String hql = "from Clientmanager where fkuserid ="+userId;
		    Query query = session.createQuery(hql);
		        List clientmanagerlist = query.list();
					  for(Iterator it = clientmanagerlist.iterator();it.hasNext();)
					  {
						  Clientmanager clientmanager = (Clientmanager) it.next();
						//  log.info("ID: " + clientmanager.getPkclientmanagerid());
						  clientManagerId=clientmanager.getPkclientmanagerid();
					  }
			transaction.commit();
			
			/*closeSession();*/
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
		return clientManagerId;
	}
	
	public Clientmanager getclientManager(long userId)
	{ SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session=sessionFactory.openSession();
		long clientManagerId = 0;
		Transaction transaction = null;
		 Clientmanager clientmanager = null;
		try {
			transaction = session.beginTransaction();
			//log.info("class:::"+getClass());
			String hql = "from Clientmanager where fkuserid ="+userId;
		    Query query = session.createQuery(hql);
		        List clientmanagerlist = query.list();
					  for(Iterator it = clientmanagerlist.iterator();it.hasNext();)
					  {
						  clientmanager = (Clientmanager) it.next();
						  //log.info("ID: " + clientmanager.getPkclientmanagerid());
						  clientManagerId=clientmanager.getPkclientmanagerid();
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
	
	public Clientmanager getAdmin()
	{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session=sessionFactory.openSession();
		Transaction transaction = null;
		Role role=null;
		Clientmanager clientman=null;
		long sysuserid=0;
		 Clientmanager clientManager=null;
		try {
			transaction = session.beginTransaction();
			//log.info("class:::"+getClass());
			Criteria crt=session.createCriteria(Role.class);
			List list=crt.add(Restrictions.eq("rolename","sysadmin")).list();
			if(list!=null){
				role=(Role) list.get(0);
			}
			long roleid=role.getPkroleid();
			//log.info("{}",roleid);
			String hql = "from Sysuser where fkroleid ="+roleid;
		    Query query = session.createQuery(hql);
		        List sysuserlist = query.list();
					
					  for(Iterator it = sysuserlist.iterator();it.hasNext();)
					  {
						  Sysuser sysuser = (Sysuser) it.next();
						 // log.info("userid: " + sysuser.getPkuserid());
						  sysuserid=sysuser.getPkuserid();
					  }
					 // log.info("sysuserid:::"+sysuserid);
						String hql1 = "from Clientmanager where fkuserid ="+sysuserid;
					    Query query1 = session.createQuery(hql1);
					        List clientmanagerlist = query1.list();
					     //   log.info("list size ::"+clientmanagerlist.size());
					        long clientManaId=0l;
								  for(Iterator it = clientmanagerlist.iterator();it.hasNext();)
								  {
									  clientman = (Clientmanager) it.next();
									 // log.info("client manager::"+clientman);
									//  log.info("ID: " + clientman.getPkclientmanagerid());
									  clientManaId=clientman.getPkclientmanagerid();
								  }
				  transaction.commit();
//					  clientManager=getclientManager(sysuserid,session);
			
			/*session.flush();
			closeSession();*/
		} catch (HibernateException e) {
			log.error(e.toString());
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.flush();
			session.close();
			session = null;
			transaction = null;
		}
		return clientman;
	}
	
	 public List getList() {
	        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	          Session session = sessionFactory.openSession();

	             Transaction transaction = null;
	             List<Clientmanager> entity=null;
	             try {
	                
	                     Criteria crit = session.createCriteria(Clientmanager.class);
	                     crit.add(Restrictions.eq("isdeleted",false));
	                     entity= crit.list();
	                    
	             } catch (HibernateException e) {
	     			log.error(e.toString());
	    			e.printStackTrace();
	    			transaction.rollback();
	             } finally {
	            	 
	     			session.close();
	     			session = null;
	     			transaction = null;
	             }
	             return entity;
	     }
}
