package com.hyr;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.xhm.config.RibbonConfig;

/***
 * @RibbonClient 注解可以实现ribbon客户端,ribbon需要配置客户端的名称,以及相关的路由配置
 * 主要的核心注解就是@Import(RibbonClientConfigurationRegistrar.class)
 * @author m1832
 *
 */
@SpringBootApplication
//ribbonClient 中的name指具体的服务名称,若需要多个服务提供方,这个时候可以使用@RibbonClients进行配置
@RibbonClient(name="WOLF-CLOUD-PROVIDER-PRODUCT",configuration=RibbonConfig.class)
public class ConsumerAppRibbon {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerAppRibbon.class,args);
    }
}