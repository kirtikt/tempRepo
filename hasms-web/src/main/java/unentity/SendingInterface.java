package main.java.unentity;


import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

public class SendingInterface extends PL4Base {


	private Double localSMSCost;
	private Double localReplyCost;
	private Double internationalSMSCost;
	private Double internationalReplyCost;
	private String interfaceName;
	private String billingCode;
	private String refundCode;
	private String kannelName;


	public SendingInterface() {
	}


}
