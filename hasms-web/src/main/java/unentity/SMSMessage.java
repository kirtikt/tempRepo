package main.java.unentity;


import java.util.Date;


public class SMSMessage implements java.io.Serializable {


	private String smsid;

	private String source;
	private String destination;
	private String status;
	private Date datetime;
	private Integer smscount;
	private String deliveryinfo;
	private Double creditbefore;
	private Double creditafter;
	private Boolean isnationalnumber;
	private Boolean isbilled;
	private Boolean futurestatus;
	private String identifier;
	private String DeliveryStatusDescription;
	private String clientTransactionReference;
	private String systemTransactionReference;

}
