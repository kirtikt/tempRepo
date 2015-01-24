package main.java.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;
import com.ecomm.ultimatesms.messaging.utils.Helper;

public class SMSManagerDAO extends GenericHibernateDAO<Sms, Long> {

	org.slf4j.Logger log = LoggerFactory.getLogger(SMSManagerDAO.class);
	Properties props;

	public SMSManagerDAO() {
		try {
			props = Helper.getProps();
			
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	public Sms getSmsFromSmsid(String smsid) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Sms sms = null;
		try {
			
			Criteria crt = session.createCriteria(Sms.class);
			// Added on 27 Feb
			crt.setMaxResults(1);
			
			List<Sms> listSms = crt.add(Restrictions.eq("smsid", smsid.trim()))
					.list();
			if (listSms != null && listSms.size() != 0) {
				sms = listSms.get(0);
			}
			
		} catch (HibernateException e) {
			
			e.printStackTrace();
		} finally {

			transaction = null;
			session.flush();
			session.close();
			session = null;
		}
		
		return sms;
	}

//	public Sms getSmsFromSmsid(String smsid) {
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction transaction = null;
//		Sms sms = null;
//		try {
//			transaction = session.beginTransaction();
//			Criteria crt = session.createCriteria(Sms.class);
//			List<Sms> listSms = crt.add(Restrictions.eq("smsid", smsid.trim()))
//					.list();
//			if (listSms != null && listSms.size() != 0) {
//				sms = listSms.get(0);
//			}
//			transaction.commit();
//		} catch (HibernateException e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			e.printStackTrace();
//		} finally {
//
//			transaction = null;
//			session.flush();
//			session.close();
//			session = null;
//		}
//		return sms;
//	}
	
	public Sms getSmsObj(String smsid,String status) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Sms sms = null;
		try {
			transaction = session.beginTransaction();
			Criteria crt = session.createCriteria(Sms.class);
			crt.add(Restrictions.eq("smsid", smsid.trim()));
			crt.add(Restrictions.eq("status", status.trim()));
			List<Sms> listSms = crt.list();
			if (listSms != null && listSms.size() != 0) {
				sms = listSms.get(0);
			}
			transaction.commit();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			transaction = null;
			session.flush();
			session.close();
			session = null;
		}
		
		return sms;
	}


	public List<Sms> getSmsList(String criterianame, String criteriavalue) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Sms sms = null;
		List<Sms> listSms = null;
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Criteria crt = session.createCriteria(Sms.class);
		crt.add(Restrictions.eq(criterianame, criteriavalue));
		crt.addOrder(Order.desc("datetime"));
		crt.setMaxResults(100);

		listSms = crt.list();
		if (listSms != null && listSms.size() != 0) {
			sms = listSms.get(0);
		}
		if (transaction != null) {
			transaction.commit();
		}
		session.flush();
		session.close();
		session = null;
		return listSms;
	}

	public List<Sms> getSmsList(String criterianame, String criteriavalue,
			Long clientmanagerId, String from, String to) {

		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Sms sms = null;
		List<Sms> listSms = null;
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Clientmanager cm = (Clientmanager) session.load(Clientmanager.class,
				clientmanagerId);
		Criteria crt = session.createCriteria(Sms.class);
		// crt.add(Restrictions.eq(criterianame, criteriavalue));
		crt.add(Restrictions.eq("clientmanager", cm));
		crt.add(Restrictions.between("datetime", new Date(from), new Date(to)));
		crt.addOrder(Order.desc("datetime"));
		// Server gets block
		crt.setMaxResults(100);
		// String SQL_QUERY = " from Sms where " + criterianame + "='"
		// + criteriavalue + "' and fkclientmanagerid='" + clientmanagerId
		// +"' and datetime between '"+from+"' and '"+to+"' order by pksmsid desc";
		// Query query = session.createQuery(SQL_QUERY);
		listSms = crt.list();
		if (listSms != null && listSms.size() != 0) {
			sms = listSms.get(0);
		}
		if (transaction != null) {
			transaction.commit();
		}
		session.flush();
		session.close();
		session = null;
		return listSms;
	}

	public List<Sms> getSmsList(String criterianame, String criteriavalue,
			Long clientmanagerId) {
		List list = null;
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();

			String SQL_QUERY = " from Sms where " + criterianame + "='"
					+ criteriavalue + "' and fkclientmanagerid='"
					+ clientmanagerId + "' order by pksmsid desc";
			System.out.print(SQL_QUERY);
			Query query = session.createQuery(SQL_QUERY);
			query.setMaxResults(100);
			list = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
		}
		return list;

	}

	public List<Sms> getSmsListDateWise(String criterianame,
			String criteriavalue, Long clientmanagerId, String from, String to) {
		List list = null;
		Session session = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date dateFrom = (Date) formatter.parse(from);
			Date dateTo = (Date) formatter.parse(to);
			Clientmanager id = (Clientmanager) session.load(
					Clientmanager.class, clientmanagerId);
			Criteria crt = session.createCriteria(Sms.class);
			crt.add(Restrictions.eq(criterianame, criteriavalue));
			crt.add(Restrictions.eq("clientmanager", id));
			crt.add(Restrictions.between("datetime", dateFrom, dateTo));
			list = crt.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
		}
		return list;

	}

	public List<Sms> getSmsList(String criterianame, String criteriavalue,
			Long clientmanagerId, int pageNumber, String from, String to,
			int recordPerPage) {
		Session session = null;
		List list = null;
		Transaction transaction = null;
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			//log.info("recordPerPage ::" + recordPerPage);
			//log.info("pageNumber ::" + pageNumber);
			Calendar d = new GregorianCalendar();
			String date = (d.get(Calendar.MONTH) + 1) + "/"
					+ d.get(Calendar.DATE) + "/" + d.get(Calendar.YEAR);
			// String SQL_QUERY =
			// " from Sms as s where s."+criterianame+"='"+criteriavalue+"' and s.clientmanager.pkclientmanagerid='"+clientmanagerId+"' order by s.datetime desc";

			transaction = session.beginTransaction();
			String SQL_QUERY = " from Sms where " + criterianame + "='"
					+ criteriavalue + "' and fkclientmanagerid='"
					+ clientmanagerId + "' and datetime between '" + from
					+ " 00:00:00' and  '" + to
					+ " 23:59:59' order by datetime desc";
			System.out.print(SQL_QUERY);

			Query query = session.createQuery(SQL_QUERY);

			query.setFirstResult(recordPerPage * (pageNumber - 1));
			query.setMaxResults(recordPerPage);
			list = query.list();
			if (transaction != null) {
				transaction.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
			transaction = null;
		}
		return list;

	}

