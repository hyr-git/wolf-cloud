package com.hyr.feign.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.hyr.commom.ResultResopnse;
import com.hyr.feign.service.fallback.EmployeeFallbackService;
import com.hyr.vo.Employee;

@FeignClient(value="WOLF-CLOUD-EMPLOYEE",fallback=EmployeeFallbackService.class)
public interface IEmployeeClientService {

	@PostMapping("/employee/insert")
    ResultResopnse<?> insert(@RequestBody Employee employee);

    @GetMapping("/employee/{id}")
    ResultResopnse<Employee> getEmployee(@PathVariable("id") Long id);

    @GetMapping("/employee/listEmployeesByIds")
    ResultResopnse<List<Employee>> listEmployeesByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/employee/getByEmployeeName")
    ResultResopnse<Employee> getByEmployeeName(@RequestParam("username") String username);

    @PostMapping("/employee/update")
    ResultResopnse<?> update(@RequestBody Employee user);

    @PostMapping("/employee/delete/{id}")
    ResultResopnse<?> delete(@PathVariable("id") Long id);
}
