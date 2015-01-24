package com.ecomm.pl4sms.composer.jms;


import com.ecomm.pl4sms.composer.batchload.BatchLoad;
import com.ecomm.pl4sms.ws.json.inputs.*;
import com.ecomm.pl4sms.ws.json.outputs.*;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



public class TestQueueLoader {



    //private static String BASE_URL = "http://localhost:8080/pl4sms-loader-1.0-SNAPSHOT/ws/smsbulksender";

    private static String BASE_URL = "http://41.185.26.92:8080/pl4sms-loader-1.0-SNAPSHOT/ws/smsbulksender";


    private static String CREATE_BATCH_URL = "/j_create_batch";
    private static String CREATE_CLIENT_URL= "/j_create_client";

    private static String SEND_SMS_URL = "/j_send_sms_test";

    private static String SEND_QUEUE_SMS_URL = "/j_send_sms";

    public void testCreateClient() {

        JSON_Output_CreateClient returnType = new JSON_Output_CreateClient();
        HttpClient httpClient = new DefaultHttpClient();

        try {

            JSONCreateClientInput input = new JSONCreateClientInput();
            input.setClientName("Client123");

            HttpGet request = new HttpGet(BASE_URL + CREATE_CLIENT_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {

                String responseString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

                returnType = new Gson().fromJson(responseString, JSON_Output_CreateClient.class);

                System.out.println( returnType.getStatus() );
                System.out.println( returnType.getStatusMessage() );

            }else {
                System.out.println( "ERROR - CODE [" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    public void testSend() {

//                CreateBatchReturnType returnType = new CreateBatchReturnType();
//                HttpClient httpClient = new DefaultHttpClient();
//
//                try {
//
//                    JSONCreateBatchInput input = new JSONCreateBatchInput();
//                    input.setBatchDescription("Batch Description Tester");
//                    input.setClientIdentifier("Client123");
//
//                    HttpGet request = new HttpGet(BASE_URL + CREATE_BATCH_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
//                    request.addHeader("content-type", "application/x-www-form-urlencoded");
//                    HttpResponse response = httpClient.execute(request);
//                    if (response.getStatusLine().getStatusCode() == 200) {
//
//                        String responseString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
//
//                        returnType = new Gson().fromJson(responseString, CreateBatchReturnType.class);
//
//                        System.out.println( returnType.getStatus() );
//                        System.out.println( returnType.getStatusMessage() );
//                        System.out.println( returnType.getBatchIdentifier() );
//
//                    }else {
//                        System.out.println( "ERROR - CODE [" + response.getStatusLine().getStatusCode() + "]");
//                    }
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                } finally {
//                    httpClient.getConnectionManager().shutdown();
//                }

            }

    @Test
    public void testKannel() {




        JSON_TestSMS_Output returnType = new JSON_TestSMS_Output();
        HttpClient httpClient = new DefaultHttpClient();

        try {

            JSONTestSMSInput input = new JSONTestSMSInput();

            input.setSmsCentre("connect");
            input.setTargetNumber("27828501754");
            input.setTextMessage("Remote Tester 123");

            HttpGet request = new HttpGet(BASE_URL + SEND_SMS_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {

                String responseString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

                returnType = new Gson().fromJson(responseString, JSON_TestSMS_Output.class);

                System.out.println( returnType.getStatus() );
                System.out.println( returnType.getStatusMessage() );


            }else {
                System.out.println( "ERROR - CODE [" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    @Test
    public void testQueueSend() {

        JSON_Queue_SMS_Output returnType = new JSON_Queue_SMS_Output();
        HttpClient httpClient = new DefaultHttpClient();

        try {

            JSONQueueSMSInput input = new JSONQueueSMSInput();
            input.setBatchId("JADITWERK!!!");
            input.setSmsCentre("connect");
            input.setTargetNumber("27828501754");
            input.setTextMessage("Remote Tester 123");

        HttpGet request = new HttpGet(BASE_URL + SEND_QUEUE_SMS_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {

                String responseString = IOUtils.toString(response.getEntity().getContent(), "UTF-8");

                returnType = new Gson().fromJson(responseString, JSON_Queue_SMS_Output.class);

                System.out.println( returnType.getStatus() );
                System.out.println( returnType.getStatusMessage() );


            }else {
                System.out.println( "ERROR - CODE [" + response.getStatusLine().getStatusCode() + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

}
