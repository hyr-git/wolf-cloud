package com.hyr.employee.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyr.employee.service.EmployeeService;
import com.hyr.vo.Employee;

/**
 * Description:
 *
 * @author JourWon
 * @date 2019/12/18 14:08
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private List<Employee> employeeList;

    @Override
    public void insert(Employee user) {
    	employeeList.add(user);
    }

    @Override
    public Employee getEmployee(Long id) {
        List<Employee> list = employeeList.stream().filter(userItem -> userItem.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void update(Employee user) {
    	employeeList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(userItem -> {
            userItem.setUsername(user.getUsername());
            userItem.setPassword(user.getPassword());
        });
    }

    @Override
    public void delete(Long id) {
        Employee user = getEmployee(id);
        if (user != null) {
        	employeeList.remove(user);
        }
    }

    @Override
    public Employee getByEmployeeName(String username) {
        List<Employee> list = employeeList.stream().filter(userItem -> userItem.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Employee> listEmployeesByIds(List<Long> ids) {
        return employeeList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
    	employeeList = new ArrayList<>();
    	employeeList.add(new Employee(1L, "jourwon", "123456"));
    	employeeList.add(new Employee(2L, "andy", "123456"));
    	employeeList.add(new Employee(3L, "mark", "123456"));
    }

	@Override
	public List<Employee> listEmployees() {
		return employeeList;
	}

}
