package com.hyr.employee.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyr.commom.ResultResopnse;
import com.hyr.employee.service.EmployeeService;
import com.hyr.vo.Employee;

/**
 * Description:
 * @author JourWon
 * @date 2019/12/18 11:52
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(value="/insert")
    public ResultResopnse insert(@RequestBody Employee user) {
        employeeService.insert(user);
        return ResultResopnse.success();
    }

    @GetMapping("/{id}")
    public ResultResopnse<Employee> getEmployee(@PathVariable Long id) {
        Employee user = employeeService.getEmployee(id);
        LOGGER.info("根据id获取用户信息，用户名称为：{}",user.getUsername());
        return ResultResopnse.success(user);
    }

    @GetMapping("/listEmployeesByIds")
    public ResultResopnse<List<Employee>> listEmployeesByIds(@RequestParam List<Long> ids) {
        List<Employee> userList= employeeService.listEmployeesByIds(ids);
        LOGGER.info("根据ids获取用户信息，用户列表为：{}",userList);
        return ResultResopnse.success(userList);
    }
    
    @GetMapping("/listEmployees")
    public ResultResopnse<List<Employee>> listEmployee() {
        List<Employee> userList= employeeService.listEmployees();
        LOGGER.info("根据ids获取用户信息，用户列表为：{}",userList);
        return ResultResopnse.success(userList);
    }

    @GetMapping("/getByEmployeeName")
    public ResultResopnse<Employee> getByEmployeeName(@RequestParam String username) {
        Employee user = employeeService.getByEmployeeName(username);
        return ResultResopnse.success(user);
    }

    @PostMapping("/update")
    public ResultResopnse<?> update(@RequestBody Employee user) {
        employeeService.update(user);
        return ResultResopnse.success();
    }

    @PostMapping("/delete/{id}")
    public ResultResopnse<?> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResultResopnse.success();
    }
}   