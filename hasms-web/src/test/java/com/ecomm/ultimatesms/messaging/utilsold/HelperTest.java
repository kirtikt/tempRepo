package com.ecomm.ultimatesms.messaging.utilsold;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ecomm.ultimatesms.messaging.commons.SmsOrder;
import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sms;

import com.ecomm.ultimatesms.messaging.utils.Helper;


public class HelperTest {

	//Logger log=LoggerFactory.getLogger(HelperTest.class);

	//@Test
	public void TestSpeedreport() {
		try{
	String managerId = "5";
	String fromDate = "05/18/2012";
	String toDate = "05/18/2012";

	String from = fromDate + " 00:00:00";
	String to = toDate + " 23:59:59";

	Map<Integer, Object> map = new LinkedHashMap<Integer, Object>();
	HoldingaccountManager ha = new HoldingaccountManager();
	List<Holdingaccount> list = null;
	if(managerId != null && managerId != "")
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date from1 = null;
		Date to1 = null;
		try {
			from1 = sdf.parse(from);
			to1 = sdf.parse(to);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Long longId = Long.parseLong(managerId);
		list = ha.getClientBatches(longId, from1, to1);
	}
	else
	{
		list = ha.getBatches(from, to);
	}


	int count=1;

	if(list!=null && list.size()!=0){
		Iterator<Holdingaccount> itr = list.iterator();
		while (itr.hasNext()) {
			Map<String,Object> tempMap = new LinkedHashMap<String,Object>();
			Holdingaccount haRow = itr.next();
			tempMap.put("Batch Id", haRow.getPkeyholdingaccountid());
			tempMap.put("Batch Size",haRow.getCountofcellno());
			tempMap.put("DateTime", haRow.getTimedate());
			System.out.println(" haRow.getPkeyholdingaccountid() :: "+haRow.getPkeyholdingaccountid());
			
			String temp = ha.getSpeedReport(haRow.getPkeyholdingaccountid());
			System.out.println("temp :: "+temp+ " haRow.getPkeyholdingaccountid() :: "+haRow.getPkeyholdingaccountid());
			String[] s = temp.split("&");
			String[] s1 = s[0].split("\\.");
			String[] s2 = s[1].split("\\.");
			Long is = Long.parseLong(s1[0]);
			Long fs = Long.parseLong(s2[0]);
			Double sd =  Double.parseDouble(s[0]) - Double.parseDouble(s[1]);
		//	Long sd = is-fs;
			tempMap.put("SMSC processing speed",is);
			tempMap.put("System Speed",fs);
			tempMap.put("System delay", sd);
			map.put(count,tempMap);
			count++;
		}
		System.out.println("Map  "+ map);
	}
	else{
		map=null;
	}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
}
	
	
	
	//@Test
		public void TestSP() {
	Session session = null;
	Transaction tr = null;

	try {

		SessionFactory sessionFactory = HibernateUtil
				.getSessionFactory();
		session = sessionFactory.openSession();
		 tr = session.beginTransaction();
		 System.out.println("#######-Start-#####");
		// String SQL_QUERY =
		// "select savefeedback('0a609648-ad1c-4bca-90c5-19ca8ce38bb0','16','deliveryInfo','12/12/12','msgdeli','n/a','27711681922')";
		String SQL_QUERY = "select speedreport(562)";
		Query query = session.createSQLQuery(SQL_QUERY);
		List list = query.list();
		
		tr.commit();
		System.out.println("#######-Done-##### :: "+ list);

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		session.flush();
		session.close();
		tr=null;
		session = null;
	
	}
		}
	
	//@Test
	public void httpget() {
		  String url = "http://68.169.52.16:8080/payless4smsLoadTest/feedback";

		int counter = 1000;
		for(int i = 0; i < counter; i++)
		{
			HttpClient client = new HttpClient();	
			HttpMethod method = new GetMethod(url);
			try {
				int statusCode = client.executeMethod(method);
				System.out.println("Status Code : "+statusCode);
				if (statusCode != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + method.getStatusLine());
				}

				// Read the response body.
				byte[] responseBody = method.getResponseBody();
				// Deal with the response.
				// Use caution: ensure correct character encoding and is not binary data
				System.out.println(new String(responseBody)+"i value == "+i);

			}
			catch (HttpException e) {
				System.err.println("Fatal protocol violation: " + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.err.println("Fatal transport error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				// Release the connection.
				method.releaseConnection();
			}  
		}
	}

	
	
	//@Test
		public void speedReport() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        Session session=sessionFactory.openSession();
       
    	 Holdingaccount entity=null;
         try {
                // it must get not load 
        	 System.out.println(new Date().getTime());
        	 Map map=new LinkedHashMap();
                 entity=(Holdingaccount)session.get(Holdingaccount.class, 376l);
                 Set set=entity.getSmses();
                List list=new ArrayList(set);
                Collections.sort(list, new SmsOrder());
                Iterator it=list.iterator(); 
                while(it.hasNext()){
                	Sms sms=(Sms)it.next();
                	map.put(sms.getSmsid()+sms.getStatus(), sms.getDatetime());
                	//System.out.println(sms.getPksmsid());
               }
               Sms firstsms= (Sms)list.get(0);
               System.out.println("getCountofcellno::"+entity.getCountofcellno());
               Sms lastsms= (Sms)list.get(entity.getCountofcellno()+1);
             
               map.get(firstsms.getSmsid()+"m8");
               
               
               
               System.out.println("firstsms::"+firstsms.getStatus());
               System.out.println("lastsms::"+lastsms.getStatus());
               
               System.out.println("firstsms for m8 status::"+map.get(firstsms.getSmsid()+"m8"));
               System.out.println("last for m8 status::"+map.get(lastsms.getSmsid()+"m8"));
               
               
               System.out.println(new Date().getTime());
         } catch (HibernateException e) {
                 e.printStackTrace();
         } finally {
        	 
       	  session.flush();
             session.close();
                session = null;
         }		  
		}
	
	//@Test
	public void MnoSmscproprty() {

		String from = "04/03/2012" + " 00:00:00";
		String to = "04/11/2012" + " 23:59:59";

		Map<Integer, Object> map = new LinkedHashMap<Integer, Object>();
		HoldingaccountManager ha = new HoldingaccountManager();
		List<Holdingaccount> list = ha.getBatches(from, to);

		int count=1;

		if(list!=null && list.size()!=0){
			Iterator<Holdingaccount> itr = list.iterator();
			while (itr.hasNext()) {
				Map<String,Object> tempMap = new LinkedHashMap<String,Object>();
				Holdingaccount haRow = itr.next();
				tempMap.put("Batch Id", haRow.getPkeyholdingaccountid());
				tempMap.put("Batch Size",haRow.getCountofcellno());
				tempMap.put("DateTime", haRow.getTimedate());
				String temp = ha.getSpeedReport(haRow.getPkeyholdingaccountid());
				String[] s = temp.split("&");
				String[] s1 = s[0].split("\\.");
				String[] s2 = s[1].split("\\.");
				Integer is = Integer.parseInt(s1[0]);
				Integer fs = Integer.parseInt(s2[0]);
				Integer sd = is-fs;
				tempMap.put("Initial Speed",is);
				tempMap.put("Final Speed",fs);
				tempMap.put("Speed Drop", sd);
				map.put(count,tempMap);
				count++;
			}
		}
		else{
			map=null;
		}
       
		System.out.println("Map:::"+map);
		Map Sortedmap=new Helper().SortMap(map,"Batch Id","asc");
		System.out.println("Sorted map:::"+Sortedmap);
		
				  
	}
	//	 
	//	  String tempmsg="Test for @$ !& char";
	//			 Properties pro= new Helper().getProps();
	//			 tempmsg= tempmsg.replace("_", pro.getProperty("_"));
	//			 tempmsg= tempmsg.replace("@", pro.getProperty("@")); 
	//			 tempmsg= tempmsg.replace("$", pro.getProperty("$")); 
	//			 System.out.println("_"+pro.getProperty("_"));
	//			 System.out.println(URLEncoder.encode(tempmsg, "UTF-8"));
	//			 System.out.println(URLEncoder.encode(tempmsg));
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}

