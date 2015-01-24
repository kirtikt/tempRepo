package main.java.stashed;


import com.ecomm.pl4sms.persistence.entities.MobileNetworkOperator;
import com.ecomm.pl4sms.persistence.entities.PL4Base;
import com.ecomm.pl4sms.persistence.entities.SendingInterface;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class MNOSMSCProperty extends PL4Base {

	private SendingInterface sendinginterface;
	private MobileNetworkOperator mnoDetails;
	private Boolean concatenation;
	private String offeredInterface;
	private Boolean deliveryReceipts;
	private Double smsCost;
	private Double replyCost;
	private String sourceNumber;
	private Boolean alphaNumericSenderId;
	private Boolean replyPath;
    private Long uc;
    private Date dc;
    private Long um;
    private Date dm;
    private Long ud;
    private Date dd;

	public MNOSMSCProperty() {
	}



}
