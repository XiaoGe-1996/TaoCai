package com.my.aicai.entity;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.PaasClient;


public class BuyGoodsEntity {
    private String id;
    private String goodId;
    private String url;
    private String name;
    private String price;
    private String des;
    private String type;
    private String number;
    private AVUser user;
    private String address;
    private String phone;
    private String orderID;

    public BuyGoodsEntity(String id, String goodId, String url, String name, String price, String des, String type, String number, AVUser user,String address,String phone,String orderID) {
        this.id = id;
        this.goodId = goodId;
        this.url = url;
        this.name = name;
        this.price = price;
        this.des = des;
        this.type = type;
        this.number = number;
        this.user = user;
        this.address=address;
        this.phone=phone;
        this.orderID=orderID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
