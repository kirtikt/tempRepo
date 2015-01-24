package com.ecomm.ultimatesms.messaging.admin.settings;


public class AddclientTest {

//	@Test
//	public void add() {
//		System.out.println("In Register User.");
//		String userId = "a";
//		String password = "a";
//		String clientRef = "a";
//		long sendinginterface = 4;
//		long role = 1;
//		Double credit = 10000d;
//		Double smscost = 12d;
//		Double replycost = 14d;
//
//		long clinetmanagerId = 2;
//
//		// RequestDispatcher rd=null;
//		SysuserManager usermanager = new SysuserManager();
//		RoleManager rolemanager = new RoleManager();
//		ClientmanagersManager clientmanager = new ClientmanagersManager();
//		Clientmanager clientmg = new Clientmanager();
//		CreditaccountManager creditaccountmanager = new CreditaccountManager();
//		Creditaccount creditaccount = new Creditaccount();
//		SendingInterfaceManager sendinginterfacemanager = new SendingInterfaceManager();
//
//		if (userId != null && password != null && clientRef != null
//				&& !("".equals(userId)) && !("".equals(password))
//				&& !("".equals(clientRef)) && !("null".equals(userId))
//				&& !("null".equals(password)) && !("null".equals(clientRef))) {
//			System.out.println("Adding user to database.");
//			HibernateSessionManager hsm = new HibernateSessionManager();
//			Session sess = null;
//			try {
//
//				sess = hsm.preHandle();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			Sysuser user = new Sysuser();
//			user.setUsername(userId);
//			user.setUserid(userId);
//			user.setClientref(clientRef);
//			user.setPassword(password);
//			user.setRole(rolemanager.findById(role, sess));
//			user.setUc((int) clinetmanagerId);
//			user.setDc(new Date());
//			usermanager.add(user);
//
//			creditaccount.setAvailablecredit(credit);
//			creditaccount.setSpendcredit( 0d);
//			creditaccount.setUc((int) clinetmanagerId);
//			creditaccount.setDc(new Date());
//			creditaccount.setPurchasedby(clinetmanagerId);
//			creditaccount.setPurchaseddate(System.currentTimeMillis());
//			creditaccountmanager.add(creditaccount);
//
//			clientmg.setLocalsmscost(smscost);
//			clientmg.setLocalreplycost(replycost);
//			clientmg.setSysuser(user);
//			clientmg.setCreatedby(clinetmanagerId);
//			clientmg.setCreditaccount(creditaccount);
//			clientmg.setSendinginterface(sendinginterfacemanager.findById(
//					sendinginterface, sess));
//			clientmanager.add(clientmg);
//
//			CreditsManager creditmanager = new CreditsManager();
//			Credits credits = new Credits();
//			credits.setPurchasedamount(credit);
//			credits.setPurchasedby(clinetmanagerId);
//			credits.setPurchaseddate(new Date());
//			credits.setClientmanager(clientmg);
//			creditmanager.add(credits);
//
//			try {
//				hsm.afterCompletion(sess);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
//	}

}
