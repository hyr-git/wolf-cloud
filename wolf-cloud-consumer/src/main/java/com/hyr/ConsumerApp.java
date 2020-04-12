package com.hyr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.xhm.config.RibbonConfig;


@EnableEurekaClient
@SpringBootApplication
//ribbonClient 中的name指具体的服务名称,若需要多个服务提供方,这个时候可以使用@RibbonClients进行配置
@RibbonClient(name="WOLF-CLOUD-PROVIDER-PRODUCT",configuration=RibbonConfig.class)
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class,args);
    }
}