//	public boolean isSmsexist(String smsid, String status) {
//
//		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Transaction transaction = null;
//		boolean smsexist = false;
//		try {
//			transaction = session.beginTransaction();
//			Criteria crt = session.createCriteria(Sms.class);
//		//	Criterion smsidcrt = Restrictions.eq("smsid", smsid);
//		//	Criterion statuscrt = Restrictions.eq("status", status);
//		//	LogicalExpression expression = Restrictions.and(smsidcrt, statuscrt);
//			crt.add(Restrictions.eq("smsid", smsid.trim()));
//			crt.add(Restrictions.eq("status", status.trim()));
//		//	List<Sms> listSms = crt.add(expression).list();
//			List<Sms> listSms = crt.list();
//			if (listSms != null && listSms.size() != 0) {
//				smsexist = true;
//			}
//			transaction.commit();
//			session.flush();
//		} catch (HibernateException e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			session.close();
//			session = null;
//			transaction = null;
//		}
//		return smsexist;
//
//	}
	public boolean isSmsexist(String smsid, String status) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		boolean smsexist = false;
		Integer smssent=null;
		try {
			Criteria crt = session.createCriteria(Sms.class);
			crt.add(Restrictions.eq("smsid", smsid.trim()));
			crt.add(Restrictions.eq("status", status.trim()));
			crt.setProjection(Projections.rowCount()).uniqueResult();
			List listSms = crt.list();
			if (listSms != null) {
				smssent = (Integer) listSms.get(0);
			}
			if (smssent!=0) {
				smsexist = true;
			}
			
			session.flush();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return smsexist;

	}


	public List getSmsListForHolA(long ha) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		boolean smsexist = false;
		List<Sms> listSms = null;
		try {
			transaction = session.beginTransaction();

			String SQL_QUERY = " from Sms where fkholdingaccountid='" + ha
					+ "'  order by datetime desc";
			System.out.print(SQL_QUERY);

			Query query = session.createQuery(SQL_QUERY);
			// Criteria crt = session.createCriteria(Sms.class);
			// Criterion smsidcrt = Restrictions.eq("holdingaccount", ha);
			//
			// listSms = crt.list();
			// if (listSms != null && listSms.size() != 0) {
			// smsexist = true;
			// }
			listSms = query.list();
			transaction.commit();
			session.flush();
		} catch (HibernateException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
			session = null;
			transaction = null;
		}
		return listSms;

	}

	public int getSmsCount(String dateFrom, String dateTo, String status) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int smssent = 0;
		Double credits = 0d;

		try {
			transaction = session.beginTransaction();
			//log.info("class:::" + getClass());

			// List list=crt.add(Restrictions.between("timedate",dateFrom1
			// ,dateTo1)).list();
			String hql = "from Sms where status ='" + status
					+ "' and datetime between '" + dateFrom + "'and '" + dateTo
					+ "'";
			Query query = session.createQuery(hql);
			List list = query.list();
			if (list != null) {
				smssent = list.size();
			}
			//log.info("smssent ::" + smssent);
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
		}
		return smssent;

	}

	public Long getCount(String dateFrom, String dateTo, String status,
			long managerid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Long smssent = 0l;
		try {
			transaction = session.beginTransaction();

			String hql = "select count(*) from Sms where fkclientmanagerid ='"
					+ managerid + "' and datetime between '" + dateFrom
					+ "'and '" + dateTo + "'" + " and status ='" + status + "'";
			Query query = session.createQuery(hql);
			List list = query.list();

			if (list != null) {
				smssent = (Long) list.get(0);
			}
			//log.info("smssent ::" + smssent);
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
		}
		return smssent;

	}

	public int getLoadedCount(String dateFrom, String dateTo, long managerid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int count = 0;
		try {
			transaction = session.beginTransaction();

			String queryloaded = " from Holdingaccount where  fkclientmanagerid ='"
					+ managerid
					+ "' and timedate between '"
					+ dateFrom
					+ "'and '" + dateTo + "'";

			org.hibernate.Query q = session.createQuery(queryloaded);
		//	log.info("list size::" + q.list().size());
			List l = q.list();
			Iterator it = l.iterator();
			while (it.hasNext()) {
				Holdingaccount ha = (Holdingaccount) it.next();
				//log.info("db count {} {}", ha.getCountofcellno(),ha.getTimedate());
				//log.info("count{}", count);
				count = count + ha.getCountofcellno();
			}
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
		}
		return count;

	}

	public Map<String, Object> getMapSmsCountForClientSummaryRep(
			String dateFrom, String dateTo, long managerid) {

		int smssent = 0;
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();

		smsMapCount.put("Sms Loaded",
				getLoadedCount(dateFrom, dateTo, managerid));

		smsMapCount.put(props.getProperty("11"),
				getCount(dateFrom, dateTo, "11", managerid));

		smsMapCount.put(props.getProperty("m8"),
				getCount(dateFrom, dateTo, "m8", managerid));

		smsMapCount.put(props.getProperty("m16"),
				getCount(dateFrom, dateTo, "m16", managerid));

		smsMapCount.put(props.getProperty("1"),
				getCount(dateFrom, dateTo, "1", managerid));

		smsMapCount.put(props.getProperty("2"),
				getCount(dateFrom, dateTo, "2", managerid));

	//	log.info("smssent ::" + smssent);

		return smsMapCount;
	}

	public Map<String, Object> getMapSmsCountForAdminSummaryRep(
			String dateFrom, String dateTo, long managerid) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();

		smsMapCount.put("Sms Loaded",
				getLoadedCount(dateFrom, dateTo, managerid));

		smsMapCount.put(props.getProperty("11"),
				getCount(dateFrom, dateTo, "11", managerid));

		smsMapCount.put(props.getProperty("m8"),
				getCount(dateFrom, dateTo, "m8", managerid));

		// smsMapCount.put(props.getProperty("m16"), getCount(dateFrom, dateTo,
		// "m16", managerid));

		smsMapCount.put(props.getProperty("1"),
				getCount(dateFrom, dateTo, "1", managerid));

		smsMapCount.put(props.getProperty("2"),
				getCount(dateFrom, dateTo, "2", managerid));

		// smsMapCount.put(props.getProperty("8"), getCount(dateFrom, dateTo,
		// "8", managerid));

		//log.info("return map::" + smsMapCount);
		return smsMapCount;
	}

	public Map<String, Object> getMapSmsCountForAdminSummaryDetailedRep(
			String dateFrom, String dateTo, long managerid) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();

		smsMapCount.put("Failed to be Loaded", 0);

		smsMapCount.put("Sms Loaded",
				getLoadedCount(dateFrom, dateTo, managerid));

		smsMapCount.put(props.getProperty("11"),
				getCount(dateFrom, dateTo, "11", managerid));

		// status 88 for kannel is down
		// status 99 for no response fron kannel
		Long f88count = getCount(dateFrom, dateTo, "88", managerid);
		Long f99count = getCount(dateFrom, dateTo, "99", managerid);
		Long finalFailedCount = f88count + f99count;

		smsMapCount.put("Failed to be processed", finalFailedCount);

		smsMapCount.put(props.getProperty("8"),
				getCount(dateFrom, dateTo, "8", managerid));

		smsMapCount.put(props.getProperty("m8"),
				getCount(dateFrom, dateTo, "m8", managerid));

		smsMapCount.put(props.getProperty("m16"),
				getCount(dateFrom, dateTo, "m16", managerid));

		smsMapCount.put(props.getProperty("1"),
				getCount(dateFrom, dateTo, "1", managerid));

		smsMapCount.put(props.getProperty("2"),
				getCount(dateFrom, dateTo, "2", managerid));

		//log.info("return map::" + smsMapCount);
		return smsMapCount;
	}

	public int getSmsCount(String dateFrom, String dateTo, String status,
			long managerid) {
		BasicConfigurator.configure();
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int smssent = 0;
		Double credits = 0d;
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		try {
			transaction = session.beginTransaction();

			String hql = "from Sms where status ='" + status
					+ "'and fkclientmanagerid ='" + managerid
					+ "' and datetime between '" + dateFrom + "'and '" + dateTo
					+ "'";
			Query query = session.createQuery(hql);
			List list = query.list();
			if (list != null) {
				smssent = list.size();
			}

		//	log.info("smssent ::" + smssent);
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
		}
		return smssent;

	}

	public Map<String, Object> getMapSmsCountForSMSC(String dateFrom,
			String dateTo, long sendingInterfaceid) {
		BasicConfigurator.configure();
		// TODO Auto-generated method stub
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int smssent = 0;
		Long m8smscount = 0l;
		Long smscountstatus1 = 0l;
		Long smscountstatus2 = 0l;
		Map<String, Object> smsMapCount = new HashMap<String, Object>();
		// Double credits=0d;

		try {
			transaction = session.beginTransaction();
			//log.info("class:::" + getClass());
			// Criteria crt=session.createCriteria(Holdingaccount.class);
			// String pattern = "MM/dd/yyyy hh:mm:ss";
			// SimpleDateFormat format = new SimpleDateFormat(pattern);
			// Date dateFrom1 = format.parse(dateFrom);
			// Date dateTo1 = format.parse(dateTo);
			// List list=crt.add(Restrictions.between("timedate",dateFrom1
			// ,dateTo1)).list();
			String hql = "select count(*) from Sms where fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "' and datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + " and status ='11'";
			Query query = session.createQuery(hql);
			List list11 = query.list();
			if (list11 != null) {
				smsMapCount.put(props.getProperty("11"), list11.get(0));
			} else {
				smsMapCount.put(props.getProperty("11"), 0);
			}
			String hql1 = " select count(*) from Sms where fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "' and  datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + "and status ='m8'";
			Query query1 = session.createQuery(hql1);
			List listm8 = query1.list();
			if (listm8 != null) {
				m8smscount = (Long) listm8.get(0);
				smsMapCount.put(props.getProperty("m8"), listm8.get(0));

			} else {
				smsMapCount.put(props.getProperty("m8"), 0);
			}
			String hql2 = "select count(*) from Sms where  fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "' and datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + "and status ='m16'";
			Query query2 = session.createQuery(hql2);
			List listm16 = query2.list();
			if (listm16 != null) {
				smsMapCount.put(props.getProperty("m16"), listm16.get(0));
			} else {
				smsMapCount.put(props.getProperty("m16"), 0);
			}
			String hql3 = "select count(*) from Sms where fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "'and  datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + "and status ='1' ";
			Query query3 = session.createQuery(hql3);
			List list1 = query3.list();
			if (list1 != null) {
				smscountstatus1 = (Long) list1.get(0);
				smsMapCount.put(props.getProperty("1"), list1.get(0));
			} else {
				smsMapCount.put(props.getProperty("1"), 0);
			}
			String hql4 = " select count(*) from Sms where  fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "' and datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + " and status ='2'";
			Query query4 = session.createQuery(hql4);
			List list2 = query4.list();
			if (list2 != null) {
				smscountstatus2 = (Long) list2.get(0);
				smsMapCount.put(props.getProperty("2"), list2.get(0));
			} else {
				smsMapCount.put(props.getProperty("2"), 0);
			}
			String hql5 = " select count(*) from Sms where  fksendinginterfaceid ='"
					+ sendingInterfaceid
					+ "' and datetime between '"
					+ dateFrom + "'and '" + dateTo + "'" + " and status ='8'";
			Query query5 = session.createQuery(hql5);
			List list8 = query5.list();
			if (list8 != null) {
				smsMapCount.put(props.getProperty("8"), list8.get(0));
			} else {
				smsMapCount.put(props.getProperty("8"), 0);
			}
			/*
			 * sms sent with m8 but no delivery recipient from kannel (like 1 or
			 * 2) status that SMS sent but received status unconfirmed
			 */
			Long count = m8smscount - (smscountstatus1 + smscountstatus2);
			if (count > 0) {
				smsMapCount.put("SMS sent but received status unconfirmed ",
						count);
			} else {
				smsMapCount.put("SMS sent but received status unconfirmed ", 0);
			}

		//	log.info("smssent ::" + smssent);
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
		}
	//	log.info("return map::" + smsMapCount);
		return smsMapCount;
	}

	public List<Sms> getReportByCellNumber(String cellnumber, String from, String to) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		List smslist = null;
		List<Sms> listnodup = new ArrayList<Sms>();
		try {
			Transaction tr = session.beginTransaction();
			Criteria crt = session.createCriteria(Sms.class);
			crt.add(Restrictions.eq("destination", cellnumber));
			crt.add(Restrictions.between("datetime", new Date(from), new Date(
					to)));
			smslist = crt.list();
			//log.info("list size ::" + smslist.size());
			if (tr != null) {
				tr.commit();
			}
			//log.info("list size ::" + smslist.size());
			Set<String> setsms = new HashSet<String>();
			Iterator it = smslist.iterator();
			while (it.hasNext()) {
				Sms sms = (Sms) it.next();
				setsms.add(sms.getSmsid());
			}
		//	log.info("set size ::" + setsms.size());

			Iterator<String> it1 = setsms.iterator();
			while (it1.hasNext()) {
				String smsid = it1.next();
				for (int j = 0; j < smslist.size(); j++) {
					Sms sms = (Sms) smslist.get(j);
					if (smsid.equals(sms.getSmsid())) {
						listnodup.add(sms);
						break;
					}
				}
			}
			//log.info("no duplicatelist ::" + listnodup.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
		}
		return listnodup;

	}

	public List<Sms> getReportByCellNumber(String cellnumber, String from,
			String to, Long managerid) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		List<Sms> listnodup = new ArrayList<Sms>();
		// String
		// hql5=" from Sms where  fkclientmanagerid='"+managerid+"' and datetime between '"+from
		// + "'and '"+ to+"'"+ " and destination ='"+cellnumber+"'";
		// Query query = sess.createQuery(hql5);
		// List smslist = query.list();

		Criteria crt = session.createCriteria(Sms.class);
		crt.add(Restrictions.eq("destination", cellnumber));
		crt.add(Restrictions.between("datetime", new Date(from), new Date(to)));
		crt.createCriteria("clientmanager").add(Restrictions.eq("pkclientmanagerid", managerid));
		List smslist = crt.list();
		if (tr != null) {
			tr.commit();
		}
	//	log.info("list size ::" + smslist.size());
		Set<String> setsms = new HashSet<String>();
		Iterator it = smslist.iterator();
		while (it.hasNext()) {
			Sms sms = (Sms) it.next();
			setsms.add(sms.getSmsid());
		}
		//log.info("set size ::" + setsms.size());

		Iterator<String> it1 = setsms.iterator();
		while (it1.hasNext()) {
			String smsid = it1.next();
			for (int j = 0; j < smslist.size(); j++) {
				Sms sms = (Sms) smslist.get(j);
				if (smsid.equals(sms.getSmsid())) {
					listnodup.add(sms);
					break;
				}
			}
		}
		//log.info("no duplicatelist ::" + listnodup.size());

		session.flush();
		session.close();
		session = null;
		return listnodup;

	}

	public Map getBatchSmsStatusCount(Holdingaccount ha) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		smsMapCount.put("Sms loaded", ha.getCountofcellno());
		smsMapCount.put("DateTime", new Helper().myTimeIn(ha.getTimedate()));
		smsMapCount.put(props.getProperty("11"), getCount(ha, "11"));
		smsMapCount.put(props.getProperty("m8"), getCount(ha, "m8"));
		smsMapCount.put(props.getProperty("1"), getCount(ha, "1"));
		smsMapCount.put(props.getProperty("m16"), getCount(ha, "m16"));
		return smsMapCount;
	}
	
	public Map getBatchMapForSmsStatusCountWithFailedSms(Holdingaccount ha) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Integer loadedsms=getCount(ha, "00");
		smsMapCount.put(props.getProperty("00"),loadedsms);
		//Failed to be loaded ==>44
		smsMapCount.put(props.getProperty("44"), ha.getCountofcellno()-loadedsms);
		smsMapCount.put("DateTime", new Helper().myTimeIn(ha.getTimedate()));
		Integer procesedsms=getCount(ha, "11");
		Integer failedDuetoKannelIsDown=getCount(ha, "88");
		smsMapCount.put(props.getProperty("11"), procesedsms);
		//"Failed to be Processed" -->55
		//(never got to kannel)
		//smsMapCount.put("Failed before getting to gateway", loadedsms-(procesedsms+failedDuetoKannelIsDown));
		smsMapCount.put("Failed before getting to gateway", 0);
		Integer failedduetoOutOFmemoryError=loadedsms-(procesedsms+failedDuetoKannelIsDown);
		// Kannel was down
		//log.info(" failedDuetoKannelIsDown+failedduetoOutOFmemoryError::"+ failedDuetoKannelIsDown+failedduetoOutOFmemoryError);
		smsMapCount.put("Failed to be accepted by gateway", failedDuetoKannelIsDown+failedduetoOutOFmemoryError);
		//Unconfirmed delivery to mobile network
		smsMapCount.put("Unconfirmed delivery to mobile network", getCount(ha, "8"));
		
		smsMapCount.put(props.getProperty("m8"), getCount(ha, "m8"));
		smsMapCount.put(props.getProperty("1"), getCount(ha, "1"));
		smsMapCount.put(props.getProperty("m16"), getCount(ha, "m16"));
		return smsMapCount;
	}
	
	public Map getFaileRecordBatch(Holdingaccount ha) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Integer loadedsms=getCount(ha, "00");
		smsMapCount.put(props.getProperty("00"),loadedsms);
		//Failed to be loaded ==>44
		smsMapCount.put(props.getProperty("44"), ha.getCountofcellno()-loadedsms);
		smsMapCount.put("DateTime", new Helper().myTimeIn(ha.getTimedate()));
		Integer procesedsms=getCount(ha, "11");
		Integer failedDuetoKannelIsDown=getCount(ha, "88");
		smsMapCount.put(props.getProperty("11"), procesedsms);
		//"Failed to be Processed" -->55
		//(never got to kannel)
		Integer sub1=loadedsms-(procesedsms+failedDuetoKannelIsDown);
		smsMapCount.put("Failed before getting to gateway", 0);
		
		Integer failedduetoOutOFmemoryError=loadedsms-(procesedsms+failedDuetoKannelIsDown);
		
		// Kannel was down
		//smsMapCount.put("Failed to be accepted by gateway", failedDuetoKannelIsDown);
		smsMapCount.put("Failed to be accepted by gateway", failedDuetoKannelIsDown+failedduetoOutOFmemoryError);
		smsMapCount.put("Unconfirmed delivery to mobile network", getCount(ha, "8"));
		
		//Integer total=failedDuetoKannelIsDown+sub1;
		
		//smsMapCount.put("Total Failed sms count", total);
		
		//Unconfirmed delivery to mobile network
