package com.hyr.mapstruct.order.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.hyr.mapstruct.order.convert.OrderUtilMapper;
import com.hyr.mapstruct.order.dto.OrderQueryParam;
import com.hyr.mapstruct.order.entity.Order;

public class MapStructTest {
	
	
	public static void main(String[] args) {
		entity2queryParam();
	}
	
	@Test
    public static void entity2queryParam() {
        Order order = new Order();
        order.setId(12345L);
        order.setOrderSn("orderSn");
        order.setOrderType(0);
        order.setReceiverKeyword("keyword");
        order.setSourceType(1);
        order.setStatus(2);
        
        OrderQueryParam orderQueryParam = OrderUtilMapper.INSTANCE.entity2queryParam(order);
        System.out.println(orderQueryParam);
        assertEquals(orderQueryParam.getOrderSn(), order.getOrderSn());
        assertEquals(orderQueryParam.getOrderType(), order.getOrderType());
        assertEquals(orderQueryParam.getReceiverKeyword(), order.getReceiverKeyword());
        assertEquals(orderQueryParam.getSourceType(), order.getSourceType());
        assertEquals(orderQueryParam.getStatus(), order.getStatus());
    }
}
