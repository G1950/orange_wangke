package com.guangming.service;

import com.guangming.pojo.OauthClientDetails;
import com.guangming.utils.Result;

public interface IOauthClientDetailsService {
    //增
    Result save(OauthClientDetails oauthClientDetails);

    //删
    Result deleteById(String id);

    //改
    Result update(OauthClientDetails oauthClientDetails);

    //查
    Result query(OauthClientDetails oauthClientDetails);

    Result queryById(String id);
}
