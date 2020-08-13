package com.hyr.mapstruct.fixed.base;

import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

/***
 * 考虑到项目中对象转换操作基本都一样,抽取一个转换基类,不同的对象若只是简单的转换可以直接继承该基类,
 * 无需复写基类任何方法,即只需要一个空类即可。若子类复写了基类的方法,基类上的@Mapping会失效
 * 
 * 常见问题：
 * 当两个对象属性不一致时，比如User对象中某个字段不存在与UserVo当中时，在编译时会有警告提示，可以在@Mapping中配置 ignore = true，当字段较多时，可以直接在@Mapper中设置unmappedTargetPolicy属性或者unmappedSourcePolicy属性为 ReportingPolicy.IGNORE即可。
 * 如果项目中也同时使用到了 Lombok，一定要注意 Lombok的版本要等于或者高于1.18.10，否则会有编译不通过的情况发生，笔者掉进这个坑很久才爬了出来，希望各位不要重复踩坑。
 * @author mlj
 * https://blog.csdn.net/lose_alan/article/details/105043313
 * @param <SOURCE>
 * @param <TARGET>
 */
@MapperConfig
public interface BaseMapping<SOURCE, TARGET> {

    /**
     * 映射同名属性
     */
    @Mapping(target = "createTime", dateFormat = "yyyy-MM-dd HH:mm:ss")
    TARGET sourceToTarget(SOURCE var1);

    /**
     * 反向，映射同名属性
     */
    @InheritInverseConfiguration(name = "sourceToTarget")
    SOURCE targetToSource(TARGET var1);

    /**
     * 映射同名属性，集合形式
     */
    @InheritConfiguration(name = "sourceToTarget")
    List<TARGET> sourceToTarget(List<SOURCE> var1);

    /**
     * 反向，映射同名属性，集合形式
     */
    @InheritConfiguration(name = "targetToSource")
    List<SOURCE> targetToSource(List<TARGET> var1);

    /**
     * 映射同名属性，集合流形式
     */
    List<TARGET> sourceToTarget(Stream<SOURCE> stream);

    /**
     * 反向，映射同名属性，集合流形式
     */
    List<SOURCE> targetToSource(Stream<TARGET> stream);
}