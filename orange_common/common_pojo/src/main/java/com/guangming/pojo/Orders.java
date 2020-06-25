package com.guangming.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Orders implements Serializable {
    //订单编号
    private String _id;
    //用户编号
    private String user_id;
    //产品编号
    private String product_id;
    //产品数量
    private Integer _nums;
    //价格
    private BigDecimal _price;
    //折扣
    private BigDecimal discount;
    //实际价格
    private String real_price;
    //创建时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    //最后更新时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date last_time;
    //订单状态
    private Integer _status;

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id == null ? null : product_id.trim();
    }

    public Integer getNums() {
        return _nums;
    }

    public void setNums(Integer _nums) {
        this._nums = _nums;
    }

    public BigDecimal get_price() {
        return _price;
    }

    public void set_price(BigDecimal _price) {
        this._price = _price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getReal_price() {
        return real_price;
    }

    public void setReal_price(String real_price) {
        this.real_price = real_price == null ? null : real_price.trim();
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

    public Integer get_status() {
        return _status;
    }

    public void set_status(Integer _status) {
        this._status = _status;
    }
}