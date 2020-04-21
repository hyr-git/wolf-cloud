package com.hyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableCircuitBreaker //开启hystrix熔断
@EnableEurekaClient
public class UsersHystrixApp {
    public static void main(String[] args) {
        SpringApplication.run(UsersHystrixApp.class,args);
    }
}