package com.guangming.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName StartSearch
 * @Description 查题微服务启动类
 * @Author Gm
 * @Date 2020/6/28/028 1:00
 * @Version 1.0
 **/
@SpringBootApplication(scanBasePackages = {"com.guangming"})
@MapperScan(basePackages = {"com.guangming.mapper"})
@EnableEurekaClient
@EnableScheduling
public class StartSearch {
    public static void main(String[] args) {
       SpringApplication.run(StartSearch.class,args);
    }
}
