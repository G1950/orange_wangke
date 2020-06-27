package com.guangming.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication(scanBasePackages = {"com.guangming"})
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrix
public class StartUpload {
    public static void main(String[] args) {
        SpringApplication.run(StartUpload.class, args);
    }
}
