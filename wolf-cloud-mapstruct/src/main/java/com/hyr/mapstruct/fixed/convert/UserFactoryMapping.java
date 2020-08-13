package com.hyr.mapstruct.fixed.convert;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.alibaba.fastjson.JSON;
import com.hyr.mapstruct.fixed.base.BaseMapping;
import com.hyr.mapstruct.fixed.entity.User;
import com.hyr.mapstruct.fixed.vo.UserVo;
import com.hyr.mapstruct.fixed.vo.UserVo.UserConfig;

//@Mapper(componentModel = "spring")
@Mapper
public interface UserFactoryMapping extends BaseMapping<User, UserVo> {
	
	UserFactoryMapping INSTANCE = Mappers.getMapper(UserFactoryMapping.class);

    @Mapping(target = "gender", source = "sex")
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Override
    UserVo sourceToTarget(User var1);

    @Mapping(target = "sex", source = "gender")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Override
    User targetToSource(UserVo var1);

    default List<UserConfig> strConfigToListUserConfig(String config) {
        return  JSON.parseArray(config, UserConfig.class);
    }

    default String listUserConfigToStrConfig(List<UserConfig> list) {
        return JSON.toJSONString(list);
    }
}