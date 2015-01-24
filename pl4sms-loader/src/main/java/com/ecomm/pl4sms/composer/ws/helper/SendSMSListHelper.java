package com.ecomm.pl4sms.composer.ws.helper;


import com.ecomm.pl4sms.common.kannel.KannelSend_HTTP_URL;
import com.ecomm.pl4sms.common.kannel.KannelSender;
import com.ecomm.pl4sms.ws.json.inputs.JSONSendSMSInput;
import com.ecomm.pl4sms.ws.json.inputs.JSONTestSMSInput;
import com.ecomm.pl4sms.ws.json.outputs.JSON_TestSMS_Output;
import com.ecomm.pl4sms.ws.json.outputs.SendSMSListReturnType;

public class SendSMSListHelper {


    public SendSMSListReturnType sendMessageList(JSONSendSMSInput input ){
        return new SendSMSListReturnType();
    }

    public JSON_TestSMS_Output sendTestSMS( JSONTestSMSInput input ) {

        KannelSend_HTTP_URL httpURL = new KannelSend_HTTP_URL();

        httpURL.setServerName("localhost");
        httpURL.setPortNumber("13013");
        httpURL.setUrlContext("cgi-bin/sendsms");
        httpURL.setFrom("Test");
        httpURL.setUsername("tester");
        httpURL.setPassword("foobar");

        httpURL.setTargetMSDN(input.getTargetNumber());
        httpURL.setMessageText(input.getTextMessage());
        httpURL.setSmsCentre(input.getSmsCentre());

        KannelSender.submitKannelMessage(httpURL);

        JSON_TestSMS_Output output = new JSON_TestSMS_Output();
        output.setStatus("Sent");
        return output;
    }
}
