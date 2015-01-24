package main.java.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;


public abstract class GenericHibernateDAO<T,ID extends Serializable> implements GenericDAO<T,ID>{

	 private Class<T> persistentClass;
     
     @SuppressWarnings("unchecked")
     public GenericHibernateDAO(){
             this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
           
     }
     public Class<T> getPersistentClass() {
             return persistentClass;
     }
     

     public GenericHibernateDAO(Class<T> persistentClass ){
             this.persistentClass=persistentClass;
     }
     
     public void add(T obj) {
             SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session=sessionFactory.openSession();
             Transaction transaction = null;
             try {
                     transaction = session.beginTransaction();
                     session.save(obj);
                     transaction.commit();
                     session.flush();
                  
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
     }
//     public void add(T obj,Session session) {
//         
//         Transaction transaction = null;
//         try {
//                 transaction = session.beginTransaction();
//                 System.out.println("class:::"+getClass());
//                 session.save(obj);
//                 transaction.commit();
//         } catch (HibernateException e) {
//        	 if(transaction!=null){
//                 transaction.rollback();}
//                 e.printStackTrace();
//         } finally {
//                
//                 transaction = null;
//         }
// }

     public void delete(long pKey) {
             SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session=sessionFactory.openSession();
             Transaction transaction = null;
             try {
                     transaction = session.beginTransaction();
                     T obj=(T)session.load(getPersistentClass(), pKey);
                     session.delete(obj);
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
     }

     public T findById(long id,Session sess) {
             T entity=null;
             try {
                   
                     entity=(T)sess.get(getPersistentClass(), id);
                            
                     
             } catch (HibernateException e) {
            
                     e.printStackTrace();
             } finally {
            
             }
             return entity;
     }
     
     public T findById(long id) {
        
    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session=sessionFactory.openSession();
       
    	 T entity=null;
         try {
                // it must get not load 
                 entity=(T)session.get(getPersistentClass(), id);
                 
         } catch (HibernateException e) {
                 e.printStackTrace();
         } finally {
        	 
       	  session.flush();
             session.close();
                session = null;
         }
         return entity;
 }

     public List<T> getList() {
    	 SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

         Session session=sessionFactory.openSession();
    	 Transaction transaction=null;
             List<T> entity=null;
             try {
            	 transaction= session.beginTransaction();
                 Criteria crit = session.createCriteria(getPersistentClass());
                 entity= crit.list();
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
             return entity;
     }

     
     
     
     public void update(T obj) {
             SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
             Session session=sessionFactory.openSession();
             
             Transaction transaction = null;
             try {
                     transaction = session.beginTransaction();
                     session.saveOrUpdate(obj);
                     transaction.commit();

                    
             } catch (HibernateException e) {
            	 if(transaction!=null){
                     transaction.rollback();}
                     e.printStackTrace();
             } finally {
             //        session.close();
            	 
            	  session.flush();
                  session.close();
                     session = null;
                     transaction = null;
             }
     }
     
}