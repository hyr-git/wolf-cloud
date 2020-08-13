package com.hyr.mapstruct.many2one.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.hyr.mapstruct.many2one.entity.Address;
import com.hyr.mapstruct.many2one.entity.DeliveryAddress;
import com.hyr.mapstruct.many2one.entity.Person;


/***
 * 多个对象转换成一个的场景
 * @author mlj
 *
 */
@Mapper
public interface AddressMapper {
	
	//通过 Mapper 工厂获取
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    
    @Mapping(source = "person.description", target = "description")
    @Mapping(source = "address.houseNo", target = "houseNumber")
    DeliveryAddress personAndAddressToDeliveryAddressDto(Person person, Address address);
    
    
    /**
     * Person->DeliveryAddress, 缺失地址信息
     * @param person
     * @return
     */
    DeliveryAddress person2deliveryAddress(Person person);
    
    
    /**
     * 备注：不是想反悔一个新的bean对象,而是希望更新传入对象的一些熟悉(替换原有的对象中的部分属性)。
     * 更新， 使用 Address 来补全 DeliveryAddress 信息。 注意注解 @MappingTarget
     * @param address
     * @param deliveryAddress
     * 
     * 注解 @MappingTarget后面跟的对象会被更新
     */
    void updateDeliveryAddressFromAddress(Address address,
                                          @MappingTarget DeliveryAddress deliveryAddress);
}
