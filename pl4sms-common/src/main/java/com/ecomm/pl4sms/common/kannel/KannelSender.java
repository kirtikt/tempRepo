package com.ecomm.pl4sms.common.kannel;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

public class KannelSender {

    private static Logger LOG = Logger.getLogger(KannelSender.class);



    public static void submitKannelMessage(KannelSend_HTTP_URL httpUrlDTO ) {

        GetMethod getMethod = new GetMethod(httpUrlDTO.toString());

        LOG.info( "!!!!!");
        LOG.info( httpUrlDTO.toString() );

        try {
            new HttpClient().executeMethod(getMethod);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }

    }

//    public synchronized void sendMessagetoKannel(SMSMessageDetails smsMessageDetails,
//                                                 String dlrmask, long ctime) {
//
//        String response = null;
//        int splits = 0;
//        try {
//
//            String messageText = smsMessageDetails.getMessage();
//
//            String feedbackURL = Helper.getProps().getProperty("feedbackurl");
//
//            String dlr_to_encode = feedbackURL.trim() + "?status=%d&smsc=%i&timeDate=%t&destination=%p&delivInfo=%a&source=%P&msgid_sending=%F&msgid_delivery=%k&charge=%B&accountInfo=%o&smsid="
//                    + smsMessageDetails.getMessageID().trim() + "&message=" + URLEncoder.encode(messageText, "UTF-8");
//
//
//            String ipAddress = Helper.getProps().getProperty("ip");
//            String kannelPort = Helper.getProps().getProperty("kannelport");
//
//            response = new CreateUrl().sendSMS(ipAddress.trim(), kannelPort.trim(), "tester",
//                    "foobar", smsMessageDetails.getSender().trim(),
//                    smsMessageDetails.getReceiver().trim(), smsMessageDetails.getMessage(),
//                    smsMessageDetails.getSmsCentreIdentifier().trim(), dlrmask, dlr_to_encode);
//
//            LOG.info("Response:" + response);
//            if ("Sent.".equals(response)) {
//                splits = 1;
//                LOG.info("Message Sent Successfully.");
//
//            }
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//        }
//    }

//
//
//    private void saveSuccessfulToDB() {
//
//        try {
//            ClientmanagersManager clientmanager = new ClientmanagersManager();
//            Clientmanager client = clientmanager.findById(textMessage
//                    .getClientmanagerId());
//            clientmanager = null;
//            HoldingaccountManager holdmanager = new HoldingaccountManager();
//            Holdingaccount holdccount = holdmanager
//                    .findById(textMessage.getHoldingaccountId());
//            holdmanager = null;
//            Sms sms = new Sms();
//            sms.setClientmanager(client);
//            sms.setHoldingaccount(holdccount);
//            sms.setSmsid(textMessage.getMessageID());
//            sms.setMessage(textMessage.getMessage());
//            sms.setSource(textMessage.getSender());
//            sms.setDestination(textMessage.getReciever());
//            sms.setStatus("11"); //Message processed.
//            sms.setDatetime(new Date());
//            int smscount = textMessage.getSmscount();
//            sms.setSmscount(smscount);
//            // sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
//            double creditAfter = holdccount.getCreditsleft();
//            sms.setCreditafter(creditAfter);
//            sms.setSendinginterface(client.getSendinginterface());
//            sms.setDeliveryinfo(textMessage.getDeliveryinfo());
//            // sms.setUm(Integer
//            // .valueOf(client.getSysuser().getPkuserid()));
//            sms.setDm(new Date());
//            SmsManager smsmanager = new SmsManager();
//            smsmanager.add(sms);
//            log.info("//************ processed the sms for {} ***************// ", textMessage.getReciever());
//
//            sms = null;
//            smsmanager = null;
//            holdmanager = null;
//            clientmanager = null;
//            holdccount = null;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // * Message sent to kannel subtracted from user credits
//
//        log.info("userid :" + textMessage.getClientmanagerId());
//
//    }
//
//    private void saveUnsuccessfulToDB() {
//
//
//            } else if (response != null) {
//				/*
//				 * If sms not processed Add sms object into database with status
//				 * 99
//				 */
//                try {
//                    ClientmanagersManager clientmanager = new ClientmanagersManager();
//                    Clientmanager client = clientmanager.findById(textMessage
//                            .getClientmanagerId());
//                    clientmanager = null;
//                    HoldingaccountManager holdmanager = new HoldingaccountManager();
//                    Holdingaccount holdccount = holdmanager.findById(textMessage.getHoldingaccountId());
//                    holdmanager = null;
//                    Sms sms = new Sms();
//                    sms.setClientmanager(client);
//                    sms.setHoldingaccount(holdccount);
//                    sms.setSmsid(textMessage.getMessageID());
//                    sms.setMessage(textMessage.getMessage());
//                    sms.setSource(textMessage.getSender());
//                    sms.setDestination(textMessage.getReciever());
//                    sms.setStatus("99"); // Message not send we not get succeed from kannel
//                    sms.setDatetime(new Date());
//                    int smscount = textMessage.getSmscount();
//                    sms.setSmscount(smscount);
//                    // sms.setCreditbefore(client.getCreditaccount().getAvailablecredit());
//                    double creditAfter = holdccount.getCreditsleft();
//                    sms.setCreditafter(creditAfter);
//                    sms.setSendinginterface(client.getSendinginterface());
//                    sms.setDeliveryinfo(textMessage.getDeliveryinfo());
//
//                    sms.setDm(new Date());
//                    SmsManager smsmanager = new SmsManager();
//                    smsmanager.add(sms);
//                    log.info("//************ Failed the sms for {} ***************// ",textMessage.getReciever());
//
//                    sms = null;
//                    smsmanager = null;
//                    clientmanager=null;
//                    holdmanager=null;
//                    holdccount=null;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (response.contains("Message splits:")) {
//                    splits = Integer.parseInt(Character.toString((response
//                            .charAt(response.length() - 1))));
//                    log.info("Message Sent, split into " + splits + " parts :"
//                            + response);
//                }
//            } else {
//                log.info("Message sending failed :" + response);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
