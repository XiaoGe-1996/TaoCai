package com.my.aicai.entity;

import com.avos.avoscloud.AVUser;


public class AddressEntity {
    private AVUser user;
    private String shouhuoName;
    private String shouhuoPhone;
    private String address;
    private String id;

    public AddressEntity(AVUser user,String shouhuoName ,String shouhuoPhone,String address, String id) {
        this.user = user;
        this.shouhuoName=shouhuoName;
        this.shouhuoPhone=shouhuoPhone;
        this.address = address;
        this.id=id;
    }

    public String getShouhuoName() {
        return shouhuoName;
    }

    public void setShouhuoName(String shouhuoName) {
        this.shouhuoName = shouhuoName;
    }

    public String getShouhuoPhone() {
        return shouhuoPhone;
    }

    public void setShouhuoPhone(String shouhuoPhone) {
        this.shouhuoPhone = shouhuoPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
