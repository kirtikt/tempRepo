package com.ecomm.pl4sms.ws.json;

import java.net.URLEncoder;

import com.ecomm.pl4sms.ws.json.inputs.JSONSendSMSInput;
import com.ecomm.pl4sms.ws.json.outputs.SendSMSListReturnType;


import com.google.gson.Gson;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;


public class BulkSenderClient {

    private static String BASE_URL = "http://localhost:8080/payless4sms/ws/smsbulksender";
    private static String SEND_SMS_URL = "/j_send_sms_list";


    public SendSMSListReturnType checkCredits(JSONSendSMSInput input) {


        HttpClient httpClient = new HttpClient();
        SendSMSListReturnType returnType = null;
        try {

            //RESTBulkSender/ws/smsbulksender/j_credit_check/ {"username":"username","password":"password","clientRef":"clientref"}
            //String url="http://localhost:8080/payless4sms/ws/smsbulksender/j_credit_check/{\"username\":\"sysuser\",\"password\":\"pass\",\"clientRef\":\"sysuser\"}";
            GetMethod request = new GetMethod(BASE_URL + SEND_SMS_URL + "/" + URLEncoder.encode(new Gson().toJson(input)));
            request.setRequestHeader("content-type", "application/x-www-form-urlencoded");
            int response = httpClient.executeMethod(request);
            if (response == 200) {

                returnType = new Gson().fromJson(String.valueOf(response), SendSMSListReturnType.class);
            } else {
                System.out.println("ERROR - CODE [" + response + "]");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return returnType;
    }


    public static void main(String[] args) {


        BulkSenderClient client = new BulkSenderClient();


        JSONSendSMSInput input3 = new JSONSendSMSInput();
        input3.setClientReference("sysuser");
        input3.setPassword("pass");
        input3.setUsername("sysuser");

        System.out.println(client.checkCredits(input3).getStatus());


    }

}