//		smsMapCount.put("Unconfirmed delivery to mobile network", getCount(ha, "8"));
//		smsMapCount.put(props.getProperty("m8"), getCount(ha, "m8"));
//		smsMapCount.put(props.getProperty("1"), getCount(ha, "1"));
//		smsMapCount.put(props.getProperty("m16"), getCount(ha, "m16"));
		return smsMapCount;
	}
	
	
	
	public Map getClientBatchMapForSmsStatusCountWithFailedSms(Holdingaccount ha) {
		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Integer loadedsms=getCount(ha, "00");
		smsMapCount.put(props.getProperty("00"),loadedsms);
		//Failed to be loaded ==>44
		smsMapCount.put(props.getProperty("44"), ha.getCountofcellno()-loadedsms);
		smsMapCount.put("DateTime", new Helper().myTimeIn(ha.getTimedate()));
		Integer procesedsms=getCount(ha, "11");
		//Integer failedDuetoKannelIsDown=getCount(ha, "88");
		smsMapCount.put(props.getProperty("11"), procesedsms);
		//"Failed to be Processed" -->55
		//(never got to kannel)
		smsMapCount.put("Failed to be processed", loadedsms-procesedsms);
		// Kannel was down
		//smsMapCount.put("Failed to be accepted by gateway", failedDuetoKannelIsDown);
		//Unconfirmed delivery to mobile network
		//smsMapCount.put("Unconfirmed delivery to mobile network", getCount(ha, "8"));
		smsMapCount.put(props.getProperty("m8"), getCount(ha, "m8"));
		smsMapCount.put(props.getProperty("1"), getCount(ha, "1"));
		smsMapCount.put(props.getProperty("m16"), getCount(ha, "m16"));
		return smsMapCount;
	}
	
	

	public Integer getCount(Holdingaccount ha, String status) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Integer smssent = 0;
		try {
			transaction = session.beginTransaction();

			// String hql =
			// "select count(*) from Sms where fkholdingaccountid ='"
			// + ha.getPkeyholdingaccountid() + "' and status ='"+status+"'";
			// Query query = session.createQuery(hql);
			Criteria crt = session.createCriteria(Sms.class);
			crt.add(Restrictions.eq("status", status));
			crt.add(Restrictions.eq("holdingaccount", ha));
			crt.setProjection(Projections.rowCount()).uniqueResult();
			List list = crt.list();

			if (list != null) {
				smssent = (Integer) list.get(0);
			}

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
		}
		return smssent;

	}

	public Map<Holdingaccount, Map> speedReportPerBatch(String from,String to) {
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Holdingaccount> listH = null;
		List<Sms> listS = null;
		List<Sms> listSms = null;
		Date loadedDate = null;
		float sumLoaded = 0l;
		float summ8 = 0l;
		Map<Holdingaccount, Map> map = new LinkedHashMap<Holdingaccount, Map>();
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crt = session.createCriteria(Holdingaccount.class);
			 crt.add(Restrictions.between("timedate", new Date(from), new Date(to)));
			crt.addOrder(Order.desc("pkeyholdingaccountid"));
			crt.setMaxResults(10);
			listH = crt.list();

			for (int i = 0; i < listH.size(); i++) {
				Holdingaccount ha = listH.get(i);
				Criteria crt1 = session.createCriteria(Sms.class);
				crt1.add(Restrictions.eq("holdingaccount", ha));
				crt1.add(Restrictions.eq("status", "00"));
				listS = crt1.list();

				int count11 = 0;
				int countm8 = 0;
				for (int j = 0; j < listS.size(); j++) {
					Sms sms = listS.get(j);
					Map submap = new LinkedHashMap();
					loadedDate = sms.getDatetime();
					Criteria crt2 = session.createCriteria(Sms.class);
					crt2.add(Restrictions.eq("smsid", sms.getSmsid()));
					listSms = crt2.list();

					for (int k = 0; k < listSms.size(); k++) {
						Sms smswithallstatus = (Sms) listSms.get(k);
						String status = smswithallstatus.getStatus();

						if ("11".equals(status.trim())) {
							Date processedDate = smswithallstatus.getDatetime();
							
							System.out.println("Status:: "+status.trim()+"loadedDate:: "+loadedDate+"processedDate:: "+processedDate);
							float diff = processedDate.getTime()
									- loadedDate.getTime();

							float diffSeconds = diff / 1000;

							sumLoaded = sumLoaded + diffSeconds;
							count11++;
						}
						if ("m8".equals(status.trim())) {
							Date processedDate = smswithallstatus.getDatetime();
							System.out.println("Status:: "+status.trim()+"loadedDate:: "+loadedDate+"processedDate:: "+processedDate);
							
							float diff = processedDate.getTime()
									- loadedDate.getTime();
						
							float diffSeconds = diff / 1000;
							
							summ8 = summ8 + diffSeconds;
							countm8++;
						}
					}
					float avg11 = 0;
					float avgm8 = 0;
					if (count11 != 0) {
						avg11 = sumLoaded / count11;
					}
					if (countm8 != 0) {
						avgm8 = sumLoaded / countm8;
					}
					submap.put(avg11, avgm8);
					map.put(ha, submap);
				}
			}
			//log.info("map" + map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
		}
		return map;
	}
	
	
	
	public Map<Holdingaccount, List> speedReportPerBatchNew(String from,String to) {
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Holdingaccount> listH = null;
		List<Sms> listS = null;
		
		Map<Holdingaccount, List> map = new LinkedHashMap<Holdingaccount, List>();
		try {
			sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			Criteria crt = session.createCriteria(Holdingaccount.class);
			 crt.add(Restrictions.between("timedate", new Date(from), new Date(to)));
			crt.addOrder(Order.desc("pkeyholdingaccountid"));
			
			listH = crt.list();
         //   log.info("total batches:: "+listH.size());
			for (int i = 0; i < listH.size(); i++) {
				
				List sublist=new LinkedList();
				
				Holdingaccount ha = listH.get(i);
				Criteria crt1 = session.createCriteria(Sms.class);
				crt1.add(Restrictions.eq("holdingaccount", ha));
				crt1.add(Restrictions.eq("status", "00"));
				crt.addOrder(Order.asc("pksmsid"));
				listS = crt1.list();
				 
				if(listS.size()!=0){
				
				sublist.add(listS.size());
				
				Sms firstSms=listS.get(0);
				
				int size=listS.size();
				Sms lastSms=listS.get(size-1);
				
				
				Date firstdate=firstSms.getDatetime();
				Date lastdate=lastSms.getDatetime();
				
				float diff = (lastdate.getTime()
						- firstdate.getTime())/size;
			
				float diffSeconds = diff / 1000;
				
				
				String smsid=firstSms.getSmsid();
				
				Criteria crt2 = session.createCriteria(Sms.class);
				crt2.add(Restrictions.eq("smsid", smsid));
				crt2.add(Restrictions.eq("status", "11"));
				
				List firstListProcessed = crt2.list();
				
				String smsidlast=lastSms.getSmsid();
				Criteria crt3 = session.createCriteria(Sms.class);
				crt3.add(Restrictions.eq("smsid", smsidlast));
				crt3.add(Restrictions.eq("status", "11"));
				
				List lastListprocessed = crt3.list();
				float diffpro=0l;
				float diffSecondspro=0l; 
				if(firstListProcessed.size()!=0 && lastListprocessed.size()!=0){
				Date fist=((Sms)firstListProcessed.get(0)).getDatetime();
				Date last=((Sms)lastListprocessed.get(0)).getDatetime();
				 diffpro = (last.getTime()- fist.getTime())/size;
				 diffSecondspro = diffpro / 1000;
				}
				
				/*
				 * For status8+messageid 
				 */Criteria crt4 = session.createCriteria(Sms.class);
					crt4.add(Restrictions.eq("smsid", smsid));
					crt4.add(Restrictions.eq("status", "m8"));
					
					List firstListm8 = crt4.list();
					
					
					
					Criteria crt5 = session.createCriteria(Sms.class);
					crt5.add(Restrictions.eq("smsid", smsidlast));
					crt5.add(Restrictions.eq("status", "11"));
					
					List lastListm8 = crt5.list();
					
					float diffpro1=0l;
					float diffSecondspro1=0l; 
					
					if(firstListm8.size()!=0 && lastListm8.size()!=0){
					Date fist=((Sms)firstListm8.get(0)).getDatetime();
					Date last=((Sms)lastListm8.get(0)).getDatetime();
					 diffpro1 = (last.getTime()- fist.getTime())/size;
					 diffSecondspro1 = diffpro1 / 1000;
					}
				
				
				// log.info("processed sms/sec:: "+ diffSecondspro);
				
					// loaded count
				sublist.add(diffSeconds);
				
				// processed count
				if(diffSecondspro!=0l ){
					sublist.add(diffSecondspro);
				}else{	
					sublist.add("Last sms not processed" );
				}
				//8 + messageid count
                
				if(diffSecondspro1!=0l ){
					sublist.add(diffSecondspro1);
				}else{
					sublist.add("Last sms not processed" );
				}
					
				
				
				map.put(ha, sublist);
				sublist=null;
				listS=null;
				firstListProcessed=null;
				lastListprocessed=null;
				}
			}
			//log.info("map" + map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.flush();
			session.close();
			session = null;
			listH=null;
		}
		return map;
	}
	
	public List<Sms> getFailedSms(Long pkholdingaccount){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<Sms> list =null;
		try {
			 transaction = session.beginTransaction();
			 String hql = "from Sms where fkholdingaccountid="+pkholdingaccount+" and status='00' and futurestatus = 'false' and smsid not in (select smsid from Sms where status='11' and fkholdingaccountid="+pkholdingaccount+") order by pksmsid";
			 Query query = session.createQuery(hql);
			 list = query.list();
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
		}
		return list;
	}
	public List<Sms> getFailedSms(Long pkholdingaccount,String status){
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		
		List<Sms> list =null;
		try {
			 transaction = session.beginTransaction();
			 String hql = "from Sms where fkholdingaccountid="+pkholdingaccount+" and status='8' and futurestatus = 'false' and smsid not in (select smsid from Sms where status='m8' and fkholdingaccountid="+pkholdingaccount+") order by pksmsid";
			 Query query = session.createQuery(hql);
			 list = query.list();
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
		}
		return list;
	}
	
	/*
	 Function hits database once to fetch records for monthly summary report.
	  */
	public List getMonthlyDataForAdminSummaryRep(String dateFrom, String dateTo, long managerid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(dateFrom);
			toDate = sdf.parse(dateTo);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}
		List results = null;
		try {
			String hql="SELECT EXTRACT(MONTH FROM datetime) as month, EXTRACT(YEAR FROM datetime) as year, status||' ', COUNT(*) FROM sms WHERE fkclientmanagerid ="+managerid+" AND datetime BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY month, year, status;";
			Query query = session.createSQLQuery(hql);
			results = query.list();
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
	 Function hits database once to fetch records for daily summary report.
	  */
	public List getDailyDataForAdminSummaryRep(String dateFrom, String dateTo, long managerid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(dateFrom);
			toDate = sdf.parse(dateTo);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}

		List results = null;
		try {
			String hql="SELECT EXTRACT(DAY FROM datetime) as day, EXTRACT(MONTH FROM datetime) as month, EXTRACT(YEAR FROM datetime) as year, status||' ', COUNT(*) FROM sms WHERE fkclientmanagerid = "+managerid+" AND datetime BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY day, month, year, status;";
			Query query = session.createSQLQuery(hql);
			results = query.list();
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
	 Sorting monthly data retrieved from database into proper maps for display purpose into detailed summary report.
	 */
	public Map<String, Object> getMapSmsCountForMonthlySummaryDetailedRep(String str_key,List list) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim(), row[3].toString().trim());
		}

		smsMapCount.put("Failed to be Loaded", 0);
		if(tempMap.get(str_key+"00") !=null ){
			smsMapCount.put("Sms Loaded", tempMap.get(str_key+"00"));
		}else{
			smsMapCount.put("Sms Loaded", 0);
		}
		if(tempMap.get(str_key+"11") !=null ){
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}else{
			smsMapCount.put(props.getProperty("11"), 0);
		}
		// status 88 for kannel is down
		Long f88count = 0l;
		if(tempMap.get(str_key+"88")!=null)
		{
			f88count = (Long) tempMap.get(str_key+"88");
		}
		// status 99 for no response fron kannel
		Long f99count = 0l;
		if(tempMap.get(str_key+"99")!=null)
		{
			f99count = (Long) tempMap.get(str_key+"99");
		}
		Long finalFailedCount = f88count + f99count;
		smsMapCount.put("Failed to be processed", finalFailedCount);
		if(tempMap.get(str_key+"8") !=null ){
			smsMapCount.put(props.getProperty("8"),tempMap.get(str_key+"8"));
		}else{
			smsMapCount.put(props.getProperty("8"), 0);
		}
		if(tempMap.get(str_key+"m8") !=null ){
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
		}else{
			smsMapCount.put(props.getProperty("m8"), 0);
		}
		if(tempMap.get(str_key+"m16") !=null ){
			smsMapCount.put(props.getProperty("m16"),tempMap.get(str_key+"m16"));
		}else{
			smsMapCount.put(props.getProperty("m16"), 0);
		}
		if(tempMap.get(str_key+"1") !=null ){
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
		}else{
			smsMapCount.put(props.getProperty("1"), 0);
		}
		if(tempMap.get(str_key+"2") !=null ){
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
		}else{
			smsMapCount.put(props.getProperty("2"), 0);
		}
		return smsMapCount;
	}

	/*
	 Sorting daily data retrieved from database into proper maps for display purpose into detailed summary report.
	 */
	public Map<String, Object> getMapSmsCountForDailySummaryDetailedRep(String str_key,List list) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim()+row[3].toString().trim(), row[4].toString().trim());
		}	

		smsMapCount.put("Failed to be Loaded", 0);
		if(tempMap.get(str_key+"00") !=null ){
			smsMapCount.put("Sms Loaded", tempMap.get(str_key+"00"));
		}else{
			smsMapCount.put("Sms Loaded", 0);
		}
		if(tempMap.get(str_key+"11") !=null ){
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}else{
			smsMapCount.put(props.getProperty("11"), 0);
		}
		// status 88 for kannel is down
		Long f88count = 0l;
		if(tempMap.get(str_key+"88")!=null)
		{
			f88count = (Long) tempMap.get(str_key+"88");
		}
		// status 99 for no response fron kannel
		Long f99count = 0l;
		if(tempMap.get(str_key+"99")!=null)
		{
			f99count = (Long) tempMap.get(str_key+"99");
		}
		Long finalFailedCount = f88count + f99count;
		smsMapCount.put("Failed to be processed", finalFailedCount);
		if(tempMap.get(str_key+"8") !=null ){
			smsMapCount.put(props.getProperty("8"),tempMap.get(str_key+"8"));
		}else{
			smsMapCount.put(props.getProperty("8"), 0);
		}
		if(tempMap.get(str_key+"m8") !=null ){
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
		}else{
			smsMapCount.put(props.getProperty("m8"), 0);
		}
		if(tempMap.get(str_key+"m16") !=null ){
			smsMapCount.put(props.getProperty("m16"),tempMap.get(str_key+"m16"));
		}else{
			smsMapCount.put(props.getProperty("m16"), 0);
		}
		if(tempMap.get(str_key+"1") !=null ){
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
		}else{
			smsMapCount.put(props.getProperty("1"), 0);
		}
		if(tempMap.get(str_key+"2") !=null ){
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
		}else{
			smsMapCount.put(props.getProperty("2"), 0);
		}
		return smsMapCount;
	}

	
	/*
	 Sorting monthly data retrieved from database into proper maps for display purpose into summary report.
	 */
	public Map<String, Object> getMonthlyMapSmsCountForAdminSummaryRep(String str_key,List list){

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, String> tempMap = new HashMap<String, String>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim(), row[3].toString().trim());
			row=null;
		}	
		if(tempMap.get(str_key+"00") == null){
			smsMapCount.put("Sms Loaded", 0);
		}else{
			smsMapCount.put("Sms Loaded", tempMap.get(str_key+"00"));
		}
		if(tempMap.get(str_key+"11") == null){
			smsMapCount.put(props.getProperty("11"), 0);
		}else{
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}
		if(tempMap.get(str_key+"m8") == null){
			smsMapCount.put(props.getProperty("m8"), 0);
		}else{
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
		}
		if(tempMap.get(str_key+"1") == null){
			smsMapCount.put(props.getProperty("1"), 0);
		}else{	
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
		}
		if(tempMap.get(str_key+"2") == null){
			smsMapCount.put(props.getProperty("2"), 0);
		}else{
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
		}
		return smsMapCount;
	}

	/*
	 Sorting daily data retrieved from database into proper maps for display purpose into summary report.
	 */
	public Map<String, Object> getDailyMapSmsCountForAdminSummaryRep(String str_key,List list){

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, String> tempMap = new HashMap<String, String>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim()+row[3].toString().trim(), row[4].toString().trim());
			row=null;
		}	
		if(tempMap.get(str_key+"00") == null){
			smsMapCount.put("Sms Loaded", 0);
		}else{
			smsMapCount.put("Sms Loaded", tempMap.get(str_key+"00"));
		}
		if(tempMap.get(str_key+"11") == null){
			smsMapCount.put(props.getProperty("11"), 0);
		}else{
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}
		if(tempMap.get(str_key+"m8") == null){
			smsMapCount.put(props.getProperty("m8"), 0);
		}else{
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
		}
		if(tempMap.get(str_key+"1") == null){
			smsMapCount.put(props.getProperty("1"), 0);
		}else{	
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
		}
		if(tempMap.get(str_key+"2") == null){
			smsMapCount.put(props.getProperty("2"), 0);
		}else{
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
		}
		return smsMapCount;
	}
	
	/*
	 Function hits database once to fetch records for monthly dashboard report.
	 */
	public List getMonthlyDataForDashboardRep(String dateFrom, String dateTo, long sendingInterfaceid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(dateFrom);
			toDate = sdf.parse(dateTo);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}
		List results = null;
		try {
			String hql="SELECT EXTRACT(MONTH FROM datetime) as month, EXTRACT(YEAR FROM datetime) as year, status||' ', COUNT(*) FROM sms WHERE fksendinginterfaceid ="+sendingInterfaceid+" AND datetime BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY month, year, status;";
			Query query = session.createSQLQuery(hql);
			results = query.list();
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
	 Sorting monthly data retrieved from database into proper maps for display purpose into dashboard report.
	 */
	public Map<String, Object> getMapSmsCountForMonthlyDashboardRep(String str_key,List list) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim(), row[3].toString().trim());
		}
		if(tempMap.get(str_key+"11") !=null ){
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}else{
			smsMapCount.put(props.getProperty("11"), 0);
		}
		Long smscountstatusm8 = 0l;
		if(tempMap.get(str_key+"m8") !=null ){
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
			smscountstatusm8 = Long.parseLong(tempMap.get(str_key+"m8").toString());
		}else{
			smsMapCount.put(props.getProperty("m8"), 0);
		}
		if(tempMap.get(str_key+"m16") !=null ){
			smsMapCount.put(props.getProperty("m16"),tempMap.get(str_key+"m16"));
		}else{
			smsMapCount.put(props.getProperty("m16"), 0);
		}
		Long smscountstatus1 = 0l; 
		if(tempMap.get(str_key+"1") !=null ){
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
			smscountstatus1 = Long.parseLong(tempMap.get(str_key+"1").toString());
		}else{
			smsMapCount.put(props.getProperty("1"), 0);
		}
		Long smscountstatus2 = 0l;
		if(tempMap.get(str_key+"2") !=null ){
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
			smscountstatus2 = Long.parseLong(tempMap.get(str_key+"2").toString());
		}else{
			smsMapCount.put(props.getProperty("2"), 0);
		}
		if(tempMap.get(str_key+"8") !=null ){
			smsMapCount.put(props.getProperty("8"),tempMap.get(str_key+"8"));
		}else{
			smsMapCount.put(props.getProperty("8"), 0);
		}
		Long count = smscountstatusm8 - (smscountstatus1 + smscountstatus2);
		smsMapCount.put("SMS sent but received status unconfirmed ",count);	
		return smsMapCount;
	}

	/*
	 Function hits database once to fetch records for daily dashboard report.
	 */
	public List getDailyDataForDashboardRep(String dateFrom, String dateTo, long sendingInterfaceid) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(dateFrom);
			toDate = sdf.parse(dateTo);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		}

		List results = null;
		try {
			String hql="SELECT EXTRACT(DAY FROM datetime) as day, EXTRACT(MONTH FROM datetime) as month, EXTRACT(YEAR FROM datetime) as year, status||' ', COUNT(*) FROM sms WHERE fksendinginterfaceid = "+sendingInterfaceid+" AND datetime BETWEEN '"+fromDate+"' AND '"+toDate+"' GROUP BY day, month, year, status;";
			Query query = session.createSQLQuery(hql);
			results = query.list();
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
	 Sorting daily data retrieved from database into proper maps for display purpose into dashboard report.
	 */
	public Map<String, Object> getMapSmsCountForDailyDashboardRep(String str_key,List list) {

		Map<String, Object> smsMapCount = new LinkedHashMap<String, Object>();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			tempMap.put(row[0].toString().trim()+row[1].toString().trim()+row[2].toString().trim()+row[3].toString().trim(), row[4].toString().trim());
		}	
		if(tempMap.get(str_key+"11") !=null ){
			smsMapCount.put(props.getProperty("11"),tempMap.get(str_key+"11"));
		}else{
			smsMapCount.put(props.getProperty("11"), 0);
		}
		Long smscountstatusm8 = 0l;
		if(tempMap.get(str_key+"m8") !=null ){
			smsMapCount.put(props.getProperty("m8"),tempMap.get(str_key+"m8"));
			smscountstatusm8 = Long.parseLong(tempMap.get(str_key+"m8").toString());
		}else{
			smsMapCount.put(props.getProperty("m8"), 0);
		}
		if(tempMap.get(str_key+"m16") !=null ){
			smsMapCount.put(props.getProperty("m16"),tempMap.get(str_key+"m16"));
		}else{
			smsMapCount.put(props.getProperty("m16"), 0);
		}
		Long smscountstatus1 = 0l; 
		if(tempMap.get(str_key+"1") !=null ){
			smsMapCount.put(props.getProperty("1"),tempMap.get(str_key+"1"));
			smscountstatus1 = Long.parseLong(tempMap.get(str_key+"1").toString());
		}else{
			smsMapCount.put(props.getProperty("1"), 0);
		}
		Long smscountstatus2 = 0l;
		if(tempMap.get(str_key+"2") !=null ){
			smsMapCount.put(props.getProperty("2"),tempMap.get(str_key+"2"));
			smscountstatus2 = Long.parseLong(tempMap.get(str_key+"2").toString());
		}else{
			smsMapCount.put(props.getProperty("2"), 0);
		}
		if(tempMap.get(str_key+"8") !=null ){
			smsMapCount.put(props.getProperty("8"),tempMap.get(str_key+"8"));
		}else{
			smsMapCount.put(props.getProperty("8"), 0);
		}
		Long count = smscountstatusm8 - (smscountstatus1 + smscountstatus2);
		smsMapCount.put("SMS sent but received status unconfirmed ",count);	
		return smsMapCount;
	}
	
	
	
}