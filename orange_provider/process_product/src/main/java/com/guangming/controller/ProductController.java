package com.guangming.controller;

import com.guangming.pojo.Product;
import com.guangming.service.IProductService;
import com.guangming.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ProductController
 * @Description 产品处理微服务，对外接口
 * @Author Gm
 * @Date 2020/6/26/026 16:23
 * @Version 1.0
 **/
@RestController
public class ProductController {
    @Resource
    private IProductService productService;

    //查询单个产品
    @GetMapping("/products/{id}")
    public Result queryProducts(@PathVariable("id") String id) {
        return productService.query(id);
    }

    //查询所有或特定产品
    @GetMapping("/products")
    public Result queryProducts(Product product) {
        System.out.println(product);
        return productService.query(product);
    }

    //添加产品
    @PostMapping("/products")
    public Result saveProducts(@RequestBody List<Product> products) {
        return productService.save(products);
    }

    //更新产品
    @PutMapping("/products")
    public Result updateProducts(@RequestBody List<Product> products) {
        return productService.update(products);
    }
    //更新产品库存
    @PutMapping("/products/{id}/{nums}")
    public Result updateProducts(@PathVariable("id")String id ,@PathVariable("nums")Integer nums) {
        return productService.updateById(id,nums);
    }

    //批量删除产品
    @DeleteMapping("/products")
    public Result deleteProducts(@RequestBody List<String> id) {
        return productService.deleteById(id);
    }

}