	//		 Map map= new CreditsManager().getCreditsMap(5l);
	//		 System.out.println(map);
	//		 MnoManager mnom1=new MnoManager();
	//			List listmno=mnom1.getList();
	//		    Iterator itmon=listmno.iterator();
	//		    while(itmon.hasNext()){
	//		        Mno mno=(Mno)itmon.next();
	//		        
	//		        Mnosmscproperty msp= new MnosmscpropertyManager().getMnosmscproperty(mno,70l);
	//		        double smscost=0;
	//		        double replycost=0;
	//		        String sourcenumber="";
	//		        if(msp!=null){
	//		        	 smscost=msp.getSmscost();
	//		        	 replycost=msp.getSmscost();
	//		        	 sourcenumber=msp.getSourcenumber();
	//		        }
	//		        System.out.println("<tr><td></td><td>Details for "+mno.getName()+"</td></tr>");
	//		        System.out.println("<tr>");
	//		        System.out.println("<td>"+mno.getName()+" sms cost</td>");
	//		        System.out.println("<td><input type='text' name='"+mno.getName().trim()+"' value='"+smscost+"' /></td>");
	//		        System.out.println("</tr>");
	//		        System.out.println("<tr>");
	//		        System.out.println("<td>"+mno.getName()+" reply cost</td>");
	//		        System.out.println("<td><input type='text' name='r"+mno.getName().trim()+"' value='"+replycost+"'/></td>");
	//		        System.out.println("</tr>");
	//		        System.out.println("<tr>");
	//		        System.out.println("<td>"+mno.getName()+" Source Address</td>");
	//		        System.out.println("<td><input type='text' value='n/a' name='sa"+mno.getName().trim()+"' value='"+sourcenumber+"' /></td>");
	//		        System.out.println("</tr>");
	//		    }
	//	 }


