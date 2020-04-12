package com.hyr.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;

/****
 * wolf-cloud-service模块专门定义客户端的调用接口;
 * 若要使用feign进行远程调用,依然需要安全服务提供方的认证问题，不过在feign里面已经集成了这块功能
 * @author m1832
 *
 */

@Configuration
public class FeignClientConfig {

	@Bean
    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("admin", "enjoy");
    }
	
	
	/*
	 * NONE：默认的，不显示任何日志；
BASIC：仅记录请求方法、URL、响应状态码及执行时间；
HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息；
FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据
	 */
	//通过Feign配置来是Feign打印最详细的http请求日志信息。
	@Bean 
	Logger.Level level(){
	   return Logger.Level.FULL; 
	}
}
