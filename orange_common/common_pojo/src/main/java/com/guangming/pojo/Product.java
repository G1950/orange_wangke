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
    //产品付费类型 1为包年，0为包月，-1为按次数
    private Integer type;
    //查题次数，按次数充值有效
    private Integer tm_nums;
    //查题按月，月数
    private Integer tm_months;
    //产品状态,1为在售，0为下架
    private Integer status;
    //库存
    private Integer nums;

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

    public Integer getType() { return type; }

    public void setType(Integer type) { this.type = type; }

    public Integer getTm_nums() {
        return tm_nums;
    }

    public void setTm_nums(Integer tm_nums) {
        this.tm_nums = tm_nums;
    }

    public Integer getTm_months() {
        return tm_months;
    }

    public void setTm_months(Integer tm_months) {
        this.tm_months = tm_months;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNums() { return nums; }

    public void setNums(Integer nums) { this.nums = nums; }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", desc='" + desc + '\'' +
                ", img_url='" + img_url + '\'' +
                ", status=" + status +
                ", nums=" + nums +
                '}';
    }
}