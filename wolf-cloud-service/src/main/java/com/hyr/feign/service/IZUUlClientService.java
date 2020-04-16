package com.hyr.feign.service;

import com.hyr.feign.FeignClientConfig;
import com.hyr.feign.service.fallback.IZUUlClientServiceallbackFactory;
import com.hyr.vo.Product;
import com.hyr.vo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/***
 * 在Zuul由于出现网络问题失去联系后进行容错处理
 * @author m1832
 *
 */
@FeignClient(name = "wolf-cloud-ZUUL-GATEWAY",configuration = FeignClientConfig.class,
        fallbackFactory = IZUUlClientServiceallbackFactory.class)
public interface IZUUlClientService {

    @RequestMapping("/enjoy-api/product-proxy/prodcut/get/{id}")
    public Product getProduct(@PathVariable("id")long id);

    @RequestMapping("/enjoy-api/product-proxy/prodcut/list")
    public List<Product> listProduct() ;

    @RequestMapping("/enjoy-api/product-proxy/prodcut/add")
    public boolean addPorduct(Product product) ;

    @RequestMapping("/enjoy-api/users-proxy/users/get/{name}")
    public Users getUsers(@PathVariable("name")String name);
}