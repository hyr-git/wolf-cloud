package com.hyr.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.commom.ResultResopnse;
import com.hyr.feign.service.IEmployeeClientService;
import com.hyr.feign.service.IProudctClientService;
import com.hyr.vo.Product;

@RestController
@RequestMapping("/consumer")
public class ConsumerProductController {
	
	@Resource
    private IProudctClientService iProductClientService;
	
	@Resource
    private IEmployeeClientService iEmployeeClientService;
	
	private static final Logger logger = LoggerFactory.getLogger("ConsumerProductController");
	
	//添加了ribbon负载均衡 可以使用eureka中的服务名称替代具体的ip加端口访问
	private static final String PROVIDER_SERVER = "WOLF-CLOUD-PROVIDER-PRODUCT";
	
    
    /*
     * 功能用途：在服务的消费方，可以获取到服务提供方的具体信息
     */
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping(value="/product/get",method=RequestMethod.GET)
    public ResultResopnse<Product> getProduct(long id) {
    	/* Product product = restTemplate.exchange(PRODUCT_GET_URL + id,HttpMethod.GET,new HttpEntity<Object>(httpHeaders), Product.class).getBody();
        return  ResultResopnse.success(product);*/
    	Product product =  iProductClientService.getProduct(id);
    	return  ResultResopnse.success(product);
    }

    @RequestMapping(value="/product/list",method=RequestMethod.GET)
    public  ResultResopnse<List<Product>> listProduct() {
    	
    	//获取服务提供方的信息
    	ServiceInstance serviceInstance = this.loadBalancerClient.choose(PROVIDER_SERVER);
    	logger.info("【*** ServiceInstance ***】host = " + serviceInstance.getHost()
        + "、port = " + serviceInstance.getPort()
        + "、serviceId = " + serviceInstance.getServiceId());
    	
        //List<Product> list = restTemplate.exchange(PRODUCT_LIST_URL,HttpMethod.GET,new HttpEntity<Object>(httpHeaders), List.class).getBody();
    	List<Product> list = iProductClientService.listProduct();
    	return  ResultResopnse.success(list);
    }

    @RequestMapping(value="/product/add",method=RequestMethod.POST)
    public ResultResopnse<Boolean> addPorduct(Product product) {
        //Boolean result = restTemplate.exchange(PRODUCT_ADD_URL, HttpMethod.POST,new HttpEntity<Object>(product,httpHeaders), Boolean.class).getBody();
    	Boolean result = iProductClientService.addPorduct(product);
        return  ResultResopnse.success(result);
    }
    
    
    
    
   /* @GetMapping("/getEntityByUsername")
    public ResultResopnse getEntityByUsername(@RequestParam String username) {
        ResponseEntity<Result> entity = restTemplate.getForEntity(userServiceUrl + "/user/getByUsername?username={1}", Result.class, username);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        } else {
            return new Result("操作失败", 500);
        }
    }*/
    
    /*@RequestMapping("/product/get")
    public Object getProduct(long id) {
        Product product = restTemplate.getForObject(PRODUCT_GET_URL + id, Product.class);
        return  product;
    }

    @RequestMapping("/product/list")
    public  Object listProduct() {
        List<Product> list = restTemplate.getForObject(PRODUCT_LIST_URL, List.class);
        return  list;
    }

    @RequestMapping("/product/add")
    public Object addPorduct(Product product) {
        Boolean result = restTemplate.postForObject(PRODUCT_ADD_URL, product, Boolean.class);
        return  result;
    }*/
}