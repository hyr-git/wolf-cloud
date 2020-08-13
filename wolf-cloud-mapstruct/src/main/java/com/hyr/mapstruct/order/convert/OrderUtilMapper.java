package com.hyr.mapstruct.order.convert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.hyr.mapstruct.order.dto.OrderQueryParam;
import com.hyr.mapstruct.order.entity.Order;

@Mapper
public interface OrderUtilMapper {
	
    OrderUtilMapper INSTANCE = Mappers.getMapper(OrderUtilMapper.class);
	
    OrderQueryParam entity2queryParam(Order order);
}