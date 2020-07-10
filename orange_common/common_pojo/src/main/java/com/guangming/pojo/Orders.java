package com.guangming.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Orders implements Serializable {
    //订单编号
    private String id;
    //用户编号
    private String user_id;
    //产品编号
    private String product_id;
    //产品数量
    private Integer nums;
    //价格
    private BigDecimal price;
    //折扣
    private BigDecimal discount;
    //实际价格
    private BigDecimal real_price;
    //创建时间
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date create_time;
    //最后更新时间
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date last_time;
    //订单状态
    private Integer status;
    //支付交易号
   private String alipay_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id == null ? null : product_id.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getReal_price() {
        return real_price;
    }

    public void setReal_price(BigDecimal real_price) {
        this.real_price = real_price;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlipay_id() {
        return alipay_id;
    }
    public void setAlipay_id(String alipay_id) {
        this.alipay_id = alipay_id == null ? null : alipay_id.trim();
    }
}