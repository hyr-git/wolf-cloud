package com.cyz.boot.mapper;

import java.time.LocalDate;

import com.cyz.boot.domain.User;
import com.cyz.boot.dto.UserDTO;

public class TestMap {

	public static void main(String[] args) {
		
		UserMapper mapper = UserMapper.INSTANCE;
		
		User user = new User();
		user.setUserName("zhangsan");
		user.setAccount(1024);
		user.setBirthDay(LocalDate.now());
		
		UserDTO userDto = mapper.entityToDTO(user);
		System.out.println(userDto);
	}
}
