package com.hyr.mapstruct.many2one.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.hyr.mapstruct.many2one.convert.AddressMapper;
import com.hyr.mapstruct.many2one.entity.Address;
import com.hyr.mapstruct.many2one.entity.DeliveryAddress;
import com.hyr.mapstruct.many2one.entity.Person;

public class ManyTest {

	public static void main(String[] args) {
		Person person = new Person();
		person.setFirstName("first");
		person.setLastName("last name");
		person.setDescription("面试过年GV分工表山东省发布");
		person.setHeight(180);
		
		Address address = new Address();
		address.setDescription("addreds  sdgsdgsgn ");
		address.setHouseNo(23);
		address.setStreet("阶段接到");
		address.setZipCode(234);	
		
		DeliveryAddress deliveryAddress = AddressMapper.INSTANCE.personAndAddressToDeliveryAddressDto(person, address);
		
		System.out.println(deliveryAddress);
	}
	
	
	@Test
	public void updateDeliveryAddressFromAddress() {
	    Person person = new Person();
	    person.setFirstName("first");
	    person.setDescription("perSonDescription");
	    person.setHeight(183);
	    person.setLastName("homejim");
	    DeliveryAddress deliveryAddress = AddressMapper.INSTANCE.person2deliveryAddress(person);
	    
	    System.out.println(">>>>>>>"+deliveryAddress);
	    assertEquals(deliveryAddress.getFirstName(), person.getFirstName());
	    assertNull(deliveryAddress.getStreet());
	    
	    
	    Address address = new Address();
	    address.setDescription("addressDescription");
	    address.setHouseNo(29);
	    address.setStreet("street");
	    address.setZipCode(344);
	    AddressMapper.INSTANCE.updateDeliveryAddressFromAddress(address, deliveryAddress);
	    System.out.println("updateDeliveryAddressFromAddress>>>>>>>"+deliveryAddress);

	    assertNotNull(deliveryAddress.getStreet());
	}
}