	//	 @Test
	//	    public void dbtest() {
	//			System.out.print("sdas");
	//			Session session = null;
	//			List list=null;
	//			try {
	//				Calendar cal=Calendar.getInstance();
	//				System.out.println("Time:: "+ cal.getTime());
	//				SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	//				session = sessionFactory.openSession();
	//	            String SQL_QUERY = "select savefeedback('0a609648-ad1c-4bca-90c5-19ca8ce38bb0','16','deliveryInfo','12/12/12','msgdeli','n/a','27711681922')";
	//				System.out.print(SQL_QUERY);
	//			//	Query query = session.createSQLQuery(SQL_QUERY);
	//				
	//			//	list = query.list();
	//			//	System.out.print(list);
	//				
	//				Calendar cal1=Calendar.getInstance();
	//				System.out.println("Time:: "+ cal1.getTime());
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//			} finally {
	//				session.flush();
	//				session.close();
	//				session = null;
	//			}
	//		}
	// @Test
	//  public void getSmsStatus() {

	//	  
	//	  SessionFactory sessionFactory = null;
	//		Session hibersession = null;
	//		try {
	//
	//			sessionFactory = HibernateUtil.getSessionFactory();
	//
	//			hibersession = sessionFactory.openSession();
	//			Transaction tr = hibersession.beginTransaction();
	//
	//			// Mno mno=mm.findById(mnolong);
	//			Mno mno = (Mno) hibersession.get(Mno.class, 18l);
	//
	//			// mno.setName(name);
	//			mno.setSmscost(Double.valueOf("17"));
	//			mno.setReplycost(Double.valueOf("17"));
	//			mno.setSourcenumber("adasdas");
	//			hibersession.update(mno);
	//			tr.commit();
	//		}catch (Exception e) {
	//			// TODO: handle exception
	//		}finally{
	//			hibersession.close();
	//			hibersession = null;
	//		}
	//	  


	//	  SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	//      Session session = sessionFactory.openSession();
	//
	//         Transaction transaction = null;
	//         List<Clientmanager> entity=null;
	//         try {
	//                transaction = session.beginTransaction();
	//                 Criteria crit = session.createCriteria(Clientmanager.class);
	//                 crit.add(Restrictions.eq("isdeleted",false));
	//                 entity= crit.list();
	//                 transaction.commit();
	//         } catch (Exception e) {
	//			e.printStackTrace();
	//			transaction.rollback();
	//         } finally {
	//        	 session.flush();
	// 			session.close();
	// 			session = null;
	// 			transaction = null;
	//         }

