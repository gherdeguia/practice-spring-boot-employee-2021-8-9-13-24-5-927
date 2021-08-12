package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import com.thoughtworks.springbootemployee.repository.Old_EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//    public EmployeeService(Old_EmployeesRepository olderEmployeeRepository) {
//        this.olderEmployeeRepository = olderEmployeeRepository;
//    }

    public List<Employee> getAllEmployeesService() {
        return employeesRepository.findAll();
    }

    public Employee findByEmployeeIDService(int employeeID) {
        return olderEmployeeRepository.getEmployees()
                .stream()
                .filter(employee -> employee.getId().equals(employeeID))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeesByPageService(Integer page, Integer pageSize) {
        int skipCount = (page - 1) * pageSize;
        return olderEmployeeRepository.getEmployees()
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeByGenderService(String gender) {
        return olderEmployeeRepository.getEmployees()
                .stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public void addNewEmployeeService(Employee employee) {
        int lastEmployeeID = getLastEmployeeID();
        Employee newEmployee = new Employee(lastEmployeeID + 1,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary()
        );
        olderEmployeeRepository.getEmployees().add(newEmployee);
    }

    private int getLastEmployeeID() {
        return Objects.requireNonNull(olderEmployeeRepository.getEmployees()
                .stream()
                .max(Comparator.comparingInt(Employee::getId))
                .orElse(null))
                .getId();
    }

    public Employee updateEmployeeService(Integer employeeID, Employee employeeToBeUpdate) {
        return olderEmployeeRepository.getEmployees().stream()
                .filter(employee -> employee.getId().equals(employeeID))
                .findFirst()
                .map(employee -> updateEmployeeInfo(employee, employeeToBeUpdate))
                .orElse(null);
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
        olderEmployeeRepository.getEmployees().remove(findByEmployeeIDService(employeeID));
    }
}
