package com.hyr.service;

import com.hyr.commom.ResultResopnse;
import com.hyr.vo.Employee;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Description:
 *
 * @author JourWon
 * @date 2019/12/18 16:16
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.user-service}")
    private String userServiceUrl;

    @HystrixCommand(fallbackMethod = "fallbackMethod1")
    public ResultResopnse<?> getEmployee(Long id) {
        return restTemplate.getForObject(userServiceUrl + "/employee/{1}", ResultResopnse.class, id);
    }

    /**
     * 声明的参数需要包含controller的声明参数
     *
     * @param id
     * @return
     */
    public ResultResopnse fallbackMethod1(@PathVariable Long id) {
        return ResultResopnse.build("500","服务调用失败");
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod2", ignoreExceptions = {NullPointerException.class})
    public ResultResopnse getUserException(Long id) {
        if (id == 1) {
            throw new IndexOutOfBoundsException();
        } else if (id == 2) {
            throw new NullPointerException();
        }

        return restTemplate.getForObject(userServiceUrl + "/employee/{1}", ResultResopnse.class, id);
    }

    public ResultResopnse fallbackMethod2(@PathVariable Long id, Throwable e) {
        LOGGER.error("id {},throwable class:{}", id, e.getClass());
        return ResultResopnse.build("500","服务调用失败");
    }

    @HystrixCommand(fallbackMethod = "fallbackMethod1",
            commandKey = "getEmployeeCommand",
            groupKey = "getEmployeeGroup",
            threadPoolKey = "getEmployeeThreadPool")
    public ResultResopnse getEmployeeCommand(Long id) {
        return restTemplate.getForObject(userServiceUrl + "/employee/{1}", ResultResopnse.class, id);
    }


    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "fallbackMethod1", commandKey = "getEmployeeCache")
    public ResultResopnse getEmployeeCache(Long id) {
        LOGGER.info("getUserCache id:{}", id);
        return restTemplate.getForObject(userServiceUrl + "/employee/{1}", ResultResopnse.class, id);
    }

    /**
     * 为缓存生成key的方法
     *
     * @return
     */
    public String getCacheKey(Long id) {
        return String.valueOf(id);
    }

    @HystrixCommand
    @CacheRemove(commandKey = "getUserCache", cacheKeyMethod = "getCacheKey")
    public ResultResopnse removeCache(Long id) {
        LOGGER.info("removeCache id:{}", id);
        return restTemplate.postForObject(userServiceUrl + "/employee/delete/{1}", null, ResultResopnse.class, id);
    }

    @HystrixCollapser(batchMethod = "listEmployeeByIds",collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "100")
    })
    public Future<Employee> getEmployeeFuture(Long id) {
        return new AsyncResult<Employee>() {
            @Override
            public Employee invoke() {
            	ResultResopnse result = restTemplate.getForObject(userServiceUrl + "/employee/{1}", ResultResopnse.class, id);
                Map data = (Map) result.getData();
                Employee user = BeanUtil.mapToBean(data, Employee.class, true);
                LOGGER.info("getUserById username:{}",user.getUsername());
                return user;
            }
        };
    }

    @HystrixCommand
    public List<Employee> listUsersByIds(List<Long> ids) {
        LOGGER.info("listUsersByIds:{}",ids);
        ResultResopnse result = restTemplate.getForObject(userServiceUrl + "/employee/listEmployeeByIds?ids={1}", ResultResopnse.class, CollUtil.join(ids, ","));
        return (List<Employee>)result.getData();
    }


}
