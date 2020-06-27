package com.guangming.service;

import com.guangming.pojo.Product;
import com.guangming.utils.Result;

import java.util.List;

//产品接口
public interface IProductService {

    //查询所有产品
    Result query();

    //查询单个产品
    Result query(String id);

    //查询特定产品
    Result query(Product product);

    //添加产品
    Result save(List<Product> products);

    //更新产品
    Result update(List<Product> products);

    //更新产品
    Result updateById(String id, Integer nums);

    //批量删除产品
    Result delete(List<String> id);

    //删除单个产品
    Result delete(String id);
}
