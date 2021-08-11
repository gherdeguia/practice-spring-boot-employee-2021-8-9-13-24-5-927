package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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

    public List<Employee> getEmployeesByPageService(Integer page, Integer pageSize) {
        int skipCount = (page - 1) * pageSize;
        return employeesRepository.getEmployees()
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeeByGenderService(String gender) {
        return employeesRepository.getEmployees()
                .stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public void addNewEmployee(Employee employee) {
        int lastEmployeeID = employeesRepository.getEmployees().stream().max(Comparator.comparingInt(Employee::getId)).get().getId();
        Employee newEmployee = new Employee( lastEmployeeID +1,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary()
        );
        employeesRepository.getEmployees().add(newEmployee);
    }
}
