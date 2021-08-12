package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Repository
public class Old_EmployeesRepository {

    private List<Employee> employees = new ArrayList<>();

    public Old_EmployeesRepository() {
        employees.add(new Employee(1, "tom", 20, "female", 1000));
        employees.add(new Employee(2, "jerry", 21, "male", 900));
        employees.add(new Employee(3, "Gerarld", 23, "male", 1100));
        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
        employees.add(new Employee(5, "Falcon", 21, "male", 2000));
        employees.add(new Employee(6, "tomB", 20, "female", 1000));
        employees.add(new Employee(7, "jerryB", 21, "male", 900));
        employees.add(new Employee(8, "GerarldB", 23, "male", 1100));
        employees.add(new Employee(9, "LorenzB", 21, "male", 800));
        employees.add(new Employee(10, "FalconB", 21, "male", 2000));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Employee findByEmployeeID(int employeeID) {
        return getEmployees()
                .stream()
                .filter(employee -> employee.getId().equals(employeeID))
                .findFirst()
                .orElse(null);
    }

    public void addNewEmployee(Employee employee) {
        int lastEmployeeID = getLastEmployeeID();
        Employee newEmployee = new Employee(lastEmployeeID + 1,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary()
        );
        employees.add(newEmployee);
    }

    private int getLastEmployeeID() {
        return Objects.requireNonNull(getEmployees()
                .stream()
                .max(Comparator.comparingInt(Employee::getId))
                .orElse(null))
                .getId();
    }
}
