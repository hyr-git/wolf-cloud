package com.xhm.config;

import org.springframework.context.annotation.Bean;

import com.netflix.loadbalancer.IRule;


/****
 * 新增一个路由骨子额的配置类，这个类不应该放到spring cloud扫描的位置，否则又会编程全局的IRule，
 * 所以这个时候应该单独使用一个新的包，这个包不能和启动类放在同一个包下
 * @author m1832
 *
 */
public class RibbonConfig {
	
	/***
     * 
     * 全局路由配置
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
    @Bean
    public IRule ribbonRule(){//IRule就是所有规则的标准
    	//以线性轮休的方式
    	return new com.netflix.loadbalancer.RoundRobinRule();
    }

}
