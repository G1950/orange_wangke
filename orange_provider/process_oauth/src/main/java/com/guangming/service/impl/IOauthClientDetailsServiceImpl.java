package com.guangming.service.impl;

import com.guangming.mapper.OauthClientDetailsMapper;
import com.guangming.pojo.OauthClientDetails;
import com.guangming.service.IOauthClientDetailsService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName IOauthClientDetailsServiceImpl
 * @Description TODO
 * @Author Gm
 * @Date 2020/7/3/003 17:26
 * @Version 1.0
 **/
@Service
public class IOauthClientDetailsServiceImpl implements IOauthClientDetailsService {
    @Resource
    private OauthClientDetailsMapper oauthClientDetailsMapper;

    @Override
    public Result save(OauthClientDetails oauthClientDetails) {
        try {
            OauthClientDetails clientDetails = oauthClientDetailsMapper.queryById(oauthClientDetails.getClient_id());
            if (clientDetails != null)
                return Result.build(ResultEnum.RESOURCE_EXIST);
            oauthClientDetailsMapper.save(oauthClientDetails);
            return Result.build(ResultEnum.SAVE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.SAVE_ERROR);
        }
    }

    @Override
    public Result deleteById(String id) {
        try {
            oauthClientDetailsMapper.deleteById(id);
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }


    @Override
    public Result update(OauthClientDetails oauthClientDetails) {
        try {
            OauthClientDetails clientDetails = oauthClientDetailsMapper.queryById(oauthClientDetails.getClient_id());
            if (clientDetails != null)
                return Result.build(ResultEnum.RESOURCE_EXIST);
            oauthClientDetailsMapper.update(oauthClientDetails);
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            return Result.build(ResultEnum.UPDATE_ERROR);
        }

    }

    @Override
    public Result query(OauthClientDetails oauthClientDetails) {
        try {
            List<OauthClientDetails> clientDetails = oauthClientDetailsMapper.query(oauthClientDetails);
            return Result.build(ResultEnum.QUERY_SUCCESS, clientDetails);
        } catch (Exception e) {
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result queryById(String id) {
        try {
            OauthClientDetails clientDetails = oauthClientDetailsMapper.queryById(id);
            return Result.build(ResultEnum.QUERY_SUCCESS, clientDetails);
        } catch (Exception e) {
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }
}
