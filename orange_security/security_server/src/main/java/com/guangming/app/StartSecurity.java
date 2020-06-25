package com.guangming.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.guangming"})
@MapperScan(basePackages = {"com.guangming.mapper"})
@EnableEurekaClient
public class StartSecurity {
    public static void main(String[] args) {
        SpringApplication.run(StartSecurity.class, args);
    }
}
