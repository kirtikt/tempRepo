package com.ecomm.pl4sms.common.kannel;


public class DeliveryInfo {

    private String id;
    private String hasBeenSubmitted;
    private String hasBeenDelivered;
    private String submissionDate;
    private String completionDate;
    private String status;
    private String errorCode;
    private String text;

    public DeliveryInfo() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHasBeenSubmitted() {
        return hasBeenSubmitted;
    }

    public void setHasBeenSubmitted(String hasBeenSubmitted) {
        this.hasBeenSubmitted = hasBeenSubmitted;
    }

    public String getHasBeenDelivered() {
        return hasBeenDelivered;
    }

    public void setHasBeenDelivered(String hasBeenDelivered) {
        this.hasBeenDelivered = hasBeenDelivered;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
