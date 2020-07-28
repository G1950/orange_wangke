package com.guangming.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Wallet implements Serializable {
    //用户id
    private String user_id;
    //支付类型
    private Integer pay_type;
    //剩余查题次数
    private Integer tm_nums;
    //生效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;
    //失效时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date invalid_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public Integer getTm_nums() {
        return tm_nums;
    }

    public void setTm_nums(Integer tm_nums) {
        this.tm_nums = tm_nums;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getInvalid_time() {
        return invalid_time;
    }

    public void setInvalid_time(Date invalid_time) {
        this.invalid_time = invalid_time;
    }
}