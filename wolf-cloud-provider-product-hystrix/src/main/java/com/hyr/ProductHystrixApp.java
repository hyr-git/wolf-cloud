package com.hyr;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@MapperScan("com.hyr.mapper")
@EnableEurekaClient//
@EnableDiscoveryClient
@EnableCircuitBreaker  //开启hystrix断路器功能
public class ProductHystrixApp{
    public static void main(String[] args) {
        SpringApplication.run(ProductHystrixApp.class,args);
    }
}