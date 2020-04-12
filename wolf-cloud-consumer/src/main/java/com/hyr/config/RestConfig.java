package com.hyr.config;

import java.nio.charset.Charset;
import java.util.Base64;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;


/***
 * 使用@LoadBalance注解赋予RestTemplate负载均衡的功能。
 * 使用ribbon的负载均衡功能非常简单,和直接使用restTemplate一样,
 * 只需要给restTemplate添加一个@LoadBalance即可。
 * 
 * 使用ribbon需要获取服务列表,故需要集成eureka，从eureka服务器列表中获取服务列表
 * 故需要在application.yml中添加eureka客户端配置
 * @author m1832
 *
 */
//@Configuration注解不建议在springboot注解启动类的层级下,否咋会引起2个上下文的互串，导致一些不必要的问题。
@Configuration
public class RestConfig {
	
	
    @Bean
    @LoadBalanced   //采用ribbon赋予RestTemplate负载均衡的能力   
    public RestTemplate restTemplate() {
        return  new RestTemplate();
    }
    
    @Bean
    public HttpHeaders getHeaders() { // 要进行一个Http头信息配置
        HttpHeaders headers = new HttpHeaders(); // 定义一个HTTP的头信息
        String auth = "admin:enjoy"; // 认证的原始信息
        byte[] encodedAuth = Base64.getEncoder()
                .encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        return headers;
    }
    
    /***
     * 
     * 全局路由配置---@Configuration与@Bean 可以进行配置全局的负载路由配置
     * 
     * RandomRule --随机访问
     * RoundRobinRule --线性方式轮询访问
     * RetryRule --在RoundRobinRule的基础上添加重试机制,在指定的时间内,反复使用线性轮休策略来选择可用实例
     * WeightedResponseTimeRule --对RoundRobbinRule的扩展,响应速度越快的实例选择权限越大，月容易被选择
     * BestAvailabilityFilteringRule --先过滤故障实例,在选择并发比较小的实例
     * ZoneAwareLoaderBalancer --采用双重过滤,同时过滤不是同一区域的实例和故障实例,选择并发较小的实例。
     *
     * 若希望每个服务提供方的路由规则并不相同时，不能让spring 扫描到Irule,需要通过@RibbonClient来指定服务于配置的关系。
     *
     */
    /*@Bean
    public IRule ribbonRule(){//IRule就是所有规则的标准
    	//以线性轮休的方式
    	return new com.netflix.loadbalancer.RoundRobinRule();
    }*/
    
    
}