package com.hyr.employee.service;


import java.util.List;

import com.hyr.vo.Employee;

/**
 * Description:
 *
 * @author JourWon
 * @date 2019/12/18 14:06
 */
public interface EmployeeService {

    void insert(Employee user);

    Employee getEmployee(Long id);

    void update(Employee user);

    void delete(Long id);

    Employee getByEmployeeName(String username);

    List<Employee> listEmployeesByIds(List<Long> ids);

}
