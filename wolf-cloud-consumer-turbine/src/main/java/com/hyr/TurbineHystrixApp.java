package com.hyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableTurbine
public class TurbineHystrixApp {
    public static void main(String[] args) {
        SpringApplication.run(TurbineHystrixApp.class,args);
    }
}
