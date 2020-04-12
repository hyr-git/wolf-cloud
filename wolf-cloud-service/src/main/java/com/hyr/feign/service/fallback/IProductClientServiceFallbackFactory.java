package com.hyr.feign.service.fallback;

import com.hyr.feign.service.IProudctClientService;
import com.hyr.vo.Product;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;


/***
 * IProductClientService的失败调用(降级处理)
 * @author m1832
 *
 */
@Component
public class IProductClientServiceFallbackFactory implements FallbackFactory<IProudctClientService> {
    @Override
    public IProudctClientService create(Throwable throwable) {
        return  new IProudctClientService() {
            @Override
            public Product getProduct(long id) {
                Product product = new Product();
                product.setProductId(999999L);
                product.setProductName("feign-hystrixName");
                product.setProductDesc("feign-hystrixDesc");
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
        };
    }
}
