package main.java.stashed;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SMSCProxy implements java.io.Serializable {


	private String name;
	private Long uc;
	private Date dc;
	private Long um;
	private Date dm;
	private Long ud;
	private Date dd;
	private Long availablecredits;
	private Long spendcredits;
	private Integer smscost;
	private Integer replycost;
	private String blandno;
	private String bmobno;
	private String bemailid;
	private String alandno;
	private String amobno;
	private String aemailid;
	private String aphyadd;
	private String tflandno;
	private String tfmobno;
	private String tfemailid;
	private String tslandno;
	private String tsmobno;
	private String tsemailid;
	private Set<SMSCProxyProperty> smscproxyproperties = new HashSet<SMSCProxyProperty>(
			0);
	private Set<AdminCredit> admincredits = new HashSet<AdminCredit>(0);

	public SMSCProxy() {
	}


	public long getPksmscproxyid() {
		return this.pksmscproxyid;
	}

	public void setPksmscproxyid(long pksmscproxyid) {
		this.pksmscproxyid = pksmscproxyid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUc() {
		return this.uc;
	}

	public void setUc(Long uc) {
		this.uc = uc;
	}

	public Date getDc() {
		return this.dc;
	}

	public void setDc(Date dc) {
		this.dc = dc;
	}

	public Long getUm() {
		return this.um;
	}

	public void setUm(Long um) {
		this.um = um;
	}

	public Date getDm() {
		return this.dm;
	}

	public void setDm(Date dm) {
		this.dm = dm;
	}

	public Long getUd() {
		return this.ud;
	}

	public void setUd(Long ud) {
		this.ud = ud;
	}

	public Date getDd() {
		return this.dd;
	}

	public void setDd(Date dd) {
		this.dd = dd;
	}

	public Long getAvailablecredits() {
		return this.availablecredits;
	}

	public void setAvailablecredits(Long availablecredits) {
		this.availablecredits = availablecredits;
	}

	public Long getSpendcredits() {
		return this.spendcredits;
	}

	public void setSpendcredits(Long spendcredits) {
		this.spendcredits = spendcredits;
	}

	public Integer getSmscost() {
		return this.smscost;
	}

	public void setSmscost(Integer smscost) {
		this.smscost = smscost;
	}

	public Integer getReplycost() {
		return this.replycost;
	}

	public void setReplycost(Integer replycost) {
		this.replycost = replycost;
	}

	public String getBlandno() {
		return this.blandno;
	}

	public void setBlandno(String blandno) {
		this.blandno = blandno;
	}

	public String getBmobno() {
		return this.bmobno;
	}

	public void setBmobno(String bmobno) {
		this.bmobno = bmobno;
	}

	public String getBemailid() {
		return this.bemailid;
	}

	public void setBemailid(String bemailid) {
		this.bemailid = bemailid;
	}

	public String getAlandno() {
		return this.alandno;
	}

	public void setAlandno(String alandno) {
		this.alandno = alandno;
	}

	public String getAmobno() {
		return this.amobno;
	}

	public void setAmobno(String amobno) {
		this.amobno = amobno;
	}

	public String getAemailid() {
		return this.aemailid;
	}

	public void setAemailid(String aemailid) {
		this.aemailid = aemailid;
	}

	public String getAphyadd() {
		return this.aphyadd;
	}

	public void setAphyadd(String aphyadd) {
		this.aphyadd = aphyadd;
	}

	public String getTflandno() {
		return this.tflandno;
	}

	public void setTflandno(String tflandno) {
		this.tflandno = tflandno;
	}

	public String getTfmobno() {
		return this.tfmobno;
	}

	public void setTfmobno(String tfmobno) {
		this.tfmobno = tfmobno;
	}

	public String getTfemailid() {
		return this.tfemailid;
	}

	public void setTfemailid(String tfemailid) {
		this.tfemailid = tfemailid;
	}

	public String getTslandno() {
		return this.tslandno;
	}

	public void setTslandno(String tslandno) {
		this.tslandno = tslandno;
	}

	public String getTsmobno() {
		return this.tsmobno;
	}

	public void setTsmobno(String tsmobno) {
		this.tsmobno = tsmobno;
	}

	public String getTsemailid() {
		return this.tsemailid;
	}

	public void setTsemailid(String tsemailid) {
		this.tsemailid = tsemailid;
	}

	public Set<SMSCProxyProperty> getSmscproxyproperties() {
		return this.smscproxyproperties;
	}

	public void setSmscproxyproperties(
			Set<SMSCProxyProperty> smscproxyproperties) {
		this.smscproxyproperties = smscproxyproperties;
	}

	public Set<AdminCredit> getAdmincredits() {
		return this.admincredits;
	}

	public void setAdmincredits(Set<AdminCredit> admincredits) {
		this.admincredits = admincredits;
	}

}
