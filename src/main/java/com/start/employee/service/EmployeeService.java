package com.start.employee.service;

import com.start.employee.domain.Employee;

/**
 * Created by umesh.pai on 12/20/2017.
 */
public interface EmployeeService
{
	String getEmployeeDetails(String empId);
	String createEmployee(Employee employee);
}
