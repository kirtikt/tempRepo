package main.java.stashed;

// Generated Dec 1, 2011 6:05:27 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Profit generated by hbm2java
 */
public class Profit implements java.io.Serializable {

	private long pkprofitid;
	private Long profitedusermanagerid;
	private Double profit;
	private Date datetime;
	private Long profitedbyusermanagerid;

	public Profit() {
	}

	public Profit(long pkprofitid) {
		this.pkprofitid = pkprofitid;
	}

	public Profit(long pkprofitid, Long profitedusermanagerid, Double profit,
			Date datetime, Long profitedbyusermanagerid) {
		this.pkprofitid = pkprofitid;
		this.profitedusermanagerid = profitedusermanagerid;
		this.profit = profit;
		this.datetime = datetime;
		this.profitedbyusermanagerid = profitedbyusermanagerid;
	}

	public long getPkprofitid() {
		return this.pkprofitid;
	}

	public void setPkprofitid(long pkprofitid) {
		this.pkprofitid = pkprofitid;
	}

	public Long getProfitedusermanagerid() {
		return this.profitedusermanagerid;
	}

	public void setProfitedusermanagerid(Long profitedusermanagerid) {
		this.profitedusermanagerid = profitedusermanagerid;
	}

	public Double getProfit() {
		return this.profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Date getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public Long getProfitedbyusermanagerid() {
		return this.profitedbyusermanagerid;
	}

	public void setProfitedbyusermanagerid(Long profitedbyusermanagerid) {
		this.profitedbyusermanagerid = profitedbyusermanagerid;
	}

}
