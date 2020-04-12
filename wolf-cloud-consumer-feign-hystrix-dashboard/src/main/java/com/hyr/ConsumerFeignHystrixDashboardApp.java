package com.hyr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


@SpringBootApplication
@EnableHystrixDashboard
public class ConsumerFeignHystrixDashboardApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignHystrixDashboardApp.class,args);
    }
}