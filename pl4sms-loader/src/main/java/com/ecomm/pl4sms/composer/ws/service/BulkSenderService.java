package com.ecomm.pl4sms.composer.ws.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


import com.ecomm.pl4sms.common.PL4Status;
import com.ecomm.pl4sms.composer.batchload.BatchLoad;
import com.ecomm.pl4sms.composer.ws.helper.QueueLoaderHelper;
import com.ecomm.pl4sms.composer.ws.helper.SendSMSListHelper;
import com.ecomm.pl4sms.persistence.entities.MessagingClient;
import com.ecomm.pl4sms.persistence.entities.SMSBatch;
import com.ecomm.pl4sms.ws.json.inputs.*;
import com.ecomm.pl4sms.ws.json.outputs.*;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

@Path("/smsbulksender")
public class BulkSenderService {

    private static Logger LOG = Logger.getLogger(BulkSenderService.class);

	public BulkSenderService() {

	}


    @GET
    @Path("/j_send_sms_test/{smsMessage}")
    public Response sendSMSTest(@PathParam("smsMessage") String smsMessage) {

        System.out.println("Sending Test SMS ...\n " + smsMessage);

        JSONTestSMSInput input = new Gson().fromJson(smsMessage, JSONTestSMSInput.class);

        JSON_TestSMS_Output returnJSON = new SendSMSListHelper().sendTestSMS(input);

        return Response.status(200).entity(new Gson().toJson(returnJSON)).build();

    }


    @GET
    @Path("/j_send_sms/{smsMessage}")
    public Response sendSMS(@PathParam("smsMessage") String smsMessage) {

        System.out.println("Sending SMS via Queue ...\n " + smsMessage);

        JSONQueueSMSInput input = new Gson().fromJson(smsMessage, JSONQueueSMSInput.class);

        JSON_Queue_SMS_Output returnJSON = new QueueLoaderHelper().loadSMSOntoQueue(input);

        return Response.status(200).entity(new Gson().toJson(returnJSON)).build();

    }




    @GET
	@Path("/j_send_sms_list/{smslist}")
	public Response bulkMethod_JSON_SMSList(@PathParam("smslist") String smslist) {
		System.out.println("SMSList input \n " + smslist);

		JSONSendSMSInput input = new Gson().fromJson(smslist, JSONSendSMSInput.class);

		SendSMSListReturnType returnJSON = new SendSMSListHelper().sendMessageList(input);

		return Response.status(200).entity(new Gson().toJson(returnJSON)).build();

	}

    @GET
    @Path("/j_create_batch/{batchInputs}")
    public Response createBatchForClient(@PathParam("batchInputs") String batchInputs) {


        JSONCreateBatchInput input = new Gson().fromJson(batchInputs, JSONCreateBatchInput.class);

        LOG.debug("Batch Description [" + input.getBatchDescription() + "]");

        CreateBatchReturnType output = new CreateBatchReturnType();
        try {
            SMSBatch smsBatch = new BatchLoad().createBatchForClient(input.getBatchDescription(), input.getClientIdentifier() );
            output.setStatus(PL4Status.SUCCESS.getStatusMessage());
            output.setStatusMessage("Batch with identifier [" + smsBatch.getBatchIdentifier() + "] created.");

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            System.out.println( e.getMessage());
            output.setStatus(PL4Status.ERROR.getStatusMessage());
            output.setStatusMessage("Client with identifier [" + input.getClientIdentifier() + "] not found.");
        }

        return Response.status(200).entity(new Gson().toJson(output)).build();
    }



    @GET
    @Path("/j_create_client/{createClientInput}")
    public Response createClient(@PathParam("createClientInput") String createClientInput) {


        JSONCreateClientInput input = new Gson().fromJson(createClientInput, JSONCreateClientInput.class);

        LOG.debug("Batch Description [" + input.getClientName() + "]");

        JSON_Output_CreateClient output = new JSON_Output_CreateClient();
        try {
            MessagingClient messagingClient = new BatchLoad().createClient(input.getClientName() );
            output.setStatus(PL4Status.SUCCESS.getStatusMessage());
            output.setStatusMessage("Client with identifier [" + messagingClient.getClientIdentifier() + "] created.");

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            output.setStatus(PL4Status.ERROR.getStatusMessage());
            output.setStatusMessage("Client with name [" + input.getClientName() + "] not created.");
        }

        return Response.status(200).entity(new Gson().toJson(output)).build();
    }


}



	


