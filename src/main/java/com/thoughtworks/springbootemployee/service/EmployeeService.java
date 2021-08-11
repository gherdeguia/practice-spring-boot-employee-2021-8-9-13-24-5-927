package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private EmployeesRepository employeesRepository;

    public EmployeeService(EmployeesRepository employeesRepository) {
        this.employeesRepository = employeesRepository;
    }

    public List<Employee> getAllEmployeesService() {
        return employeesRepository.getEmployees();
    }

    public Employee findByEmployeeIDService(int employeeID) {
        return employeesRepository.getEmployees()
                .stream()
                .filter(employee -> employee.getId().equals(employeeID))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeesByPage(Integer page, Integer pageSize) {
        int skipCount = (page - 1) * pageSize;
        return employeesRepository.getEmployees()
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
