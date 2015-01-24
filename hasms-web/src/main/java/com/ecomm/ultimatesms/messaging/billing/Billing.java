package com.ecomm.ultimatesms.messaging.billing;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecomm.ultimatesms.messaging.commons.Commons;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ClientmanagersManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.CreditaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.HoldingaccountManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.MnosmscpropertyManager;
import com.ecomm.pl4sms.persistence.dbutilsCRUD.ProfitManager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Clientmanager;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Creditaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Holdingaccount;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mno;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Mnosmscproperty;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Profit;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Role;
import com.ecomm.ultimatesms.messaging.persistence.pojos.Sendinginterface;

public class Billing {

	Logger log = LoggerFactory.getLogger(Billing.class);

	public int getSmsCount(String message) {
		int msgLen = message.length();
		int smsCount = 0;
		if (msgLen < 160)
			smsCount = 1;
		if (msgLen > 160 && msgLen <= 312)
			smsCount = 2;
		if (msgLen > 312 && msgLen <= 464)
			smsCount = 3;
		if (msgLen > 464 && msgLen <= 616)
			smsCount = 4;
		if (msgLen > 616 && msgLen <= 768)
			smsCount = 5;
		if (msgLen > 768 && msgLen <= 920)
			smsCount = 6;
		if (msgLen > 920 && msgLen <= 1072)
			smsCount = 7;
		if (msgLen > 1072 && msgLen <= 1224)
			smsCount = 8;
		if (msgLen > 1224 && msgLen <= 1376)
			smsCount = 9;
		if (msgLen > 1376 && msgLen <= 1528)
			smsCount = 10;
		if (msgLen > 1528)
			return 0;
		return smsCount;
	}

	public synchronized boolean checkClientCredit(long managerid, int smscount,
			int countOfCellNo) {
		boolean isCreditAvailable = false;

		if (smscount != 0 && countOfCellNo != 0) {
			String errormsg = "";
			ClientmanagersManager clientManger = new ClientmanagersManager();

			Clientmanager client = clientManger.findById(managerid);

			Creditaccount ca = client.getCreditaccount();
			Double credits = ca.getAvailablecredit();
			Double smscost = client.getLocalsmscost();
			client = null;

			/*
			 * Calculate the credits needed
			 */
			Double creditsneeded = smscost * countOfCellNo * smscount;
			/*
			 * Check the credits availability if credits available value will be
			 * true
			 */
			if (credits >= creditsneeded)
				isCreditAvailable = true;
			/*
			 * Check the credits availability if credits not available value
			 * will be false
			 */
			else {
				isCreditAvailable = false;
				errormsg = errormsg + managerid
						+ "Don't have sufficent balance ";
			}
			clientManger = null;

			client = null;
		}
		return isCreditAvailable;
	}

	// public synchronized boolean checkClientCredit(long managerid, int
	// smscount,
	// int countOfCellNo ) {
	// boolean isCreditAvailable=false;
	//
	// if(smscount!=0 && countOfCellNo!=0){
	// String errormsg = "";
	// ClientmanagersManager clientManger = new ClientmanagersManager();
	//
	// Clientmanager client = clientManger.findById(managerid,sess);
	//
	// Creditaccount ca = client.getCreditaccount();
	// Double credits = ca.getAvailablecredit();
	// Double smscost = client.getLocalsmscost();
	// client=null;
	// /*
	// * Calculate the credits needed
	// */
	// Double creditsneeded = smscost * countOfCellNo * smscount;
	// /*
	// * Check the credits availability if credits available value will be
	// * true
	// */
	// if (credits >= creditsneeded)
	// isCreditAvailable = true;
	// /*
	// * Check the credits availability if credits not available value will be
	// * false
	// */
	// else {
	// isCreditAvailable = false;
	// errormsg = errormsg + managerid + "Don't have sufficent balance ";
	// }
	// clientManger=null;
	//
	// client=null;
	// }
	// return isCreditAvailable;
	// }

