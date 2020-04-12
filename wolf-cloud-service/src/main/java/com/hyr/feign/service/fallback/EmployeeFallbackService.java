package com.hyr.feign.service.fallback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hyr.commom.ResultResopnse;
import com.hyr.feign.service.IEmployeeClientService;
import com.hyr.vo.Employee;

@Component
public class EmployeeFallbackService implements IEmployeeClientService{
	
	private final static String fallBackCode = "500";
	
	@Override
    public ResultResopnse<?> insert(Employee user) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

    @Override
    public ResultResopnse<Employee> getEmployee(Long id) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

    @Override
    public ResultResopnse<List<Employee>> listEmployeesByIds(List<Long> ids) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

    @Override
    public ResultResopnse<Employee> getByEmployeeName(String username) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

    @Override
    public ResultResopnse<?> update(Employee user) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

    @Override
    public ResultResopnse<?> delete(Long id) {
        return ResultResopnse.build(fallBackCode,"调用失败，服务被降级");
    }

}