	//         ClientmanagersManager cmm= new ClientmanagersManager();
	//        List list=cmm.getList();
	//       
	//        Clientmanager client=cmm.findById(5l);
	//        
	//         
	//         System.out.println(""+client.getCreditaccount().getAvailablecredit());
	//        
	//         for(int i=0;i<list.size();i++){
	//        	 Clientmanager cm=(Clientmanager)list.get(0);
	//        	 System.out.println(""+cm.getCreditaccount().getAvailablecredit());
	//        	 System.out.println(""+cm.getSendinginterface().getSmscproxyproperty().getKannelsmscname());
	////        	Set set= cm.getHoldingaccounts();
	//        	Iterator it=set.iterator();
	//        	while(it.hasNext())
	//        	{
	//        		Holdingaccount ha= (Holdingaccount)it.next();
	//        		 System.out.println("HOlding account details::"+ha.getAfteravailablecredits());
	//        	}


	//        }




	//	@Test
	//  public void getSmsStatus() {
	//		HibernateSessionManager hsm=new HibernateSessionManager();
	//		Session session=null;
	//		try {
	//		 session=hsm.preHandle();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		ClientmanagersManager cmm=new ClientmanagersManager();
	//		Clientmanager admin=cmm.getAdmin(session);
	//		System.System.out.printlnln("creditaccount::"+admin.getCreditaccount());
	//		try {
	//			hsm.afterCompletion(session);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		
	//	}
	//	
	//	
	//	public  String my_time_in(Date date){
	//		String target_time_zone="GMT+2:00";
	//		String format="yyyy-MM-dd HH:mm:ss";
	//		
	//		TimeZone tz = TimeZone.getTimeZone(target_time_zone);
	//	        
	//	     //   Date date = Calendar.getInstance().getTime();
	//	        SimpleDateFormat date_format_gmt = new SimpleDateFormat(format);
	//	        try {
	//				date_format_gmt.parse(date.toString());
	//			} catch (ParseException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//	        date_format_gmt.setTimeZone(tz);
	//	        
	//	        return date_format_gmt.format(date);
	//	    }
	//
	//		@Test
	//	   public void dateTest(){
	//		   SmsManager smsmanager=new SmsManager();
	//		   HibernateSessionManager hsm=new HibernateSessionManager();
	//		   Session session=null;
	//		try {
	//			session = hsm.preHandle();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		  Sms sms= smsmanager.findById(878 , session);
	//		  System.System.out.printlnln("date::" + sms.getDatetime());
	//		  System.System.out.printlnln("date::"+ my_time_in(sms.getDatetime()));
	//		  try {
	//			hsm.afterCompletion(session);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		   
	//	   }

	//	@Test
	//	public void getAdminStatusMessage(){
	//		
	//	List list=new SmsManager().getFailedSms(431l);
	//	log.info("list::"+list.size());

	//		CSVReader reader=null;
	//		try {
	//			reader = new CSVReader(new FileReader("/home/varsha/Downloads/newCSV.csv"));
	//		} catch (FileNotFoundException e1) {
	//			// TODO Auto-generated catch block
	//			e1.printStackTrace();
	//		}
	//	   try {
	//		String[] list=reader.readNext();
	//		System.System.out.printlnln("list ::"+ list.length);
	//		
	//	} catch (IOException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}


	//		SmsManager smsmanager=new SmsManager();
	//		   HibernateSessionManager hsm=new HibernateSessionManager();
	//		   Session session=null;
	//		   int count=0;
	//		try {
	//			session = hsm.preHandle();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		String query=" from Holdingaccount where fkclientmanagerid= 64 and timedate between '11/9/2011 00:00:00' and '11/9/2011 23:59:59'";
	//		
	//		  org.hibernate.Query q= session.createQuery(query);
	//		  System.System.out.printlnln("list size::" + q.list().size());
	//		  List l=q.list();
	//		Iterator it =l.iterator();
	//		 while (it.hasNext()){
	//			 Holdingaccount ha=(Holdingaccount) it.next();
	//			 count=count+ha.getSmscount();
	//		 }
	//		 System.System.out.printlnln("count::"+count);
	////List list = smsmanager.getReportByCellNumber("27843639524", session, "10/01/2011", "10/30/2011");
	////		System.System.out.printlnln("size:: "+list.size() );
	//		try {
	//			hsm.afterCompletion(session);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		




