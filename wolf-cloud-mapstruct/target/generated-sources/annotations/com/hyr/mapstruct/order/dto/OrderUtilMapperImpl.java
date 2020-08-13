package com.hyr.mapstruct.order.dto;

import com.hyr.mapstruct.order.entity.Order;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-08-13T10:41:13+0800",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_171 (Oracle Corporation)"
)
public class OrderUtilMapperImpl implements OrderUtilMapper {

    @Override
    public OrderQueryParam entity2queryParam(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderQueryParam orderQueryParam = new OrderQueryParam();

        orderQueryParam.setOrderSn( order.getOrderSn() );
        orderQueryParam.setReceiverKeyword( order.getReceiverKeyword() );
        orderQueryParam.setStatus( order.getStatus() );
        orderQueryParam.setOrderType( order.getOrderType() );
        orderQueryParam.setSourceType( order.getSourceType() );

        return orderQueryParam;
    }
}
