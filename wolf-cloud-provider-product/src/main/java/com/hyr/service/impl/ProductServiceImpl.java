package com.hyr.service.impl;

import com.hyr.mapper.ProductMapper;
import com.hyr.service.IProductService;
import com.hyr.vo.Product;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public Product get(long id) {
        return productMapper.findById(id);
    }

    @Override
    public boolean add(Product product) {
        return productMapper.create(product);
    }

    @Override
    public List<Product> list() {
        return productMapper.findAll();
    }
}