	//	@Test
	//	public void bulkSms(){
	//		UUID uuid = UUID.randomUUID();
	//		TextMessage1 textMessage = new TextMessage1();
	//		String sender = "Default";
	//		
	//		ClientmanagersManager clientmanager = new ClientmanagersManager();
	//
	//		Clientmanager client = clientmanager.findById(5l);
	//		SmscproxyManager smscproxymanager = new SmscproxyManager();
	//		// Smscproxy smscproxy=smscproxymanager.findById();
	//		textMessage.setSmscount(1);
	//		textMessage.setSender(sender);
	//		textMessage.setMessage("Test bulk sms");
	//		textMessage.setFooter("");
	//		// textMessage.setSmsc(smscproxy.getName());
	//		if (client != null) {
	//			textMessage.setSmsc(client
	//					.getSendinginterface().getKannelname());
	//			
	//		}
	//		textMessage.setMessageID(uuid.toString());
	//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	//    	Date date = new Date();
	//    	String dd=dateFormat.format(date).toString();
	//		textMessage.setDatetime(dd);
	//		Long clientmanagerId=5l;
	//		textMessage.setClientmanagerId(clientmanagerId);
	//		List<String> list=new ArrayList<String>();
	//		
	//		list.add("27781746159");
	//		list.add("27781746159");
	//		list.add("27720263771");
	//		list.add("27720263771");
	//		list.add("27723851363  ");
	//		ObserverImpl ob=new ObserverImpl(textMessage);
	//		ob.addBulkToKannel(list);
	//	}
	//	

	//	@Test
	//	public void utilTest(){



	//		log.info("{}",new Date());
	//		new SmsManager().speedReportPerBatchNew("12/01/2011 00:00:00", "12/08/2011 23:59:59");

	//		Calendar cal=Calendar.getInstance();
	//		List list=new SmsobjManager().getFutureSmsList(5l);
	//		log.info("size{}",list.size());
	//		List textMessageList=new ArrayList();
	//		TextMessage1 txtmsg=new TextMessage1();
	//		txtmsg.setFutureDate("2012-01-13 00:00:00");
	//		
	//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	//		
	//		Date dd;
	//		try {
	//			dd = new Date(2012, 01, 13);
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			dd=new Date();
	//			e.printStackTrace();
	//		}
	//		Timestamp timestamp=new Timestamp(dd.getTime());
	//	    log.info("formate date::"+dd);
	//	   
	//		Smsobj so=new Smsobj();
	//		SmsobjManager som=new SmsobjManager();
	//	    so.setFuturedate(timestamp);
	//	    som.add(so);
	//	   
	//	    Date date = new Date();
	//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
	//	    String dd = dateFormat.format(date);
	//		SmsobjManager som = new SmsobjManager();
	//
	//		



	//		log.info("Date::"+dd);
	//			List list = som.getSmsObjectList(dd);
	//		log.info("list size::"+list.size());
	//log.info("size::"+list.size());

	//		List<TextMessage1> smslist=new LinkedList();
	//		TextMessage1 textMessage=new TextMessage1();
	//		textMessage.setSmscount(1);
	//		textMessage.setSender("123");
	//		textMessage.setReciever("123");
	//		textMessage.setMessage("test");
	//		textMessage.setFooter("f");
	//		textMessage.setSmsc("tset");
	//		textMessage.setMessageID("testsmsid");
	//		textMessage.setDatetime("01/05/2012");
	//		textMessage.setSysuserId(4l);
	//		textMessage.setClientmanagerId(5l);
	//		textMessage.setHoldingaccountId(23);
	//		textMessage.setDeliveryinfo("de");
	//		textMessage.setFutureDate("01/05/2011");
	//		smslist.add(textMessage);
	//		
	//		Thread t=new StoredFutureSms(smslist);
	//		t.start();
	//		try{
	//		t.join();
	//		}catch(Exception e){
	//			e.printStackTrace();
	//		}

