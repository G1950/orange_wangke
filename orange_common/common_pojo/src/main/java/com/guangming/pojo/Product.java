package com.guangming.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {
    //产品编号
    private String id;
    //产品名称
    private String name;
    //产品单价
    private BigDecimal price;
    //产品折扣
    private BigDecimal discount;
    //产品描述
    private String desc;
    //产品图片
    private String img_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url == null ? null : img_url.trim();
    }
}