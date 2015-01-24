package main.java.stashed;


import com.ecomm.pl4sms.persistence.entities.SMSMessage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class HoldingAccount implements java.io.Serializable {

	private ClientManager clientmanager;
	private Double creditsdeposited;
	private Double creditsleft;
	private Integer smscount;
	private Integer smssentcount;
	private Integer uc;
	private Date dc;
	private Integer um;
	private Date dm;
	private Date timedate;
	private Integer countofcellno;
	private Double afteravailablecredits;
	private Double beforeavailablecredits;
	private Double smsrate;
	private Integer smsfailedcount;
	
	private String clientTransactionReference;
	public String getClientTransactionReference() {
		return clientTransactionReference;
	}

	public String getSystemTransactionReference() {
		return systemTransactionReference;
	}

	public void setClientTransactionReference(String clientTransactionReference) {
		this.clientTransactionReference = clientTransactionReference;
	}

	public void setSystemTransactionReference(String systemTransactionReference) {
		this.systemTransactionReference = systemTransactionReference;
	}

	private String systemTransactionReference;
	
	public Double getSmsrate() {
		return smsrate;
	}

	public void setSmsrate(Double smsrate) {
		this.smsrate = smsrate;
	}

	public Integer getSmsfailedcount() {
		return smsfailedcount;
	}

	public void setSmsfailedcount(Integer smsfailedcount) {
		this.smsfailedcount = smsfailedcount;
	}

	private Set<SMSMessage> smses = new HashSet<SMSMessage>(0);

	public HoldingAccount() {
	}

	public HoldingAccount(long pkeyholdingaccountid) {
		this.pkeyholdingaccountid = pkeyholdingaccountid;
	}

	public HoldingAccount(long pkeyholdingaccountid,
                          ClientManager clientmanager, Double creditsdeposited,
                          Double creditsleft, Integer smscount, Integer smssentcount,
                          Integer uc, Date dc, Integer um, Date dm, Date timedate,
                          Integer countofcellno, Double afteravailablecredits,
                          Double beforeavailablecredits, Set<SMSMessage> smses) {
		this.pkeyholdingaccountid = pkeyholdingaccountid;
		this.clientmanager = clientmanager;
		this.creditsdeposited = creditsdeposited;
		this.creditsleft = creditsleft;
		this.smscount = smscount;
		this.smssentcount = smssentcount;
		this.uc = uc;
		this.dc = dc;
		this.um = um;
		this.dm = dm;
		this.timedate = timedate;
		this.countofcellno = countofcellno;
		this.afteravailablecredits = afteravailablecredits;
		this.beforeavailablecredits = beforeavailablecredits;
		this.smses = smses;
	}

	public long getPkeyholdingaccountid() {
		return this.pkeyholdingaccountid;
	}

	public void setPkeyholdingaccountid(long pkeyholdingaccountid) {
		this.pkeyholdingaccountid = pkeyholdingaccountid;
	}

	public ClientManager getClientmanager() {
		return this.clientmanager;
	}

	public void setClientmanager(ClientManager clientmanager) {
		this.clientmanager = clientmanager;
	}

	public Double getCreditsdeposited() {
		return this.creditsdeposited;
	}

	public void setCreditsdeposited(Double creditsdeposited) {
		this.creditsdeposited = creditsdeposited;
	}

	public Double getCreditsleft() {
		return this.creditsleft;
	}

	public void setCreditsleft(Double creditsleft) {
		this.creditsleft = creditsleft;
	}

	public Integer getSmscount() {
		return this.smscount;
	}

	public void setSmscount(Integer smscount) {
		this.smscount = smscount;
	}

	public Integer getSmssentcount() {
		return this.smssentcount;
	}

	public void setSmssentcount(Integer smssentcount) {
		this.smssentcount = smssentcount;
	}

	public Integer getUc() {
		return this.uc;
	}

	public void setUc(Integer uc) {
		this.uc = uc;
	}

	public Date getDc() {
		return this.dc;
	}

	public void setDc(Date dc) {
		this.dc = dc;
	}

	public Integer getUm() {
		return this.um;
	}

	public void setUm(Integer um) {
		this.um = um;
	}

	public Date getDm() {
		return this.dm;
	}

	public void setDm(Date dm) {
		this.dm = dm;
	}

	public Date getTimedate() {
		return this.timedate;
	}

	public void setTimedate(Date timedate) {
		this.timedate = timedate;
	}

	public Integer getCountofcellno() {
		return this.countofcellno;
	}

	public void setCountofcellno(Integer countofcellno) {
		this.countofcellno = countofcellno;
	}

	public Double getAfteravailablecredits() {
		return this.afteravailablecredits;
	}

	public void setAfteravailablecredits(Double afteravailablecredits) {
		this.afteravailablecredits = afteravailablecredits;
	}

	public Double getBeforeavailablecredits() {
		return this.beforeavailablecredits;
	}

	public void setBeforeavailablecredits(Double beforeavailablecredits) {
		this.beforeavailablecredits = beforeavailablecredits;
	}

	public Set<SMSMessage> getSmses() {
		return this.smses;
	}

	public void setSmses(Set<SMSMessage> smses) {
		this.smses = smses;
	}

}
