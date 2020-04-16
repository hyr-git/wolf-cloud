package com.hyr.feign.service.fallback;


import com.hyr.feign.service.IZUUlClientService;
import com.hyr.vo.Product;
import com.hyr.vo.Users;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/***
 *  
 *  在Zuul由于出现网络问题失去联系后进行容错处理
 * @author m1832
 *
 */
@Component
public class IZUUlClientServiceallbackFactory implements FallbackFactory<IZUUlClientService> {
    @Override
    public IZUUlClientService create(Throwable throwable) {
        return  new IZUUlClientService() {
            @Override
            public Product getProduct(long id) {
                Product product = new Product();
                product.setProductId(999999L);
                product.setProductName("feign-zuulName");
                product.setProductDesc("feign-zuulDesc");
                return  product;
            }

            @Override
            public List<Product> listProduct() {
                return null;
            }

            @Override
            public boolean addPorduct(Product product) {
                return false;
            }

            @Override
            public Users getUsers(String name) {
                Users user = new Users();
                user.setSex("F");
                user.setAge(17);
                user.setName("zuul-fllback："+name);
                return user;
            }
        };
    }
}