	public synchronized Double getCreditsNeeded(long managerid, int smscount,
			int countOfCellNo) {
		// boolean isCreditAvailable=false;
		Double creditsneeded = null;
		if (smscount != 0 && countOfCellNo != 0) {
			// String errormsg = "";
			ClientmanagersManager clientManger = new ClientmanagersManager();
			Clientmanager client = clientManger.findById(managerid);

			Creditaccount ca = client.getCreditaccount();
			Double credits = ca.getAvailablecredit();
			Double smscost = client.getLocalsmscost();
			client = null;
			/*
			 * Calculate the credits needed
			 */
			creditsneeded = smscost * countOfCellNo * smscount;
			clientManger = null;
		}

		return creditsneeded;
	}

	public synchronized boolean checkAdminCredit(long managerid, int smscount,
			int countOfCellNo) {

		if (smscount != 0 && countOfCellNo != 0) {
			ClientmanagersManager clientManger = new ClientmanagersManager();
			Clientmanager client = clientManger.findById(managerid);

			client.getSendinginterface().getLocalsmscost();
			// long pkey = client.getFksmscid();
			// SmscproxyManager smscProxyManager = new SmscproxyManager();
			// Smscproxy smsc = smscProxyManager.findById(pkey);

			/*
			 * Read admin credits
			 */
			Clientmanager admin = clientManger.getAdmin();
			Sendinginterface sn = client.getSendinginterface();
			Double adminsmsCost = sn.getLocalsmscost();
			// log.info("adminsmsCost"+adminsmsCost);
			Double credits = admin.getCreditaccount().getAvailablecredit();

			// long credits = smsc.getAvailablecredits();
			// int smscost = smsc.getSmscost();
			// int adminsmsCost=client.getSendinginterface().getLocalsmscost();

			/*
			 * Calculate the credits needed
			 */

			clientManger = null;
			client = null;
			Double creditsneeded = adminsmsCost * countOfCellNo * smscount;
			if (credits > creditsneeded)
				return true;
			else
				return false;
		}
		return false;
	}

	// public synchronized boolean checkAdminCredit(long managerid, int
	// smscount,
	// int countOfCellNo) {
	//
	//
	//
	// if(smscount!=0 && countOfCellNo!=0){
	// ClientmanagersManager clientManger = new ClientmanagersManager();
	// Clientmanager client = clientManger.findById(managerid,sess);
	//
	// client.getSendinginterface().getLocalsmscost();
	// // long pkey = client.getFksmscid();
	// // SmscproxyManager smscProxyManager = new SmscproxyManager();
	// // Smscproxy smsc = smscProxyManager.findById(pkey);
	//
	// /*
	// * Read admin credits
	// */
	// Clientmanager admin=clientManger.getAdmin(sess);
	// Sendinginterface sn=client.getSendinginterface();
	// Double adminsmsCost=sn.getLocalsmscost();
	// log.info("adminsmsCost"+adminsmsCost);
	// Double credits=admin.getCreditaccount().getAvailablecredit();
	//
	//
	// //long credits = smsc.getAvailablecredits();
	// //int smscost = smsc.getSmscost();
	// //int adminsmsCost=client.getSendinginterface().getLocalsmscost();
	//
	// /*
	// * Calculate the credits needed
	// */
	// clientManger=null;
	// client=null;
	// Double creditsneeded = adminsmsCost * countOfCellNo * smscount;
	// if (credits > creditsneeded)
	// return true;
	// else
	// return false;
	// }
	// return false;
	// }

