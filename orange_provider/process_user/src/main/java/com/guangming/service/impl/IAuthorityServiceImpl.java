package com.guangming.service.impl;

import com.guangming.mapper.AuthorityMapper;
import com.guangming.mapper.UserMapper;
import com.guangming.mapper.WalletMapper;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.pojo.Wallet;
import com.guangming.service.IAuthorityService;
import com.guangming.utils.EncryptionUtils;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import com.guangming.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    private UserMapper userMapper;
    @Resource
    private WalletMapper walletMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(Authority authority) throws Exception {
        Authority auth = authorityMapper.findAuthorityInfoByAccount(authority.getAuthority_account());
        if (auth != null)
            return Result.build(ResultEnum.EXIST_USER);
        authority.setAuthority_id("user_" + SnowFlake.nextId());
        authority.setAuthority_pass(EncryptionUtils.md5Hex(authority.getAuthority_pass()));
        authorityMapper.saveAuthority(authority);
        User user = new User();
        user.setId(authority.getAuthority_id());
        userMapper.saveUser(user);
        Wallet wallet = new Wallet();
        wallet.setUser_id(user.getId());
        wallet.setPay_type(-1);//免费用户
        wallet.setTm_nums(20);
        walletMapper.save(wallet);
        return Result.build(ResultEnum.USER_REGISTER_SUCCESS);
    }

    @Override
    public Result update(Authority authority) {
        try {
            Authority authorityInfoByAccount = authorityMapper.findAuthorityInfoByAccount(authority.getAuthority_account());
            if (authorityInfoByAccount == null)
                Result.build(ResultEnum.NOT_EXIST_USER);
            authority.setAuthority_pass(EncryptionUtils.md5Hex(authority.getAuthority_pass()));
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
            if (authorities == null || authorities.size() == 0)
                return Result.build(ResultEnum.NOT_EXIST_USER);
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
            return Result.build(ResultEnum.USER_NOT_FOUND);
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
