package com.hyr.controller;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.commom.ResultResopnse;
import com.hyr.service.IProductService;
import com.hyr.vo.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

@RestController
@RequestMapping("/prodcut")
public class ProductController {

	    Logger logger = LoggerFactory.getLogger("ProductController");
	
	    @Resource
	    private IProductService iProductService;

	    @Resource
	    private DiscoveryClient client ; // 进行Eureka的发现服务

	    @RequestMapping(value="/get/{id}")
	    @HystrixCommand(fallbackMethod="getFallback")
	    public Object get(@PathVariable("id") long id) {
	    	Product product = this.iProductService.get(id);
	    	if(product == null) {
	    		throw new RuntimeException("该产品已下架！");
	    	}
	        return  product;
	    }
	    
	    public Object  getFallback(@PathVariable("id") long id){
	        Product product = new Product();
	        product.setProductName("HystrixName");
	        product.setProductDesc("HystrixDesc");
	        product.setProductId(0L);
	        return product;
	    }
	    
	    @RequestMapping(value="/add")
	    public Object add(@RequestBody Product product) {
	        return this.iProductService.add(product) ;
	    }
	    
	    
	    /**
	     * 不同的查询采用不同的业务线程池,进行资源进隔离
	     * @return
	     */
	    @HystrixCommand(fallbackMethod = "fallbackMethod1",
	            commandKey = "listCommand",
	            groupKey = "listGroup",
	            threadPoolKey = "listThreadPool")
	    @RequestMapping(value="/list")
	    public Object list() {
	        return this.iProductService.list() ;
	    }
	    
	    /**
	     * 声明的参数需要包含controller的声明参数
	     * @param id
	     * @return
	     */
	    public ResultResopnse fallbackMethod1(@PathVariable Long id) {
	        return ResultResopnse.build("500","服务调用失败");
	    }

	    /***
	     * 熔断异常处理,忽略掉空指针异常不进行路由熔断处理
	     * @param id
	     * @return
	     */
	    @HystrixCommand(fallbackMethod = "fallbackMethod2", ignoreExceptions = {NullPointerException.class})
	    public ResultResopnse getUserException(Long id) {
	        if (id == 1) {
	            throw new IndexOutOfBoundsException();
	        } else if (id == 2) {
	            throw new NullPointerException();
	        }

	         Product product = this.iProductService.get(id);
	         return ResultResopnse.success(product);
	    }

	    public ResultResopnse fallbackMethod2(@PathVariable Long id, Throwable e) {
	    	logger.error("id {},throwable class:{}", id, e.getClass());
	        return ResultResopnse.build("500","服务调用失败");
	    }
	    
	    @RequestMapping("/discover")
	    public Object discover() { // 直接返回发现服务信息
	        return this.client ;
	    }
	    
	    @HystrixCollapser(batchMethod = "listProductByIds",collapserProperties = {
	            @HystrixProperty(name = "timerDelayInMilliseconds",value = "100")
	    })
	    public Future<List<Product>> getEmployeeFuture(Long id) {
	        return new AsyncResult<List<Product>>() {
	            @Override
	            public List<Product> invoke() {
	            	List<Product> list= iProductService.list(); 
	                logger.info("getUserById username:{}",list.size());
	                return list;
	            }
	        };
	    }

	    @HystrixCommand
	    public ResultResopnse<List<Product>> listProductByIds(List<Long> ids) {
	        logger.info("listProductByIds:{}",ids);
	        List<Product> list= iProductService.list();
	        return ResultResopnse.success(list);
	    }
}