	public synchronized Holdingaccount depositCredit(long managerid,
			int smscount, int countOfCellNo) {

		ClientmanagersManager clientManger = new ClientmanagersManager();
		Clientmanager client = clientManger.findById(managerid);
		Creditaccount ca = client.getCreditaccount();
		Double credits = ca.getAvailablecredit();
		Double smscost = client.getLocalsmscost();
		/*
		 * Calculate the credits needed
		 */
		Double creditsneeded = smscost * countOfCellNo * smscount;
		Double leftCredit = credits - creditsneeded;
		Holdingaccount ha = null;
		

		/*
		 * Check the credits availability if credits available value will be
		 * true
		 */
		// log.info("credits :: "+credits+":::"+leftCredit);
		if (credits > creditsneeded) {
			// log.info("updated credit");
			ca.setAvailablecredit(leftCredit);
			CreditaccountManager creditaccountManager = new CreditaccountManager();
			creditaccountManager.update(ca);
			/*
			 * Create HoldingAccount instance. 
			 */
		    ha=new Holdingaccount();
			ha.setClientmanager(client);
			ha.setBeforeavailablecredits(credits);
			ha.setAfteravailablecredits(leftCredit);
			ha.setCreditsdeposited(creditsneeded);
			ha.setCreditsleft(creditsneeded);    // Same as the deposited credits  the it will be deducted when we get the status 8 + message id. 
			ha.setSmscount(smscount); // for 160 char is 1 message
			ha.setCountofcellno(countOfCellNo); // Total No of recipients in the list
			ha.setSmssentcount(0);  // At start this is 0. The count will be increment by 1 ,when we'll get status 8 + msg id.
			ha.setSmsfailedcount(0); // At start this is 0. The count will be increment by 1 ,when we'll get status 16.
			ha.setSmsrate(smscost);   // per sms cost for this batch
			ha.setTimedate(new Date());
			ha.setUc((int) client.getSysuser().getPkuserid());
			ha.setDc(new Date());
			HoldingaccountManager holdingaccountManager = new HoldingaccountManager();
			holdingaccountManager.add(ha);
		}
		/*
		 * Check the credits availability if credits not available value will be
		 * false
		 */
		else {
			log.info("Don't have sufficent balance ");
		}
		/*
		 * Add entry in holding account
		 */
		return ha;

	}

	// public static synchronized void takeoffCredits(long manageridSender, int
	// smscount, long holdingaccountid){
	// ClientmanagersManager clientManger = new ClientmanagersManager();
	//
	// Clientmanager client = clientManger.findById(manageridSender);
	// Double smsCost=client.getLocalsmscost();
	//
	// /*
	// * Take off admins credit
	// */
	//
	// //long smscid=client.getFksmscid();
	// //SmscproxyManager smscManger=new SmscproxyManager();
	// //Smscproxy smsc=smscManger.findById(smscid);
	//
	// //int SMSCsmsCost=smsc.getSmscost();
	// Sendinginterface sn=null;
	// Double adminsmsCost=0d;
	//
	// sn=client.getSendinginterface();
	// adminsmsCost=sn.getLocalsmscost();
	// Clientmanager admin=clientManger.getAdmin();
	//
	//
	// Creditaccount adminCreditaccount =admin.getCreditaccount();
	// Double credits=adminCreditaccount.getAvailablecredit();
	// /*
	// * Reduce smsc credits
	// */
	// Double crditsDeducted=credits-(adminsmsCost*smscount);
	// adminCreditaccount.setAvailablecredit(crditsDeducted);
	// // smscManger.update(smsc);
	//
	// CreditaccountManager creditaccountManager=new CreditaccountManager();
	//
	// creditaccountManager.update(adminCreditaccount);
	// /*
	// * Update to holding account. Where status 8
	// */
	//
	// HoldingaccountManager holdingaccountManager=new HoldingaccountManager();
	// Holdingaccount ha=holdingaccountManager.findById(holdingaccountid);
	// Double credit= ha.getCreditsleft();
	// Double leftCredit=credit-(smsCost*smscount);
	// ha.setCreditsleft(leftCredit);
	// ha.setSmssentcount(ha.getSmssentcount()+1);
	//
	// holdingaccountManager.update(ha);
	//
	// }
	public static synchronized void takeoffCredits(String destination,
			Clientmanager manageridSender, int smscount,
			Holdingaccount holdingaccountid) {
		ClientmanagersManager clientManger = new ClientmanagersManager();

		// Clientmanager client = clientManger.findById(manageridSender);
		Clientmanager client = manageridSender;

		Double smsCost = client.getLocalsmscost();

		Sendinginterface sn = null;
		Double adminsmsCost = 0d;

		sn = client.getSendinginterface();

		Mno mno = new Commons().searchForMno(destination);
		if (mno != null) {
			Mnosmscproperty msp = new MnosmscpropertyManager()
					.getMnoSmscProperty(mno, sn);

			// adminsmsCost=sn.getLocalsmscost();
			adminsmsCost = msp.getSmscost();
		} else {
			adminsmsCost = 14d;
		}

		Clientmanager admin = clientManger.getAdmin();

		Creditaccount adminCreditaccount = admin.getCreditaccount();
		Double credits = adminCreditaccount.getAvailablecredit();
		/*
		 * Reduce smsc credits
		 */
		Double crditsDeducted = credits - (adminsmsCost * smscount);
		adminCreditaccount.setAvailablecredit(crditsDeducted);
		// smscManger.update(smsc);

		CreditaccountManager creditaccountManager = new CreditaccountManager();

		creditaccountManager.update(adminCreditaccount);
		/*
		 * Update to holding account. Where status 8
		 */

		HoldingaccountManager holdingaccountManager = new HoldingaccountManager();
		// Holdingaccount ha=holdingaccountManager.findById(holdingaccountid);
		Holdingaccount ha = holdingaccountid;
		Double credit = ha.getCreditsleft();
		Double leftCredit = credit - (smsCost * smscount);
		ha.setCreditsleft(leftCredit);
		ha.setSmssentcount(ha.getSmssentcount() + 1);

		holdingaccountManager.update(ha);

	}

