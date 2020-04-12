package com.hyr.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hyr.commom.ResultResopnse;
import com.hyr.vo.Product;

@RestController
@RequestMapping("/consumer")
public class ConsumerProductController {
	
	private static final Logger logger = LoggerFactory.getLogger("ConsumerProductController");
	
	//添加了ribbon负载均衡 可以使用eureka中的服务名称替代具体的ip加端口访问
	private static final String PROVIDER_SERVER = "WOLF-CLOUD-PROVIDER-PRODUCT";
	
    public static final String PRODUCT_GET_URL = "http://"+PROVIDER_SERVER+"/prodcut/get/";
    public static final String PRODUCT_LIST_URL="http://"+PROVIDER_SERVER+"/prodcut/list/";
    public static final String PRODUCT_ADD_URL = "http://"+PROVIDER_SERVER+"/prodcut/add/";
    
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private HttpHeaders httpHeaders;
    
    /*
     * 功能用途：在服务的消费方，可以获取到服务提供方的具体信息
     */
    @Resource
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/product/get")
    public ResultResopnse<Product> getProduct(long id) {
        Product product = restTemplate.exchange(PRODUCT_GET_URL + id,HttpMethod.GET,new HttpEntity<Object>(httpHeaders), Product.class).getBody();
        return  ResultResopnse.success(product);
    }

    @RequestMapping("/product/list")
    public  ResultResopnse<List<Product>> listProduct() {
    	
    	//获取服务提供方的信息
    	ServiceInstance serviceInstance = this.loadBalancerClient.choose(PROVIDER_SERVER);
    	logger.info("【*** ServiceInstance ***】host = " + serviceInstance.getHost()
        + "、port = " + serviceInstance.getPort()
        + "、serviceId = " + serviceInstance.getServiceId());
    	
    	URI uri = URI.create(String.format("http://%s:%s/prodcut/list/" ,
                serviceInstance.getHost(), serviceInstance.getPort()));
    	
        List<Product> list = restTemplate.exchange(uri,HttpMethod.GET,new HttpEntity<Object>(httpHeaders), List.class).getBody();
        return  ResultResopnse.success(list);
    }

    @RequestMapping("/product/add")
    public ResultResopnse<Boolean> addPorduct(Product product) {
        Boolean result = restTemplate.exchange(PRODUCT_ADD_URL, HttpMethod.POST,new HttpEntity<Object>(product,httpHeaders), Boolean.class).getBody();
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