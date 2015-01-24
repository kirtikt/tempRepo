package main.java.dao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Credits;



public class CreditsManagerDAO extends GenericHibernateDAO<Credits,Long>{

	/*
	 * It will Give the List Of credits
	 */
	public List<Credits> getCreditsDetail(long clientManagerid,String from, String to){
		SessionFactory sessionFactory = null;
		Session session = null;
		List<Credits> list = null;
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
					Clientmanager.class, clientManagerid);
			Criteria crt = session.createCriteria(Credits.class);
			crt.add(Restrictions.eq("clientmanager", cm));
			crt.add(Restrictions.between("purchaseddate", fromDate, toDate));
			crt.addOrder(Order.desc("pkcreditid"));
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
	 * Display map for the credit purchase report
	 */
	public Map<Object,Object>  getCreditsMap(long clientManagerid,String from,String to){
		List<Credits> list=getCreditsDetail(clientManagerid,from,to);
		Iterator<Credits> it=list.iterator();
		Map<Object,Object> mainMap=new LinkedHashMap<Object,Object>();
		int count=1;
		while(it.hasNext())
		{
			Map<String,Object> subMap=new LinkedHashMap<String,Object>();
			Credits crdts=(Credits)it.next();
			subMap.put("Purchasedamount",crdts.getPurchasedamount());
			subMap.put("Date",crdts.getPurchaseddate());
			mainMap.put(count++, subMap);
		}
		return mainMap;
	}	
}
