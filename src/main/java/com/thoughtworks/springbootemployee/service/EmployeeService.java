package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import com.thoughtworks.springbootemployee.repository.Old_EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    private Old_EmployeesRepository olderEmployeeRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    public List<Employee> getAllEmployeesService() {
        return employeesRepository.findAll();
    }

    public Employee findByEmployeeIDService(int employeeID) {
        return employeesRepository.findById(employeeID).orElse(null);
    }

    public Employee findByEmployeeNameService(String employeeName) {
        return employeesRepository.findByName(employeeName);
    }

    public List<Employee> getEmployeesByPageService(Integer page, Integer pageSize) {
        return employeesRepository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
    }

    public List<Employee> getEmployeeByGenderService(String gender) {
        return employeesRepository.findAllByGender(gender);
    }

    public Employee addNewEmployeeService(Employee employee) {
        return employeesRepository.save(employee);
    }

    public Employee updateEmployeeService(Integer employeeID, Employee employeeToBeUpdate) {
        Employee employee = findByEmployeeIDService(employeeID);
        return employeesRepository.save(updateEmployeeInfo(employee,employeeToBeUpdate));
    }

    private Employee updateEmployeeInfo(Employee employee, Employee employeeToBeUpdate) {
        if (!Objects.isNull(employeeToBeUpdate.getName())) {
            employee.setName(employeeToBeUpdate.getName());
        }
        if (!Objects.isNull(employeeToBeUpdate.getAge())) {
            employee.setAge(employeeToBeUpdate.getAge());
        }
        if (!Objects.isNull(employeeToBeUpdate.getGender())) {
            employee.setGender(employeeToBeUpdate.getGender());
        }
        if (!Objects.isNull(employeeToBeUpdate.getSalary())) {
            employee.setSalary(employeeToBeUpdate.getSalary());
        }
        return employee;
    }

    public void deleteEmployeeService(Integer employeeID) {
        employeesRepository.findAll().remove(findByEmployeeIDService(employeeID));
    }
}
