package com.ds.surroundings.place;

import com.google.api.client.util.Key;

import java.io.Serializable;

public class PlaceDetails implements Serializable {

    @Key("formatted_address")
    private String address;
    @Key("international_phone_number")
    private String phoneNumber;
    @Key("open_now")
    private Boolean isOpen;
    @Key("website")
    private String website;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
