package com.my.aicai.entity;

import com.avos.avoscloud.AVUser;

/**
 * Created by Administrator on 2018/5/5 0005.
 */

public class PingJiaEntity {
    private String id;
    private AVUser user;
    private String content;
    private String goodsName;
    private String userName;
    private String url;
    private String price;

    public PingJiaEntity(String id, AVUser user, String content, String goodsName, String url, String price, String userName) {
        this.id = id;
        this.user = user;
        this.content = content;
        this.goodsName = goodsName;
        this.url = url;
        this.userName = userName;
        this.price = price;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