	public static synchronized void addProfitToreseller(long manageridSender,
			int smscount) {
		Logger log = LoggerFactory.getLogger(Billing.class);
		ClientmanagersManager clientManger = new ClientmanagersManager();

		Clientmanager client = clientManger.findById(manageridSender);
		Creditaccount ca = client.getCreditaccount();
		Double smsCost = client.getLocalsmscost();

		long pkey = client.getCreatedby();
		Clientmanager createdbyclient = clientManger.findById(pkey);
		Role role1 = createdbyclient.getSysuser().getRole();
		String roleName1 = role1.getRolename();
		long roleid1 = role1.getPkroleid();

		// log.info("role 1 ::" + roleid1 + "roleName 1 ::" + roleName1);

		if ("sysadmin".equalsIgnoreCase(roleName1.trim())) {
			// log.info("its created by sysadmin");

			return;
		}
		Creditaccount creditAccountOfcreatedBy = createdbyclient
				.getCreditaccount();

		Double creditsofCredtedBy = creditAccountOfcreatedBy
				.getAvailablecredit();
		Double smsCostcreatedby = createdbyclient.getLocalsmscost();

		// log.info("smsCost:: " + smsCost + " smsCostcreatedby:: "+
		// smsCostcreatedby);
		/*
		 * Calculate profit for reseller
		 */
		Double profitPerSms = smsCost - smsCostcreatedby;
		Double actualProfit = profitPerSms * smscount;

		// log.info("smsCost:: " + smsCost + " smsCostcreatedby:: "/+
		// smsCostcreatedby + " profit:: " + actualProfit);
		/*
		 * added profit to reseller
		 */
		creditAccountOfcreatedBy.setAvailablecredit(creditsofCredtedBy
				+ actualProfit);
		CreditaccountManager creditaccountManager = new CreditaccountManager();
		/*
		 * Updated to database
		 */

		creditaccountManager.update(creditAccountOfcreatedBy);
		/*
		 * Add profit of reseller in profit table
		 */
		Profit profit = new Profit();
		profit.setDatetime(new Date());
		profit.setProfit(actualProfit);
		profit.setProfitedusermanagerid(createdbyclient.getPkclientmanagerid());
		profit.setProfitedbyusermanagerid(client.getPkclientmanagerid());
		ProfitManager profitManager = new ProfitManager();
		profitManager.add(profit);
		/*
		 * Check for reseller
		 */
		long pkeyOfparent = createdbyclient.getCreatedby();

		Clientmanager parent = clientManger.findById(pkeyOfparent);
		Role role = parent.getSysuser().getRole();
		String roleName = role.getRolename();
		long roleid = role.getPkroleid();
		// log.info("role::" + roleid + "roleName::" + roleName);
		/*
		 * If parent is sysadmin(According to role) Then its direct client if is
		 * not a sysadmin.
		 */

		clientManger = null;
		client = null;
		role = null;
		profit = null;
		creditaccountManager = null;
		parent = null;
		if (roleid != 1l && !("sysadmin".equalsIgnoreCase(roleName.trim()))) {
			addProfitToreseller(pkey, smscount);
		}

	}