	//		FutureSms fs=new FutureSms();
	//		log.info("testing finished");
	//		//Date date = new Date();
	//		SmsobjManager som=new SmsobjManager();
	//		
	//		List textMessageList=new LinkedList();
	//		List list=som.getSmsObjectList("02/05/2012");
	//		log.info("sdasdsad::"+list.size());
	//	for(int i=0;i<list.size();i++){
	//		Smsobj smsobj=(Smsobj)list.get(i);
	//		smsobj.setStatus("s"); //S for sent
	//		som.update(smsobj);
	//		TextMessage1 textMessage=new TextMessage1();
	//		textMessage.setSmscount(smsobj.getSmscount());
	//		textMessage.setSender(smsobj.getSender());
	//		textMessage.setReciever(smsobj.getReciever());
	//		textMessage.setMessage(smsobj.getMessage());
	//		textMessage.setFooter(smsobj.getFooter());
	//		textMessage.setSmsc(smsobj.getSmsc());textMessage.setMessageID(smsobj.getSmsid());
	//		//textMessage.setDatetime(new Date().toString());
	//		textMessage.setSysuserId(smsobj.getSysuserid());
	//		textMessage.setClientmanagerId(smsobj.getClientmanager());
	//		textMessage.setHoldingaccountId(smsobj.getHoldingaccountid());
	//		textMessage.setDeliveryinfo(smsobj.getDeliverinfo());
	//		textMessageList.add(textMessage);
	//	}
	//		

	//		SessionFactory sessionFactory=null;
	//		Session session=null;
	//		List list=null;
	//		try{
	//			sessionFactory=HibernateUtil.getSessionFactory();
	//			session=sessionFactory.openSession();
	////			String sql=" from Holdingaccount where timedate < to_date('05 nov 2011', 'DD Mon YYYY')";
	////		     Query q=session.createQuery(sql);
	//				Criteria crt=session.createCriteria(Holdingaccount.class);
	//			crt.add(Restrictions.le("timedate", new Date("11/05/2011")));
	//			//crt.add(Restrictions.between("futuredate", new Date(from),new Date(to)));
	//			//crt.add(Restrictions.eq("status", "l"));
	//			list=crt.list();
	//			log.info("size::"+list.size());
	//		}catch(Exception e){
	//			e.printStackTrace();
	//		}finally{
	//			session.close();
	//			session=null;
	//		}

	//		Map map=new HashMap();
	//		 List<Holdingaccount> list=	new HoldingaccountManager().getBatches(5l, "12/1/2011", "12/15/2011");
	//			SmsManager sm=new SmsManager();
	//			int count=1;
	//		   for(int i=0;i<list.size();i++){
	//			   Holdingaccount ha= list.get(i);
	//			   Map smsCountMap=sm.getBatchSmsStatusCount(ha);
	//			   map.put(count,smsCountMap);
	//			   count++;
	//		   }
	//		   
	//	        Iterator ithd=map.entrySet().iterator();
	//			while(ithd.hasNext()){
	//				Map.Entry pairs=(Map.Entry)ithd.next();
	//				Map subMap=(Map)pairs.getValue();
	//				Iterator subIthd = subMap.entrySet().iterator();
	//				while(subIthd.hasNext()){
	//					System.out.println("<th>");
	//					Map.Entry subpairs1=(Map.Entry)subIthd.next();
	//					System.out.println(subpairs1.getKey());
	//					System.out.println("</th>");
	//		     }
	//				break;
	//		}
	//			System.out.println("</tr>");
	//	        Iterator it=map.entrySet().iterator();
	//			while(it.hasNext()){
	//				Map.Entry pairs=(Map.Entry)it.next();
	//				Map subMap=(Map)pairs.getValue();
	//				System.out.println("<tr>");
	//				System.out.println("<td>");
	//				System.out.println(pairs.getKey());
	//				System.out.println("</td>");
	//				Iterator subIt1 = subMap.entrySet().iterator();
	//				while(subIt1.hasNext()){
	////				    System.System.out.printlnln("day /"+pairs.getKey()+" smssent::"+Subpairs.getKey()+" credit::"+Subpairs.getValue());
	//					System.out.println("<td>");
	//					Map.Entry subpairs1=(Map.Entry)subIt1.next();
	//                   System.out.println(subpairs1.getValue());
	//                  System.out.println("</td>");
	//				  
	//				}  
	//			 System.out.println("</tr>");
	//				  
	//			}
	//			System.out.println("</table >");


}






