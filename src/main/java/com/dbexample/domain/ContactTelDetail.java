package com.dbexample.domain;

import java.io.Serializable;

public class ContactTelDetail implements Serializable {
    private Long id;
    private Long contactId;
    private String telType;
    private String telNumber;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return this.contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getTelType() {
        return this.telType;
    }

    public void setTelType(String telType) {
        this.telType = telType;
    }

    public String getTelNumber() {
        return this.telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Override
    public String toString() {
        return "Contact Tel Detail - Id: " + id + ", Contact id: " + contactId
                + ", Type: " + telType + ", Number: " + telNumber;
    }
}
