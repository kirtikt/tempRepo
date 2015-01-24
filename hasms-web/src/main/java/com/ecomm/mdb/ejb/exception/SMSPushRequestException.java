

package com.ecomm.mdb.ejb.exception;
public class SMSPushRequestException extends Exception
{
  private static final long serialVersionUID = -9105864303283141934L;

  public SMSPushRequestException()
  {
  }
  

  public SMSPushRequestException(String theMessage)
  {
    super(theMessage);
  }

  public SMSPushRequestException(String theMessage, Exception e) {
    super(theMessage, e);
  }
}
