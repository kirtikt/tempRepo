//package com.ecomm.ultimatesms.messaging.billing;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.hibernate.Criteria;
//import org.hibernate.HibernateException;
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.id.enhanced.OptimizerFactory.HiLoOptimizer;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.ecomm.pl4sms.persistence.dbutils.HibernateUtil;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.BlacklistManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.HibernateSessionManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmsManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.SmscproxyManager;
//import com.ecomm.pl4sms.persistence.dbutilsCRUD.SysuserManager;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Blacklist;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Clientmanager;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Creditaccount;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Holdingaccount;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Role;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Sendinginterface;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Sms;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Smscproxy;
//import com.ecomm.ultimatesms.messaging.com.ecomm.pl4sms.persistence.pojos.Sysuser;
//
//public class BillingTest {
//
//  @Test
//  public void checkClientCredit() {
//	  System.out.println("******************************** Testing checkClientCredit ***************************");
//	  System.out.println("Test Cases :: 1");
//	  boolean rslt=new Billing().checkClientCredit(5, 2, 1);
//	  Assert.assertEquals(rslt, true);
//	  System.out.println("Test Cases :: 2");
//	  boolean rslt1=new Billing().checkClientCredit(5, 0, 1);
//	  Assert.assertEquals(rslt1, false);
//	  System.out.println("Test Cases :: 3");
//	  boolean rslt2=new Billing().checkClientCredit(5, 1, 0);
//	  Assert.assertEquals(rslt2, false);
//	  System.out.println("Test Cases :: 4");
//	  boolean rslt3=new Billing().checkClientCredit(5, 0, 0);
//	  Assert.assertEquals(rslt3, false);
//  }
//
//  @Test
//  public void checkAdminCredit() {
//	  System.out.println("******************************** Testing checkSmscCredit ***************************");
//		 
//	  System.out.println("Test Cases :: 1");
//	  boolean rslt=new Billing().checkAdminCredit(1, 2, 1);
//	  Assert.assertEquals(rslt, true);
//	  System.out.println("Test Cases :: 3");
//	  boolean rslt1=new Billing().checkAdminCredit(1, 0, 1);
//	  Assert.assertEquals(rslt1, false);
//	  System.out.println("Test Cases :: 4");
//	  boolean rslt2=new Billing().checkAdminCredit(1, 1, 0);
//	  Assert.assertEquals(rslt2, false);
//	  System.out.println("Test Cases :: 5");
//	  boolean rslt3=new Billing().checkAdminCredit(1, 0, 0);
//	  Assert.assertEquals(rslt3, false);
//  }
//
//  @Test
//  public void depositCredit() {
//	  
//	  System.out.println("******************************** Testing depositCredit ***************************");
//	  System.out.println("Testing depositCredit");
//	  ClientmanagersManager clientManger = new ClientmanagersManager();
//	  HibernateSessionManager hsm=new HibernateSessionManager();
//	  Session session=null;
//	try {
//		session = hsm.preHandle();
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//		Clientmanager client = clientManger.findById(5l,session);
//		Creditaccount ca = client.getCreditaccount();
//		Double credits = ca.getAvailablecredit();
//		Double smscost = client.getLocalsmscost();
//		Double creditsneeded = smscost * 1 * 1;
//		Double actual = credits-creditsneeded;
//		
//	    Holdingaccount ha=new Billing().depositCredit(5l, 1, 1);
//	    try {
//			hsm.afterCompletion(session);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    ClientmanagersManager clientManger1 = new ClientmanagersManager();
//	    try {
//			session = hsm.preHandle();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Clientmanager client1 = clientManger1.findById(5l,session);
//		Creditaccount ca1 = client1.getCreditaccount();
//		Double expected = ca1.getAvailablecredit();
//		
//	    Assert.assertEquals(actual, expected);
//	    try {
//			hsm.afterCompletion(session);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    try {
//			session = hsm.preHandle();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    HoldingaccountManager hamanager=new HoldingaccountManager();
//	    Holdingaccount haresult= hamanager.findById(ha.getPkeyholdingaccountid(),session);
//	    try {
//			hsm.afterCompletion(session);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    Assert.assertNotNull(haresult);
//	 
//  }
////
////  @Test
////  public void getSmsCount() {
////	  System.out.println("******************************** Testing getSmsCount ***************************");
////	  String msg=" We live in a wonderfully complex universe, and we are curious about it by nature. Time and again we have wondered why are we here Where did we and the world come from? What is the world made of? It is our privilege to live in a time when enormous progress has been made towards finding some of the answers. String theory is our most recent";
////	 int count=new Billing().getSmsCount(msg);
////	 System.out.println(msg.length());
////	 
////	 Assert.assertEquals(count, 3);
////  }
////  
////  @Test
////  public void takeoffCredits(){
////	  System.out.println("******************************** Testing takeoffCredits ***************************");
////		
////		 ClientmanagersManager clientManger = new ClientmanagersManager();
////		 Clientmanager client = clientManger.findById(5);
////		 int smsCost=client.getLocalsmscost();
////		 Sendinginterface sendingInterface=client.getSendinginterface();
////		 
////		 int SMSCsmsCost=sendingInterface.getLocalsmscost();
////		 /*
////		  * Get available smsc credit
////		  */
////		 ClientmanagersManager cl=new ClientmanagersManager();
////		 long credits=cl.getAdmin().getCreditaccount().getAvailablecredit();
////		 long expected=credits-(SMSCsmsCost*1);
////		 /*
////		  * Test for hollding account
////		  */
////		 HoldingaccountManager  holdingaccountManager=new HoldingaccountManager();
////		 Holdingaccount ha=holdingaccountManager.findById(76);
////		 long creditsHoldingaccount=ha.getCreditsleft();
////		 long expected1=creditsHoldingaccount-(smsCost*1);
////		 
////		 /*
////		  * Call testing method
////		  */
////		 new Billing().takeoffCredits(5, 1, 76);
////		 /*
////		  * get remaining credits
////		  */
////		 ClientmanagersManager cl1=new ClientmanagersManager();
////		 long actual=cl1.getAdmin().getCreditaccount().getAvailablecredit();
////		 Assert.assertEquals(actual, expected);
////		 HoldingaccountManager  holdingaccountManager1=new HoldingaccountManager();
////		 Holdingaccount ha1=holdingaccountManager1.findById(76);
////		 long actual1=ha1.getCreditsleft();
////		
////		 Assert.assertEquals(actual1, expected1);
////		}
////  
////  @Test
////  public void addAdminCredits(){
////	  System.out.println("******************************** Testing addAdminCredits ***************************");
////	    ClientmanagersManager clientManger = new ClientmanagersManager();
////	    HibernateSessionManager hsm=new HibernateSessionManager();
////		  Session session=null;
////		try {
////			session = hsm.preHandle();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		Clientmanager client = clientManger.findById(2,session);
////		Double smscost=client.getSendinginterface().getLocalsmscost();
////		Double atualsmscost=smscost*1;
////		Clientmanager admin=clientManger.getAdmin(session);
////		Double credit=admin.getCreditaccount().getAvailablecredit();
////		Double expected=credit+atualsmscost;
////		try {
////			hsm.afterCompletion(session);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	    Billing.addAdminCredits(2l, 1, 76);
////	    ClientmanagersManager clientManger1 = new ClientmanagersManager();
////	    try {
////			session = hsm.preHandle();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	    Double actual=clientManger1.getAdmin(session).getCreditaccount().getAvailablecredit();
////	    try {
////			hsm.afterCompletion(session);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	    Assert.assertEquals(actual, expected);
////  }
//  @Test 
//  public void reduceProfitToreseller(){
//	  System.out.println("******************************** Testing reduceProfitToreseller ***************************");
//	  new Billing().reduceProfitToreseller(5, 1);
//  }
//
//  @Test 
//  public void returnDepositedcredit(){
//	  new Billing().returnDepositedcredit(5, 1, 76, true);
//  }
////  
////	 @Test
////	public void testFeedback(){
////	SmsManager smsmanager=new SmsManager();
////	/*
////	 * Check that if that sms is exits with status 8 
////	 */
////	
////	/*
////	 * Add entry to sms
////	 */
////	String smsID="296be3de-6c7c-4572-9d29-b7f8fe14f572";
////	String messageSendingID="demo";
////	Sms smsObj=smsmanager.getSmsFromSmsid(smsID);
////	Clientmanager clientManager=smsObj.getClientmanager();
////	int smsCount=smsObj.getSmscount();
////	Holdingaccount ha=smsObj.getHoldingaccount();
////	
////	
////	if("8".equals("8") && messageSendingID!=" " &&  messageSendingID!=null){
////		if(!smsmanager.isSmsexist(smsID, "8")){
////		System.out.println("I am getting status 8 ::::: sms not exist with status 8");
////		System.out.println("Status 8 and added profit to reseller ::");
////		Billing billing=new Billing();
////		billing.takeoffCredits(clientManager.getPkclientmanagerid(), smsCount, ha.getPkeyholdingaccountid());
////		billing.addProfitToreseller(clientManager.getPkclientmanagerid(), smsCount);
////		}
////		else{
////		System.out.println("I am getting status 8 ::::: sms exist with status 8 return from servlet");
////		return;
////		}
////		
////	}
////	if("16".equals("16") && messageSendingID!="" &&  messageSendingID!=null){
////		Billing billing=new Billing();
////		if(smsmanager.isSmsexist(smsID, "8")){
////			System.out.println("I am getting status 16 ::::: smsexist with status 8");
////			billing.returnDepositedcredit(clientManager.getPkclientmanagerid(), smsCount, ha.getPkeyholdingaccountid(),true);
////			billing.addAdminCredits(clientManager.getPkclientmanagerid(), smsCount, ha.getPkeyholdingaccountid());
////			billing.reduceProfitToreseller(clientManager.getPkclientmanagerid(), smsCount);
////		}
////		/*
////		 * Just return clients credit 
////		 */
////		else{
////		billing.returnDepositedcredit(clientManager.getPkclientmanagerid(), smsCount, ha.getPkeyholdingaccountid(),false);
////		}
////	}
////	Sms smsObj1=smsmanager.getSmsFromSmsid(smsID);
////	Clientmanager clientManager1=smsObj1.getClientmanager();
////	int smsCount1=smsObj1.getSmscount();
////	Holdingaccount ha1=smsObj1.getHoldingaccount();
////	
////	Sms sms = new Sms();
////	System.out.println("PKEy at send single sms servlet ==============>:");
////	sms.setSource("test");
////	sms.setSmsid(smsID);
////	sms.setMessage("testFeedback servlet");
////	sms.setClientmanager(clientManager1);
////	sms.setHoldingaccount(smsObj1.getHoldingaccount());
////	
////	long creditAfter=(long)ha1.getCreditsleft();
////	sms.setCreditafter(creditAfter);	
////	sms.setDatetime(new Date());
////	sms.setDestination("123456789");
////	
////	sms.setStatus("8");         
////	sms.setSendinginterface(clientManager1.getSendinginterface());
////	sms.setHoldingaccount(ha1);
////	
////	sms.setSmscount(smsCount1);
////	
////	sms.setUc((int) smsObj1.getClientmanager().getPkclientmanagerid());
////	sms.setDc(new Date());
////    sms.setDeliveryinfo("");
////    sms.setIsbilled(false);
////	smsmanager.add(sms);
////	smsmanager=null;
////	sms=null;
////  
////	 }
////  
////  @Test
////  public void testshowclient(){
////  ClientmanagersManager manager = new ClientmanagersManager();
////	List<Clientmanager> cltList=manager.getList();
////		Iterator<Clientmanager> it = cltList.iterator();
////		boolean flag = false;
////		int count = 0;
////		System.out.println("listsize:"+cltList.size());
////		System.out.print("<tr><td><b>Client id</b></td><td><b>Created by</b></td><td><b>Available Credits</b></td><td><b>Holding Account Detail</b></td><tr>");
////		while (it.hasNext()) {
////			count++;
////			System.out.println("in while");
////			Clientmanager sr = (Clientmanager) it.next();
////			Long id=sr.getCreatedby();
////			Clientmanager createdBy=manager.findById(id);
////			// System.out.println(createdBy.getSysuser().getRole().getRolename()+"("+createdBy.getSysuser().getUserid()+"\')\'");
////			// System.out.println("*****************************************************************");
////			 System.out.print("<tr><td>"
////					+ sr.getSysuser().getUserid()
////					+ "</td><td>"
////					+ createdBy.getSysuser().getRole().getRolename()+"("+createdBy.getSysuser().getUserid()+")</td><td>"
////					+ sr.getCreditaccount().getAvailablecredit()
////					+"</td><td><a href='/payless4sms/adminreport/holdingreportperclient.jsp?pKey="+sr.getPkclientmanagerid()+"'></a>Holding Account Detail</td></tr>");
////		}
////  }
// 
//
//	  class MyIntComparable implements Comparator<Holdingaccount>{
//		 
//		    @Override
//		    public int compare(Holdingaccount o1, Holdingaccount o2) {
//		        return ((o1.getPkeyholdingaccountid()>o2.getPkeyholdingaccountid()) ? -1 : ((o1.getPkeyholdingaccountid()>o2.getPkeyholdingaccountid()) ? 0 : 1));
//		    }
//		}
//	 
//	 
////	 @Test
////	 public void test(){
////	 ClientmanagersManager manager = new ClientmanagersManager();
////		List<Clientmanager> cltList=manager.getList();
////			Iterator<Clientmanager> it = cltList.iterator();
////			boolean flag = false;
////			int count = 0;
////			System.out.print("<tr><td><b>Client id</b></td><td><b>Created by</b></td><td><b>Available Credits</b></td><td><b>Holding Account Detail</b></td><tr>");
////			while (it.hasNext()) {
////				count++;
////				Clientmanager sr = (Clientmanager) it.next();
////				Long id=sr.getCreatedby();
////				Clientmanager createdBy=manager.findById(id);
////				 
////				System.out.print(""
////						+ sr.getSysuser().getUserid()
////						+ ""
////						+ createdBy.getSysuser().getRole().getRolename()+"("+createdBy.getSysuser().getUserid()+")</td><td>"
////						+ sr.getCreditaccount().getAvailablecredit());}
////	 }
//	 
//	 @Test
//	 public void test123(){
//		 int todate=12;
//		 int toMonth=10;
//		 int toYear=2011;
//		 int fromdate=12;
//		 int fromMonth=8;
//		 int fromYear=2011;
//		 
//		 int prvi=0;
//		 Map map=new HashMap();
//		 /*
//		  * If month wise
//		  */
//		 HoldingaccountManager ha= new HoldingaccountManager();
////		 for(int i=fromMonth;i<toMonth;i++){
////			String from = i+"/"+1+"/"+fromYear +" 00:00:00";
////			String to=(i+1)+"/"+ 1 +"/"+fromYear +" 23:59:59";
////			 System.out.println("from::"+from+"to::"+to);
////			//int smsCount=man.getSmsSentCount(from, to);
////		//	map.put(i,smsCount);
////			
////		 }
//		 for(int i=fromMonth;i<toMonth;i++){
//				String from = i+"/"+1+"/"+fromYear +" 00:00:00";
//				String to=(i+1)+"/"+ 1 +"/"+fromYear +" 23:59:59";
//				// System.out.println("from::"+from+"to::"+to);
//				Map smsCountAndCredits=ha.getSmsSentCount(from, to, 5);
//				map.put(i,smsCountAndCredits);
//				
//			 }
//		// int smsCount=man.getSmsSentCount("09/23/2011 00:00:00", "09/23/2011 23:59:59",5);
//		 System.out.println(map);
//		
//			Iterator it=map.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry pairs=(Map.Entry)it.next();
//				Map submap=(HashMap)pairs.getValue();
//				Iterator it1=submap.entrySet().iterator();
//				System.out.println("day /"+pairs.getKey());
//				while(it1.hasNext()){
//					Map.Entry Subpairs=(Map.Entry)it1.next();
//				    System.out.println("day /"+pairs.getKey()+" smssent::"+Subpairs.getKey()+" credit::"+Subpairs.getValue());
////				    out.print("<tr><td>day / "+pairs.getKey()+"</td><td>"+Subpairs.getKey()+"</td><td>"+Subpairs.getValue()+"</td></tr>");
//					
//				}
//			}
////		 List list = new LinkedList(map.entrySet());
////		 
////	        //sort list based on comparator
////	        Collections.sort(list, new Comparator() {
////	             public int compare(Object o1, Object o2) {
////		           return ((Comparable) ((Map.Entry) (o1)).getKey())
////		           .compareTo(((Map.Entry) (o2)).getKey());
////	             }
////		});
////	        System.out.println("*******************************************************");
////	        System.out.println(map);
//	 }
////	@Test 
////	public void testmethod(){
////		Billing bil=new Billing();
////		bil.checkAdminCredit(5, 1, 1);
////		bil.checkClientCredit(5, 1, 1);
////		bil.depositCredit(5, 1, 1);
////		bil.returnDepositedcredit(5, 1, 76, true);
////		bil.addAdminCredits(5, 1, 76);
////		bil.addProfitToreseller(5, 1);
////		bil.reduceProfitToreseller(5, 1);
////		bil.takeoffCredits(5, 1, 76);
////		
////	}
//	
////	@Test 
////	public void deleteClientManager(){
////		//SysuserManager man=new SysuserManager();
////		HibernateSessionManager hsm=new HibernateSessionManager();
////		Session sess=null;
////		try {
////			sess = hsm.preHandle();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		//Sysuser cm=man.findById(4, sess);
////		Query q=sess.createQuery("from Sysuser");
////		List list=q.list();
////		System.out.println("Size::"+list.size());
////		Iterator it=list.iterator();
////		if(it.hasNext()){
////			Sysuser s=(Sysuser)it.next();
////				System.out.println("userid::"+s.getUserid());
////				}
////		
////				try {
////					hsm.afterCompletion(sess);
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////		
////	}
//
//	
//	//	@Test
////	public void testGetList(){
////		ClientmanagersManager cmm=new ClientmanagersManager();
////		HibernateSessionManager hsm=new HibernateSessionManager();
////		Session sess;
////		try {
////			sess = hsm.preHandle();
////		
////		   List<Clientmanager> list=cmm.getList(sess);
////		   Iterator it=list.iterator();
////		   while (it.hasNext()){
////			   System.out.println(((Clientmanager)it.next()).getPkclientmanagerid());
////		   }
////		hsm.afterCompletion(sess);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////	}
//	
//	@Test
//	public void testfinfbyid(){
//		ClientmanagersManager cmm=new ClientmanagersManager();
//		HibernateSessionManager hsm=new HibernateSessionManager();
//		Session sess;
//		try {
//			sess = hsm.preHandle();
//		
//		   Clientmanager cm=cmm.findById(5l, sess);
//		 System.out.println("userid::"+cm.getSysuser().getUserid());
//		hsm.afterCompletion(sess);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//}
