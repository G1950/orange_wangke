package com.guangming.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = {"com.guangming"})
@EnableEurekaClient
public class StartGateway {
    public static void main(String[] args) {
        SpringApplication.run(StartGateway.class, args);
    }
}
