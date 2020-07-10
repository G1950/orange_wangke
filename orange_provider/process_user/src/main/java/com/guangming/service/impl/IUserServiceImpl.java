package com.guangming.service.impl;

import com.guangming.mapper.AuthorityMapper;
import com.guangming.mapper.UserMapper;
import com.guangming.mapper.WalletMapper;
import com.guangming.pojo.Authority;
import com.guangming.pojo.User;
import com.guangming.pojo.Wallet;
import com.guangming.service.IUserService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IUserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private WalletMapper walletMapper;
    @Resource
    private AuthorityMapper authorityMapper;

    @Transactional
    @Override
    public Result saveUserInfo(User user) {

        try {
            Authority authority = authorityMapper.findAuthorityInfoById(user.getId());
            if (authority == null)
                return Result.build(ResultEnum.NOT_EXIST_USER);
            userMapper.saveUserInfo(user);
            Wallet wallet = new Wallet();
            wallet.setUser_id(user.getId());
            wallet.setPay_type(-1);//免费用户
            wallet.setTm_nums(20);
            walletMapper.save(wallet);
            log.info("用户添加成功");
            return Result.build(ResultEnum.SAVE_SUCCESS);
        } catch (Exception e) {
            log.error("用户添加失败：" + e.getMessage());
            return Result.build(ResultEnum.SAVE_ERROR);
        }
    }

    @Override
    public Result updateUserInfo(User user) {
        try {
            if (user.getId() == null || userMapper.findUserInfoById(user.getId()) == null)
                return Result.build(ResultEnum.NOT_EXIST_USER);
            userMapper.updateUserInfo(user);
            log.info("用户修改成功");
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("用户修改失败：" + e.getMessage());
            return Result.build(ResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result queryUserInfoById(String id) {
        try {
            User userInfoById = userMapper.findUserInfoById(id);
            log.info("用户查询成功");
            return Result.build(ResultEnum.QUERY_SUCCESS, userInfoById);
        } catch (Exception e) {
            log.error("用户查询失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);

        }

    }

    @Override
    public Result queryUserInfo(User user) {
        try {
            List<User> userInfo = userMapper.findUserInfo(user);
            log.info("用户查询成功");
            return Result.build(ResultEnum.QUERY_SUCCESS, userInfo);
        } catch (Exception e) {
            log.error("用户查询失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Transactional
    @Override
    public Result deleteUserInfoById(List<String> id) {
        try {
            userMapper.deleteUserInfo(id);
            log.info("用户删除成功");
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("用户删除失败：" + e.getMessage());
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }
}
