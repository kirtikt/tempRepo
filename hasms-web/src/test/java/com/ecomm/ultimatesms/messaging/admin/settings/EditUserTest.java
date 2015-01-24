package com.ecomm.ultimatesms.messaging.admin.settings;


public class EditUserTest {

//  @Test
//  public void EditUser() {
//	   System.out.println("*******************************/ Testing EditUser /***************************");
//	    String userId = "test1234";
//		
//		Long sendinginterface = 2l;
//		Double smscost = 23d;
//		long clientmanagerId=1l;
//		Long pkeyManagerid=10l;
//		
//		System.out.println("userid "+userId);
//		
//		System.out.println("sendinginterface "+sendinginterface);
//		System.out.println("smscost "+smscost);
//
//		System.out.println("clinetmanagerId "+clientmanagerId);
//		System.out.println("edited user id "+pkeyManagerid);
//		
//		SysuserManager usermanager = new SysuserManager();
//		ClientmanagersManager clientmanager=new ClientmanagersManager();
//		
//		SendingInterfaceManager sendinginterfacemanager=new SendingInterfaceManager();
//		if (userId != null && 
//				!("".equals(userId)) && !("null".equals(userId))
//				) {
//			System.out.println("Adding user to database.");
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
//			
//			
//			Sysuser user=cm.getSysuser();
//			
//			user.setUsername(userId);
//			user.setUserid(userId);
//			
//			
//			
//			user.setUc((int) clientmanagerId);
//			user.setDc(new Date());
//			try {
//				hsm.afterCompletion(sess);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			usermanager.update(user);
//			
//			cm.setLocalsmscost(smscost);
//			
//			try {
//				 sess =hsm.preHandle();
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			cm.setSendinginterface(sendinginterfacemanager.findById(sendinginterface,sess));
//			try {
//				hsm.afterCompletion(sess);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			clientmanager.update(cm);
//			System.out.println("Successfully Updated ");
//		  
//		}
//		
//  }
}