	public static synchronized void addAdminCredits(long manageridSender,
			int smscount, long holdingaccountid) {
		Logger log = LoggerFactory.getLogger(Billing.class);
		ClientmanagersManager clientManger = new ClientmanagersManager();

		Clientmanager client = clientManger.findById(manageridSender);
		Double smsCost = client.getLocalsmscost();

		Sendinginterface sn = client.getSendinginterface();
		Double adminsmsCost = sn.getLocalsmscost();
		Clientmanager admin = clientManger.getAdmin();
		Creditaccount adminCreditaccount = admin.getCreditaccount();
		Double credits = adminCreditaccount.getAvailablecredit();
		/*
		 * add admin credits
		 */

		Double crditsAdded = credits + (adminsmsCost * smscount);
		adminCreditaccount.setAvailablecredit(crditsAdded);
		CreditaccountManager creditaccountManager = new CreditaccountManager();
		creditaccountManager.update(adminCreditaccount);
		// log.info(crditsAdded +"This credits are added to admin");

	}

	public static synchronized void reduceProfitToreseller(
			long manageridSender, int smscount) {
		Logger log = LoggerFactory.getLogger(Billing.class);

		ClientmanagersManager clientManger = new ClientmanagersManager();
		Clientmanager client = clientManger.findById(manageridSender);
		Creditaccount ca = client.getCreditaccount();
		Double smsCost = client.getLocalsmscost();

		long pkey = client.getCreatedby();
		Clientmanager createdbyclient = clientManger.findById(pkey);
		Role role1 = createdbyclient.getSysuser().getRole();
		String roleName1 = role1.getRolename();
		long roleid1 = role1.getPkroleid();

		// log.info("role 1 ::" + roleid1 + "roleName 1 ::" + roleName1);

		if ("sysadmin".equalsIgnoreCase(roleName1.trim())) {
			// log.info("its created by sysadmin");

			return;
		}
		Creditaccount creditAccountOfcreatedBy = createdbyclient
				.getCreditaccount();

		Double creditsofCredtedBy = creditAccountOfcreatedBy
				.getAvailablecredit();
		Double smsCostcreatedby = createdbyclient.getLocalsmscost();

		// log.info("smsCost:: " + smsCost + " smsCostcreatedby:: "/+
		// smsCostcreatedby);
		/*
		 * Calculate profit for reseller
		 */
		Double profitPerSms = smsCost - smsCostcreatedby;
		Double actualProfit = profitPerSms * smscount;

		// log.info("smsCost:: " + smsCost + " smsCostcreatedby:: "+
		// smsCostcreatedby + " profit:: " + actualProfit);
		/*
		 * added profit to reseller
		 */
		creditAccountOfcreatedBy.setAvailablecredit(creditsofCredtedBy
				- actualProfit);
		CreditaccountManager creditaccountManager = new CreditaccountManager();

		/*
		 * Updated to database
		 */

		creditaccountManager.update(creditAccountOfcreatedBy);
		/*
		 * Add profit of reseller in profit table
		 */
		Profit profit = new Profit();
		profit.setDatetime(new Date());
		profit.setProfit(-actualProfit);
		profit.setProfitedusermanagerid(createdbyclient.getPkclientmanagerid());
		profit.setProfitedbyusermanagerid(client.getPkclientmanagerid());
		ProfitManager profitManager = new ProfitManager();
		profitManager.add(profit);
		/*
		 * Check for reseller
		 */
		long pkeyOfparent = createdbyclient.getCreatedby();

		Clientmanager parent = clientManger.findById(pkeyOfparent);
		Role role = parent.getSysuser().getRole();
		String roleName = role.getRolename();
		long roleid = role.getPkroleid();
		// log.info("role::" + roleid + "roleName::" + roleName);
		/*
		 * If parent is sysadmin(According to role) Then its direct client if is
		 * not a sysadmin.
		 */

		if (roleid != 1l && !("sysadmin".equalsIgnoreCase(roleName.trim()))) {
			addProfitToreseller(pkey, smscount);
		}

	}

