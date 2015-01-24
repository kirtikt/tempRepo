package com.ecomm.pl4sms.composer.ws.helper;


import com.ecomm.pl4sms.common.PL4Status;
import com.ecomm.pl4sms.common.SMSQueueMessageDTO;
import com.ecomm.pl4sms.composer.dto.SMSMessageDTO;
import com.ecomm.pl4sms.composer.jms.QueueLoader;
import com.ecomm.pl4sms.ws.json.inputs.JSONQueueSMSInput;
import com.ecomm.pl4sms.ws.json.outputs.JSON_Queue_SMS_Output;
import org.apache.log4j.Logger;

public class QueueLoaderHelper {


    private static Logger LOG = Logger.getLogger(QueueLoaderHelper.class);

    public JSON_Queue_SMS_Output loadSMSOntoQueue( JSONQueueSMSInput smsInput ) {


        SMSQueueMessageDTO smsMessage = new SMSQueueMessageDTO(smsInput.getBatchId(), smsInput.getTextMessage(), smsInput.getTargetNumber(), smsInput.getSmsCentre());

        JSON_Queue_SMS_Output output = new JSON_Queue_SMS_Output();
        try {

            new QueueLoader().sendMessage(smsMessage);
            output.setStatus(PL4Status.SUCCESS.name());
        } catch (RuntimeException re ) {
            LOG.error(re.getMessage(), re);
            output.setStatus(PL4Status.ERROR.name());
        }

        return output;

    }

}
