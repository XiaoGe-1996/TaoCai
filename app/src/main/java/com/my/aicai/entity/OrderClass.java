package com.my.aicai.entity;

import com.avos.avoscloud.AVUser;

/**
 * Created by Gc on 2018/5/12.
 * PackageName: com.my.aicai.entity
 * Desc:
 */

public class OrderClass {

    private AVUser user;
    private String OrderPhone;
    private String OrderAddress;
    private String OrderPrice;
    private String shouhuoName;
    private String id;
    private String expressNum;
    private String status;
    private String createTime;
    private String goodsNum;
    private String com;

    public OrderClass(String id,AVUser user,String OrderPhone ,String OrderAddress,String OrderPrice,String status,String shouhuoName,String expressNum,String createTime,String goodsNum,String com) {
        this.user = user;
        this.OrderPhone=OrderPhone;
        this.OrderAddress=OrderAddress;
        this.OrderPrice = OrderPrice;
        this.shouhuoName = shouhuoName;
        this.expressNum = expressNum;
        this.id=id;
        this.createTime = createTime;
        this.goodsNum = goodsNum;
        this.status=status;
        this.com = com;
    }

    public AVUser getUser() {
        return user;
    }

    public void setUser(AVUser user) {
        this.user = user;
    }

    public String getShouhuoName() {
        return shouhuoName;
    }

    public void setShouhuoName(String shouhuoName) {
        this.shouhuoName = shouhuoName;
    }

    public String getOrderPhone() {
        return OrderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        OrderPhone = orderPhone;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }
}