	// public synchronized void returnDepositedcredit(long pkclientmanagerid,
	// int smsCount,
	// long pkeyholdingaccountid) {
	//
	// ClientmanagersManager clientManger=new ClientmanagersManager();
	// Clientmanager client = clientManger.findById(pkclientmanagerid);
	// Double smsCost=client.getLocalsmscost();
	// Double actualSmsCost=smsCost*smsCount;
	// Creditaccount crdAcc=client.getCreditaccount();
	// Double credits=crdAcc.getAvailablecredit();
	// Double availableCredits=credits+actualSmsCost;
	// crdAcc.setAvailablecredit(availableCredits);
	// CreditaccountManager creditaccountManager=new CreditaccountManager();
	// creditaccountManager.update(crdAcc);
	//
	// /*
	// * release credit from holding account
	// */
	//
	// HoldingaccountManager holdingaccountManager=new HoldingaccountManager();
	// Holdingaccount ha=holdingaccountManager.findById(pkeyholdingaccountid);
	// Double credit= ha.getCreditsleft();
	// Double leftCredit=credit-(actualSmsCost);
	// ha.setCreditsleft(leftCredit);
	//
	// /*
	// * in if only if we get status 16 after 8
	// */
	// // if(isReduceCellCount){
	// ha.setSmssentcount(ha.getSmssentcount()+1);
	// // }
	//
	// //ha.setSmssentcount(ha.getSmssentcount()+1);
	//
	// holdingaccountManager.update(ha);
	// }

	public synchronized void returnDepositedcredit(Clientmanager clientmanager,
			int smsCount, Holdingaccount holdingaccount) {

		// ClientmanagersManager clientManger=new ClientmanagersManager();
		// Clientmanager client = clientManger.findById(pkclientmanagerid);
		Clientmanager client = clientmanager;
		Double smsCost = client.getLocalsmscost();
		Double actualSmsCost = smsCost * smsCount;
		Creditaccount crdAcc = client.getCreditaccount();
		Double credits = crdAcc.getAvailablecredit();
		Double availableCredits = credits + actualSmsCost;
		crdAcc.setAvailablecredit(availableCredits);
		CreditaccountManager creditaccountManager = new CreditaccountManager();
		creditaccountManager.update(crdAcc);

		/*
		 * release credit from holding account
		 */

		HoldingaccountManager holdingaccountManager = new HoldingaccountManager();
		// Holdingaccount
		// ha=holdingaccountManager.findById(pkeyholdingaccountid);
		Holdingaccount ha = holdingaccount;
		Double credit = ha.getCreditsleft();
		Double leftCredit = credit - (actualSmsCost);
		ha.setCreditsleft(leftCredit);

		/*
		 * in if only if we get status 16 after 8
		 */
		// if(isReduceCellCount){
		ha.setSmssentcount(ha.getSmssentcount() + 1);
		// }

		// ha.setSmssentcount(ha.getSmssentcount()+1);

		holdingaccountManager.update(ha);
	}
	
	public synchronized  void returnDepositedcredit(long pkclientmanagerid, int smsCount,
			long pkeyholdingaccountid) {
	
		ClientmanagersManager clientManger=new ClientmanagersManager();
		Clientmanager client = clientManger.findById(pkclientmanagerid);
		Double smsCost=client.getLocalsmscost();
		Double actualSmsCost=smsCost*smsCount;
		Creditaccount crdAcc=client.getCreditaccount();
		Double credits=crdAcc.getAvailablecredit();
		Double availableCredits=credits+actualSmsCost;
		crdAcc.setAvailablecredit(availableCredits);
		CreditaccountManager creditaccountManager=new CreditaccountManager();
		creditaccountManager.update(crdAcc);
		
		/*
		 * release credit from holding account
		 */
	
		HoldingaccountManager holdingaccountManager=new HoldingaccountManager();
		Holdingaccount ha=holdingaccountManager.findById(pkeyholdingaccountid);
		Double credit= ha.getCreditsleft();
		Double leftCredit=credit-(actualSmsCost);
		ha.setCreditsleft(leftCredit); // Credits refunded to client. Updated the left credit.	 
		ha.setSmsfailedcount(ha.getSmsfailedcount()+1); // incremented the smsfailed count.
		holdingaccountManager.update(ha);
	}

}
