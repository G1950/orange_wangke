package com.guangming.pojo;

import java.io.Serializable;

public class User implements Serializable {
    //用户id
    private String id;
    //用户昵称
    private String nickname;
    //用户邮箱
    private String email;
    //用户电话
    private String phone;
    //用户头像
    private String avatar_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url == null ? null : avatar_url.trim();
    }
}