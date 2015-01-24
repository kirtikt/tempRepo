package com.ecomm.pl4sms.ws.json.inputs;

import java.io.Serializable;

public class JSONInputBaseClass implements Serializable{

	
	protected String username;
	protected String password;
	protected String clientReference;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientReference() {
        return clientReference;
    }

    public void setClientReference(String clientReference) {
        this.clientReference = clientReference;
    }
}
