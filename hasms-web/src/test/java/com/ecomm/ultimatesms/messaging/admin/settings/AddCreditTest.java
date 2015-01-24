package com.ecomm.ultimatesms.messaging.admin.settings;


public class AddCreditTest {

//  @Test
//  public void AddCredit() {
//	 
//		System.out.println();
//		Long  credit =  200l;
//		Long pkeyManagerid=5l;
//		
//		long clientmanagerId=1l;
//		System.out.println("credit "+credit);
//		System.out.println("clinetmanagerId "+clientmanagerId);
//		
//		RequestDispatcher rd=null;
//	
//		ClientmanagersManager clientmanager=new ClientmanagersManager();
//		CreditaccountManager creditaccountmanager=new CreditaccountManager();
//		
//		if (credit != null && !("".equals(credit)) && !("null".equals(credit))) {
//			
//			Session sess=null;
//			HibernateSessionManager  hsm= new HibernateSessionManager();
//			try {
//				
//				 sess =hsm.preHandle();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Clientmanager cm=clientmanager.findById(pkeyManagerid, sess);
//			Creditaccount creditaccount=cm.getCreditaccount();
//			Double accpected=creditaccount.getAvailablecredit()+credit;
//			creditaccount.setAvailablecredit(creditaccount.getAvailablecredit()+credit);
//			creditaccount.setUc((int) clientmanagerId);
//			creditaccount.setDc(new Date());
//			creditaccount.setPurchasedby(clientmanagerId);
//			creditaccount.setPurchaseddate(System.currentTimeMillis());
//			try {
//				hsm.afterCompletion(sess);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			creditaccountmanager.update(creditaccount);
//		     
//			try {
//				
//				 sess =hsm.preHandle();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Clientmanager cm1=clientmanager.findById(pkeyManagerid, sess);
//			Double actualcredit=cm1.getCreditaccount().getAvailablecredit();
//			
//			try {
//				hsm.afterCompletion(sess);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Assert.assertEquals(actualcredit, accpected);
////			CreditsManager creditmanager=new CreditsManager();
////			Credits credits=new Credits();
////			credits.setPurchasedamount(credit);
////			credits.setPurchasedby(clientmanagerId);
////			credits.setPurchaseddate(new Date());
////			credits.setClientmanager(cm);
////			creditmanager.add(credits);
////			
//			
//  }
//  }
}
