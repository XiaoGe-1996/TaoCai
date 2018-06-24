package com.my.aicai.entity;

import com.avos.avoscloud.AVFile;

/**
 * Created by user999 on 2018/5/3.
 */

public class GoodsEntity {
    private String id;
    private AVFile pic;
    private String name;
    private String price;
    private String des;
    private String type;


    public GoodsEntity(String id, AVFile pic, String name, String price, String des,String type) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.price = price;
        this.des = des;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AVFile getPic() {
        return pic;
    }

    public void setPic(AVFile pic) {
        this.pic = pic;
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
}
