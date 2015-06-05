package com.example.mycontactlist;

import android.text.format.Time;

public class ContactAddress {
    private int contactID;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;

    public ContactAddress() {
        contactID = -1;
    }

    public int getContactID() {
        return contactID;
    }
    public void setContactID(int i) {
        contactID = i;
    }
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String s) {
        streetAddress = s;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String s) {
        city = s;
    }
    public String getState() {
        return state;
    }
    public void setState(String s) {
        state = s;
    }
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String s) {
        zipCode = s;
    }

}