package com.hyr.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.commom.ResultResopnse;
import com.hyr.feign.service.IEmployeeClientService;
import com.hyr.vo.Employee;

@RestController
@RequestMapping("/consumer/feign")
public class ConsumerEmployeeController {
	
	@Resource
    private IEmployeeClientService iEmployeeClientService;
	
	private static final Logger logger = LoggerFactory.getLogger("ConsumerEmployeeController");
	
	//添加了ribbon负载均衡 可以使用eureka中的服务名称替代具体的ip加端口访问
	private static final String PROVIDER_SERVER = "WOLF-CLOUD-EMPLOYEE";
	
    
    /*
     * 功能用途：在服务的消费方，可以获取到服务提供方的具体信息
     */
    @Resource
    private LoadBalancerClient loadBalancerClient;


    @PostMapping("/employee/insert")
    ResultResopnse<?> insert(@RequestBody Employee employee){
    	iEmployeeClientService.insert(employee);
    	return ResultResopnse.success();
    }

    @GetMapping("/employee/{id}")
    ResultResopnse<Employee> getEmployee(@PathVariable Long id){
    	//获取服务提供方的信息
    	/*ServiceInstance serviceInstance = this.loadBalancerClient.choose(PROVIDER_SERVER);
    	logger.info("【*** ServiceInstance ***】host = " + serviceInstance.getHost()
        + "、port = " + serviceInstance.getPort()
        + "、serviceId = " + serviceInstance.getServiceId());*/
    	return iEmployeeClientService.getEmployee(id);
    }

    @GetMapping("/employee/listEmployeesByIds")
    ResultResopnse<List<Employee>> listEmployeesByIds(@RequestParam List<Long> ids){
    	return iEmployeeClientService.listEmployeesByIds(ids);
    }

    @GetMapping("/employee/getByEmployeeName")
    ResultResopnse<Employee> getByEmployeeName(@RequestParam String username){
    	return iEmployeeClientService.getByEmployeeName(username);
    }

    @PostMapping("/employee/update")
    ResultResopnse<?> update(@RequestBody Employee user){
    	return iEmployeeClientService.update(user);
    }

    @PostMapping("/employee/delete/{id}")
    ResultResopnse<?> delete(@PathVariable Long id){
    	return iEmployeeClientService.delete(id);
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