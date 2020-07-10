package com.guangming.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName StartOauth
 * @Description 资源启动类
 * @Author Gm
 * @Date 2020/7/4/004 0:13
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.guangming"})
@MapperScan(basePackages = {"com.guangming.mapper"})
@EnableEurekaClient
public class StartOauth {
    public static void main(String[] args) {
        SpringApplication.run(StartOauth.class, args);
    }
}
