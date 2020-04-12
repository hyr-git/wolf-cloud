package com.hyr.feign.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hyr.feign.FeignClientConfig;
import com.hyr.feign.service.fallback.IProductClientServiceFallbackFactory;
import com.hyr.vo.Product;

/***
 * 指定name为微服务的wolf-CLOUD-PROVIDER-PRODUCT,设置配置类configuration为FeignClientConfig.class
 * @author m1832
 *
 */
@FeignClient(name="wolf-CLOUD-PROVIDER-PRODUCT",configuration=FeignClientConfig.class,fallbackFactory = IProductClientServiceFallbackFactory.class)
public interface IProudctClientService {
	
	@RequestMapping(name="/prodcut/get/{id}",method=RequestMethod.GET)
	public Product getProduct(@PathVariable("id")long id);

    @RequestMapping(name="/prodcut/list",method=RequestMethod.GET)
    public  List<Product> listProduct() ;

    @RequestMapping(name="/prodcut/add",method=RequestMethod.POST)
    public boolean addPorduct(Product product) ;

}
