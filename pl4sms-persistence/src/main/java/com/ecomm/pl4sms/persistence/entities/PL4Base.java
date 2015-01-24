package com.ecomm.pl4sms.persistence.entities;

import javax.persistence.*;

@MappedSuperclass
public class PL4Base {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="\"ID\"")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
