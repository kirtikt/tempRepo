package main.java.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Creditaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.Helper;


public class HoldingAccountManagerDAO extends
		GenericHibernateDAO<Holdingaccount, Long> {

	Logger log = LoggerFactory.getLogger(HoldingAccountManagerDAO.class);

	Properties props;

	public HoldingAccountManagerDAO() {
		try {
			props = Helper.getProps();

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

	public int getSmsSentCount(String dateFrom, String dateTo) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int smssent = 0;
		try {
			transaction = session.beginTransaction();
			log.info("class:::" + getClass());
			Criteria crt = session.createCriteria(Holdingaccount.class);
			String pattern = "MM/dd/yyyy hh:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date dateFrom1 = format.parse(dateFrom);
			Date dateTo1 = format.parse(dateTo);
			List list = crt.add(
					Restrictions.between("timedate", dateFrom1, dateTo1))
					.list();

			Iterator it = list.iterator();

			while (it.hasNext()) {
				Holdingaccount ha = (Holdingaccount) it.next();
				// log.info("Deposit Credit :: "+ ha.getCreditsdeposited());
				smssent = smssent + ha.getSmssentcount();

			}
			// log.info("smssent ::" + smssent);
			transaction.commit();

			session.flush();
			/* closeSession(); */
		} catch (Exception e) {
			log.error(e.toString());
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();

		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return smssent;
	}

	public Map<Integer, Double> getSmsSentCount(String dateFrom, String dateTo,
			long managerid) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int smssent = 0;
		Double credits = 0d;
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		try {
			transaction = session.beginTransaction();
			// log.info("class:::" + getClass());
			// Criteria crt=session.createCriteria(Holdingaccount.class);
			String pattern = "MM/dd/yyyy hh:mm:ss";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			Date dateFrom1 = format.parse(dateFrom);
			Date dateTo1 = format.parse(dateTo);
			// List list=crt.add(Restrictions.between("timedate",dateFrom1
			// ,dateTo1)).list();
			String hql = "from Holdingaccount where fkclientmanagerid ='"
					+ managerid + "' and timedate between '" + dateFrom
					+ "'and '" + dateTo + "'";
			Query query = session.createQuery(hql);
			List list = query.list();
			Iterator it = list.iterator();

			while (it.hasNext()) {
				Holdingaccount ha = (Holdingaccount) it.next();
				// log.info("Deposit Credit :: "+ ha.getCreditsdeposited());
				smssent = smssent + ha.getSmssentcount();
				credits = credits
						+ (ha.getCreditsdeposited() - ha.getCreditsleft());

			}
			map.put(smssent, credits);
			// log.info("smssent ::" + smssent);
			transaction.commit();

			session.flush();
			/* closeSession(); */
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return map;
	}
	
	
	
	
	
	
	
	
	

	public synchronized void giveBackDepositedCredits(long clientmanagerid) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Creditaccount ca = null;
		Double addedCredits = 0d;
		try {

			Clientmanager cm = (Clientmanager) session.get(Clientmanager.class,
					clientmanagerid);
			ca = cm.getCreditaccount();
			// log.info("avaliable credits ::"+cm.getCreditaccount().getAvailablecredit());
			transaction = session.beginTransaction();
			String sql_query = "from Holdingaccount where fkclientmanagerid="
					+ clientmanagerid + " and creditsleft > 0 ";
			Query query = session.createQuery(sql_query);
			List<Holdingaccount> halist = query.list();
			// log.info("list size::"+ halist.size());
			if (halist != null) {
				Iterator it = halist.iterator();
				while (it.hasNext()) {
					Holdingaccount ha1 = (Holdingaccount) it.next();
					if (ha1.getCreditsleft() != null) {
						addedCredits = addedCredits + ha1.getCreditsleft();
					}
					ha1.setCreditsleft(0.0);
					session.update(ha1);
					ha1 = null;
				}
				ca.setAvailablecredit(ca.getAvailablecredit() + addedCredits);
				session.update(ca);
			}
			// log.info("Credits to be added::"+addedCredits);
			transaction.commit();
			session.flush();

		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			session.close();
			session = null;
			transaction = null;
			ca = null;
		}
	}

	// public List getBatches(Long clientid,String from,String to){
	// SessionFactory sessionFactory = null;
	// Session session = null;
	// List list=null;
	// try {
	// sessionFactory = HibernateUtil.getSessionFactory();
	// session = sessionFactory.openSession();
	// Clientmanager cm=(Clientmanager) session.load(Clientmanager.class,
	// clientid);
	// Criteria crt=session.createCriteria(Holdingaccount.class);
	// crt.add(Restrictions.eq("clientmanager", cm));
	// crt.add(Restrictions.between("timedate", new Date(from), new Date(to)));
	// crt.addOrder(Order.desc("timedate"));
	// list= crt.list();
	// } catch (Exception e) {
	// log.error(e.toString());
	// e.printStackTrace();
	// } finally {
	// session.close();
	// session = null;
	// }
	// return list;
	// }

	/*
	 * Function to get batches for a particular client within a specific time
	 * range
	 */
	public List getBatches(Long clientid, String from, String to) {
		SessionFactory sessionFactory = null;
		Session session = null;
		List list = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Clientmanager cm = (Clientmanager) session.load(
					Clientmanager.class, clientid);
			Criteria crt = session.createCriteria(Holdingaccount.class);
			crt.add(Restrictions.eq("clientmanager", cm));
			crt.add(Restrictions.between("timedate", fromDate, toDate));
			crt.addOrder(Order.desc("timedate"));
			list = crt.list();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
		}
		return list;
	}
	public List getBatches(Long clientid) {
		SessionFactory sessionFactory = null;
		Session session = null;
		List list = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Clientmanager cm = (Clientmanager) session.load(
					Clientmanager.class, clientid);
			Criteria crt = session.createCriteria(Holdingaccount.class);
			crt.add(Restrictions.eq("clientmanager", cm));
			crt.addOrder(Order.desc("timedate"));
			crt.setMaxResults(10);
			list = crt.list();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
		}
		return list;
	}

	/*
	 * Function to get data from database for batch report
	 */

	public List<Object> getBatchesforClientManager(Long clientManagerId,
			String from, String to) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List results = null;
		try {
			String hql = "select s.status || ' ' ,h.timedate,count(*) from sms as s JOIN holdingaccount as h on h.pkeyholdingaccountid = s.fkholdingaccountid where h.timedate between '"
					+ fromDate
					+ "' and '"
					+ toDate
					+ "' and s.fkclientmanagerid = '"
					+ clientManagerId
					+ "' group by h.timedate,status";
			Query query = session.createSQLQuery(hql);
			results = query.list();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return results;
	}

	/*
	 * Storing data in maps for displaying in jsp
	 */
	public Map<String, Object> getBatchReportMap(String datetime, Map map) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		if (map.get("00" + datetime) == null) {
			smsMapCount.put("Sms loaded", 0);
		} else {
			smsMapCount.put("Sms loaded", map.get("00" + datetime));
		}
		smsMapCount.put("DateTime", datetime);
		if (map.get("11" + datetime) == null) {
			smsMapCount.put(props.getProperty("11"), 0);
		} else {
			smsMapCount.put(props.getProperty("11"), map.get("11" + datetime));
		}
		if (map.get("m8" + datetime) == null) {
			smsMapCount.put(props.getProperty("m8"), 0);
		} else {
			smsMapCount.put(props.getProperty("m8"), map.get("m8" + datetime));
		}
		if (map.get("m16" + datetime) == null) {
			smsMapCount.put(props.getProperty("m16"), 0);
		} else {
			smsMapCount
					.put(props.getProperty("m16"), map.get("m16" + datetime));
		}
		if (map.get("1" + datetime) == null) {
			smsMapCount.put(props.getProperty("1"), 0);
		} else {
			smsMapCount.put(props.getProperty("1"), map.get("1" + datetime));
		}
		// smsMapCount.put(props.getProperty("m8"), map.get("m8"+datetime));

		if (map.get("2" + datetime) == null) {
			smsMapCount.put(props.getProperty("2"), 0);
		} else {
			smsMapCount.put(props.getProperty("2"), map.get("2" + datetime));
		}

		return smsMapCount;
	}

	/*
	 * Function to get Detail data from database for batch report
	 */
	public List<Object> getDetailedBatchesforClientManager(
			Long clientManagerId, String from, String to) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List results = null;
		try {
			String hql = "select s.status || ' ' ,h.pkeyholdingaccountid,count(*) from sms as s JOIN holdingaccount as h on h.pkeyholdingaccountid = s.fkholdingaccountid where h.timedate between '"
					+ fromDate
					+ "' and '"
					+ toDate
					+ "' and s.fkclientmanagerid = '"
					+ clientManagerId
					+ "' group by h.pkeyholdingaccountid,s.status";
			Query query = session.createSQLQuery(hql);
			results = query.list();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return results;
	}

	/*
	 * Storing all data in detail maps for displaying in jsp
	 */

	public Map<String, Object> getDetailedBatchReportMap(String id,
			Map<String, String> map, Holdingaccount ha) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Integer loadedsms = 0;
		if (map.get("00" + id) != null) {
			loadedsms = Integer.parseInt(map.get("00" + id).toString());
			smsMapCount.put(props.getProperty("00"), loadedsms);
		}
		else{
			smsMapCount.put(props.getProperty("00"), 0);
		}
		// Failed to be loaded ==>44
		smsMapCount.put(props.getProperty("44"), ha.getCountofcellno()
				- loadedsms);
		smsMapCount.put("DateTime", ha.getTimedate());

		Integer procesedsms = 0;
		if (map.get("11" + id) != null) {
			procesedsms = Integer.parseInt(map.get("11" + id).toString());
		}
		Integer failedDuetoKannelIsDown = 0;
		if (map.get("88" + id) != null) {
			failedDuetoKannelIsDown = Integer.parseInt(map.get("88" + id)
					.toString());
		}
		if(map.get("11" + id)==null){
			smsMapCount.put(props.getProperty("11"), 0);
		}else{
		smsMapCount.put(props.getProperty("11"), map.get("11" + id));
		}
		// "Failed to be Processed" -->55
		// (never got to kannel)
		// smsMapCount.put("Failed before getting to gateway",
		// loadedsms-(procesedsms+failedDuetoKannelIsDown));
		smsMapCount.put("Failed before getting to gateway", 0);
		Integer failedduetoOutOFmemoryError = loadedsms
				- (procesedsms + failedDuetoKannelIsDown);
		// Kannel was down
		// log.info(" failedDuetoKannelIsDown+failedduetoOutOFmemoryError::"+
		// failedDuetoKannelIsDown+failedduetoOutOFmemoryError);
		smsMapCount.put("Failed to be accepted by gateway",
				failedDuetoKannelIsDown + failedduetoOutOFmemoryError);
		// Unconfirmed delivery to mobile network
		if(map.get("8" + id)==null){
			smsMapCount.put("Unconfirmed delivery to mobile network",
					0);
		}
		else{
		smsMapCount.put("Unconfirmed delivery to mobile network",
				map.get("8" + id));
		}
		if(map.get("m8" + id)==null){
			smsMapCount.put(props.getProperty("m8"), 0);
		}
		else{
			smsMapCount.put(props.getProperty("m8"), map.get("m8" + id));
		}
		if(map.get("m16" + id)==null){
			smsMapCount.put(props.getProperty("m16"), 0);
		}
		else{
			smsMapCount.put(props.getProperty("m16"), map.get("m16" + id));
		}
		//smsMapCount.put(props.getProperty("m8"), map.get("m8" + id));
		if( map.get("1" + id)==null){
			smsMapCount.put(props.getProperty("1"),0);
		}
		else{
			smsMapCount.put(props.getProperty("1"), map.get("1" + id));
		}
		if(map.get("2" + id)==null){
			smsMapCount.put(props.getProperty("2"), 0);
		}
		else{
			smsMapCount.put(props.getProperty("2"), map.get("2" + id));
		}
	
		return smsMapCount;
	}
	
	/*
	 * Function to get details of credit spent by client between particular dates
	 */
	public List<Holdingaccount> getClientBatches(long clientManagerid, Date fromDate, Date toDate){
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Holdingaccount> list = null;
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Clientmanager cm = (Clientmanager) session.load(
					Clientmanager.class, clientManagerid);
			Criteria crt = session.createCriteria(Holdingaccount.class);
			crt.add(Restrictions.eq("clientmanager", cm));
			crt.add(Restrictions.between("timedate", fromDate, toDate));
			crt.addOrder(Order.asc("timedate"));
			list = crt.list();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			session.close();
			session = null;
		}
		return list;
	}

	/*
	 * Sorting details of clients into maps for display purpose
	 */
	public Map<Integer, Object> getBatchesMap(Long managerid, Date fromDate, Date toDate){
		List<Holdingaccount> list = null;
		list = getClientBatches(managerid, fromDate, toDate);
		Iterator it=list.iterator();
		Map<Integer, Object> mainMap = new LinkedHashMap<Integer, Object>();
		int count=1;
		while(it.hasNext())
		{
			Map<String, Object> subMap=new LinkedHashMap<String, Object>();
			Holdingaccount ha=(Holdingaccount)it.next();
			subMap.put("Date & Time",ha.getTimedate());
			subMap.put("SMSs Sent",ha.getSmssentcount());
			subMap.put("Credits Used",ha.getSmssentcount()*ha.getSmsrate());
			mainMap.put(count++, subMap);
		}
		return mainMap;
	}
	
	/*
	 * Getting Message from holding account Id
	 */
	public String getMessage(Holdingaccount ha)
	{
		SessionFactory sessionFactory = null;
		Session session = null;
		List list=null;
		String result = "";
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crt=session.createCriteria(Sms.class);
			crt.add(Restrictions.eq("holdingaccount",ha));
			crt.setMaxResults(1);
			list= crt.list();
			result = ((Sms)list.get(0)).getMessage();
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
		}
		return result;
	}
	 /*
		 * Getting batches between two dates
		 */
		public List<Holdingaccount> getBatches(String from, String to){
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			Date fromDate = null;
			Date toDate = null;
			try {
				fromDate = sdf.parse(from);
				toDate = sdf.parse(to);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			List<Holdingaccount> results = null;
			try {
				sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				Criteria crt=session.createCriteria(Holdingaccount.class);
				crt.add(Restrictions.between("timedate", fromDate, toDate));
				results= crt.list();
			} catch (Exception e) {
				log.error(e.toString());
				e.printStackTrace();
			} finally {
				session.close();
				session = null;
			}
			return results;
		}
		
		/*
		 * Calling Stored procedure to get speeds 
		 */
		public String getSpeedReport(Long id)
		{
			String result = "";
			Session session = null;
			try {
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
				String SQL_QUERY = "select speedreport("+id+")";
				Query query = session.createSQLQuery(SQL_QUERY);
				List list = query.list();
				result = (String)list.get(0);
			} catch (Exception e) {
							e.printStackTrace();
						} finally {
							session.flush();
							session.close();
							session = null;
						}

			return result;
		}
		
		
		/*	public UserMaster getUserLogin(String  username,String password) {

		Session session = null;
		Query query = null;
		
		SessionFactory sessionFactory = DBFinder.getSessionFactory();
		try {
			session = sessionFactory.openSession();
			query = session.createQuery("select user from UserMaster user where user.username= :username and password= :password");
			query.setParameter("username", username);
			query.setParameter("password", password);
			if(query.list().size()>0)
				return (UserMaster)query.list().get(0);

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			session.close();
			session = null;
		}
		return null;
		// return new Conversationstate();

	}*/
		
		
		
		public boolean isHoldingaccountByClntTransRefExist(String clientTransactionReference) {
					
			Session session = null;
			try {
				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				session = sessionFactory.openSession();
			
				if((clientTransactionReference!=null) && (clientTransactionReference.equals("")))
					{
					System.out.println("in if");
					return false;}
				else{
				//session = sessionFactory.openSession();
				Query query = session.createQuery("select Holdingaccount from Holdingaccount Holdingaccount where Holdingaccount.clientTransactionReference = :clientTransactionReference");
				query.setParameter("clientTransactionReference", clientTransactionReference);
				
				System.out.println("in else"+query.list().size());
				if(query.list().size()>0){
				
					System.out.println("------");
					return true;
					
				}
				}

			} catch (Throwable t) {
				t.printStackTrace();
			} finally {
				if(session!=null)
				session.close();
				session = null;
			}
			return false;
			// return new Conversationstate();
			
			
			
			
			
			
		}

}
