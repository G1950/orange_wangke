package com.guangming.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class Authority implements Serializable {
    //权限id
    private String authority_id;
    //账号
    private String authority_account;
    //密码
    private String authority_pass;
    //权限角色
    private String authority_role;
    //上一次登录时间
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date last_login;
    //账号状态
    private Integer status;

    public String getAuthority_id() {
        return authority_id;
    }

    public void setAuthority_id(String authority_id) {
        this.authority_id = authority_id == null ? null : authority_id.trim();
    }

    public String getAuthority_account() {
        return authority_account;
    }

    public void setAuthority_account(String authority_account) {
        this.authority_account = authority_account == null ? null : authority_account.trim();
    }

    public String getAuthority_pass() {
        return authority_pass;
    }

    public void setAuthority_pass(String authority_pass) {
        this.authority_pass = authority_pass == null ? null : authority_pass.trim();
    }

    public String getAuthority_role() {
        return authority_role;
    }

    public void setAuthority_role(String authority_role) {
        this.authority_role = authority_role == null ? null : authority_role.trim();
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}