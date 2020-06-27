package com.guangming.service.impl;

import com.guangming.mapper.ProductMapper;
import com.guangming.pojo.Product;
import com.guangming.service.IProductService;
import com.guangming.utils.Result;
import com.guangming.utils.ResultEnum;
import com.guangming.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName IUploadServiceImpl
 * @Description 具体产品处理类
 * @Author Gm
 * @Date 2020/6/26/026 16:40
 * @Version 1.0
 **/
@Slf4j
@Service
public class IProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;

    @Override
    public Result query() {
        try {
            List<Product> query = productMapper.query();
            log.info("查询成功");
            return Result.build(ResultEnum.QUERY_SUCCESS, query);
        } catch (Exception e) {
            log.error("查询失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result query(String id) {
        try {
            Product query = productMapper.queryById(id);
            log.info("查询成功");
            return Result.build(ResultEnum.QUERY_SUCCESS, query);
        } catch (Exception e) {
            log.error("查询失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Override
    public Result query(Product product) {
        try {
            List<Product> query = productMapper.queryByProduct(product);
            log.info("查询成功");
            return Result.build(ResultEnum.QUERY_SUCCESS, query);
        } catch (Exception e) {
            log.error("查询失败：" + e.getMessage());
            return Result.build(ResultEnum.QUERY_ERROR);
        }
    }

    @Transactional
    @Override
    public Result save(List<Product> products) {
        try {
            List<Product> list = new ArrayList<>();
            for (Product product : products) {
                product.setId(String.valueOf(SnowFlake.nextId()));
                list.add(product);
            }
            productMapper.save(list);
            log.info("添加成功");
            return Result.build(ResultEnum.SAVE_SUCCESS);
        } catch (Exception e) {
            log.error("添加失败：" + e.getMessage());
            return Result.build(ResultEnum.SAVE_ERROR);
        }
    }

    @Transactional
    @Override
    public Result update(List<Product> products) {
        try {
            for (Product product : products) {
                productMapper.update(product);
            }
            log.info("更新成功");
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("更新失败：" + e.getMessage());
            return Result.build(ResultEnum.UPDATE_ERROR);
        }
    }

    @Override
    public Result updateById(String id, Integer nums) {
        try {
            productMapper.updateById(id, nums);
            log.info("更新成功");
            return Result.build(ResultEnum.UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("更新失败：" + e.getMessage());
            return Result.build(ResultEnum.UPDATE_ERROR);
        }
    }

    @Transactional
    @Override
    public Result delete(List<String> id) {
        try {
            productMapper.delete(id);
            log.info("删除成功");
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("删除失败：" + e.getMessage());
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }

    @Transactional
    @Override
    public Result delete(String id) {
        try {
            productMapper.deleteById(id);
            log.info("删除成功");
            return Result.build(ResultEnum.DELETE_SUCCESS);
        } catch (Exception e) {
            log.error("删除失败：" + e.getMessage());
            return Result.build(ResultEnum.DELETE_ERROR);
        }
    }
}
