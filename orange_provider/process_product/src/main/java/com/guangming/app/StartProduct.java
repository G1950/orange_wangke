package com.guangming.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName StartProduct
 * @Description 物品处理微服务启动类
 * @Author Gm
 * @Date 2020/6/26/026 16:14
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.guangming"})
@EnableEurekaClient
@MapperScan(basePackages = {"com.guangming.mapper"})
public class StartProduct {
    public static void main(String[] args) {
        SpringApplication.run(StartProduct.class, args);
    }
}
