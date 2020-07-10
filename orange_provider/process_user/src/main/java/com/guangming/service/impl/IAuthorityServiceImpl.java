package com.guangming.service.impl;

import com.guangming.mapper.AuthorityMapper;
import com.guangming.pojo.Authority;
import com.guangming.service.IAuthorityService;
import com.guangming.utils.EncryptionUtils;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import com.guangming.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName IAuthorityServiceImpl
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/2/002 21:01
 * @Version 1.0
 **/
@Slf4j
@Service
public class IAuthorityServiceImpl implements IAuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;

    @Override
    public Result save(Authority authority) {
        try {
            Authority auth = authorityMapper.findAuthorityInfoByAccount(authority.getAuthority_account());
            if (auth!=null)
                return Result.build(ResultEnum.EXIST_USER);
            authority.setAuthority_id("user_"+ SnowFlake.nextId());
            authorityMapper.saveAuthority(authority);
            return Result.build(ResultEnum.SAVE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.SAVE_ERROR);
        }
    }

    @Override
    public Result update(Authority authority) {
        try {
            authorityMapper.updateAuthority(authority);
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result query(Authority authority) {
        try {
            List<Authority> authorities = authorityMapper.queryAuthority(authority);
            return Result.build(ResultEnum.QUERY_SUCCESS, authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }


    @Override
    public Result queryByAccountAndPassword(String account, String password) {
        try {
            password = EncryptionUtils.md5Hex(password);
            Authority authority = authorityMapper.findAuthorityInfoByAccountPwd(account, password);
            if (authority != null)
                return Result.build(ResultEnum.QUERY_SUCCESS, authority);
            return Result.build(ResultEnum.NOT_EXIST_USER);
        } catch (Exception e) {
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result deleteById(List<String> id) {

        try {
            authorityMapper.deleteAuthority(id);
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }
}
