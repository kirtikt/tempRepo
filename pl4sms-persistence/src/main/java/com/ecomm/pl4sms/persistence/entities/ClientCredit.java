package com.ecomm.pl4sms.persistence.entities;

import javax.persistence.*;
import java.math.BigDecimal;


@NamedQueries ( {
        @NamedQuery(name="ClientCredit.findById", query="select o from ClientCredit o where o.id=:identifier")

})
@Entity
@Table(name="\"CLIENT_CREDIT\"")
public class ClientCredit extends PL4Base{


    @Column(name="\"AMT_SMS_CREDIT\"")
    private BigDecimal amountOfSMSCredits;





}
