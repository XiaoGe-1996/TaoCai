package com.my.aicai.entity;

import com.avos.avoscloud.AVUser;


public class ShopCartEntity {
    private String id;
    private String goodId;
    private String url;
    private String name;
    private String price;
    private String des;
    private String type;
    private String number;
    private AVUser user;

    public ShopCartEntity(String id, String goodId, String url, String name, String price, String des, String type, String number, AVUser user) {
        this.id = id;
        this.goodId = goodId;
        this.url = url;
        this.name = name;
        this.price = price;
        this.des = des;
        this.type = type;
        this.number = number;
        this.user = user;
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
}
