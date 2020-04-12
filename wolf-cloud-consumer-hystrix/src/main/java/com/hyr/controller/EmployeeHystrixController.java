package com.hyr.controller;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hyr.commom.ResultResopnse;
import com.hyr.service.EmployeeService;
import com.hyr.vo.Employee;

import cn.hutool.core.thread.ThreadUtil;

public class EmployeeHystrixController {

	@Autowired
    private EmployeeService userService;

    @GetMapping("/testFallback/{id}")
    public ResultResopnse testFallback(@PathVariable("id") Long id) {
        return userService.getEmployee(id);
    }

    @GetMapping("/testException/{id}")
    public ResultResopnse testException(@PathVariable("id") Long id) {
        return userService.getUserException(id);
    }

    @GetMapping("/testCommand/{id}")
    public ResultResopnse getEmployeeCommand(@PathVariable("id") Long id) {
        return userService.getEmployeeCommand(id);
    }

    @GetMapping("/testCache/{id}")
    public ResultResopnse testCache(@PathVariable Long id) {
        userService.getEmployeeCache(id);
        userService.getEmployeeCache(id);
        userService.getEmployeeCache(id);
        return ResultResopnse.success();
    }

    @GetMapping("/testRemoveCache/{id}")
    public ResultResopnse testRemoveCache(@PathVariable Long id) {
        userService.getEmployeeCache(id);
        userService.removeCache(id);
        userService.getEmployeeCache(id);
        return ResultResopnse.success();
    }

    @GetMapping("/testCollapser")
    public ResultResopnse testCollapser() throws ExecutionException, InterruptedException {
        Future<Employee> future1 = userService.getEmployeeFuture(1L);
        Future<Employee> future2 = userService.getEmployeeFuture(2L);
        future1.get();
        future2.get();
        ThreadUtil.safeSleep(200);
        Future<Employee> future3 = userService.getEmployeeFuture(3L);
        future3.get();
        return ResultResopnse.success();
    }
}
