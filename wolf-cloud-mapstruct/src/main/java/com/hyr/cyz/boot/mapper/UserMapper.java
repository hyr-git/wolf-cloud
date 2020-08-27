package com.cyz.boot.mapper;

import com.cyz.boot.domain.User;
import com.cyz.boot.dto.UserDTO;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author:cyz
 * @Date:2020/4/5 20:02
 * @Description:
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
    	  //忽略 categoryId 的转换
       // @Mapping(target = "email",ignore = true),
    	@Mapping(source = "mobile",target = "userName"),
    	@Mapping(source = "accountFloat",target = "account")
    })
    User dtoToEntity(UserDTO dto);
    
    List<User> dtoToEntityList(List<UserDTO> list);
    
    
    @Mappings({
    	@Mapping(source = "userName",target = "mobile"),
    	@Mapping(source = "account",target = "accountFloat")
    })
    UserDTO entityToDTO(User user);
    
    List<UserDTO> entityToDTOList(List<User> list);

   /* //忽略 categoryId 的转换
    @Mapping(target = "categoryId",ignore = true),
    //源数据类中的集合应用 --> 目标类中的数据引用--转换List
    @Mapping(target = "trees",source = "colors"),
    //嵌套类的属性简单传递转换
    @Mapping(target = "run",source = "cart.animal.run"),
   //numberFormat的格式化Number类型 --> Number或String或者String或Number才有效;其余情况无效。
    //string -->Double :*.0(12.0);有效长度16位更精确。
    //string -->Float :单精度，有效长度8位，如果超出会胜率后面的数字；12.00033452或者12.00033456都会转成12.00033452
    @Mapping(target = "numberPerson", source = "cart.number", numberFormat = "0.00", defaultValue = "0"),
    //用到的机会很少不做说明
    @Mapping(target = "birthday", source = "birthDateFormat", dateFormat = "yyyy-MM-dd HH:mm:ss"),*/

    
}
