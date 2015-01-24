package main.java.stashed;

import com.ecomm.pl4sms.persistence.entities.SMSMessage;
import com.ecomm.pl4sms.persistence.entities.SendingInterface;

import java.util.HashSet;
import java.util.Set;

public class ClientManager implements java.io.Serializable {

	private long pkclientmanagerid;
	
	private SendingInterface sendinginterface;
	private CreditAccount creditaccount;
	private SysUser sysuser;
	
	private Double localsmscost;
	private Double localreplycost;
	private Long createdby;
	private Double internationalsmscost;
	private Double internationalreplycost;
	private Boolean isdeleted;
	
	private Set<SMSMessage> smses = new HashSet<SMSMessage>(0);
	private Set<HoldingAccount> holdingaccounts = new HashSet<HoldingAccount>(0);
	private Set<Credits> creditses = new HashSet<Credits>(0);

	public ClientManager() {
	}

	public ClientManager(long pkclientmanagerid) {
		this.pkclientmanagerid = pkclientmanagerid;
	}

	public ClientManager(long pkclientmanagerid,
                         SendingInterface sendinginterface, CreditAccount creditaccount,
                         SysUser sysuser, Double localsmscost, Double localreplycost,
                         Long createdby, Double internationalsmscost,
                         Double internationalreplycost, Boolean isdeleted, Set<SMSMessage> smses,
                         Set<HoldingAccount> holdingaccounts, Set<Credits> creditses) {
		this.pkclientmanagerid = pkclientmanagerid;
		this.sendinginterface = sendinginterface;
		this.creditaccount = creditaccount;
		this.sysuser = sysuser;
		this.localsmscost = localsmscost;
		this.localreplycost = localreplycost;
		this.createdby = createdby;
		this.internationalsmscost = internationalsmscost;
		this.internationalreplycost = internationalreplycost;
		this.isdeleted = isdeleted;
		this.smses = smses;
		this.holdingaccounts = holdingaccounts;
		this.creditses = creditses;
	}


}
