package com.guangming.service;

import com.guangming.pojo.Authority;
import com.guangming.pojo.User;

public interface IOauthService {

    //权限信息，根据account,password
    Authority getAuthorityInfo(String account, String password);

    //用户信息,根据id
    User getUserInfo(String id);
}
