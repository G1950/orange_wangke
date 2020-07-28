package com.guangming.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.guangming"})
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients(basePackages = {"com.guangming.service"})
//@MapperScan(basePackages = {"com.guangming.mapper"})
public class StartConsumerSearch {
    public static void main(String[] args) {
        SpringApplication.run(StartConsumerSearch.class, args);
    }
}
