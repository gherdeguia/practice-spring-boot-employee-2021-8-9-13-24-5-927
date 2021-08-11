package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeController(){
        employees.add(new Employee(1,"tom",20,"female",1000));
        employees.add(new Employee(2,"jerry",21,"male",900));
        employees.add(new Employee(3,"Gerarld",23,"male",1100));
        employees.add(new Employee(4,"Lorenz",21,"male",800));
        employees.add(new Employee(5,"Falcon",21,"male",2000));
        employees.add(new Employee(6,"tomB",20,"female",1000));
        employees.add(new Employee(7,"jerryB",21,"male",900));
        employees.add(new Employee(8,"GerarldB",23,"male",1100));
        employees.add(new Employee(9,"LorenzB",21,"male",800));
        employees.add(new Employee(10,"FalconB",21,"male",2000));
    }

    @GetMapping()
    public List<Employee> getAllEmployees(){
        return employees;
    }

    @GetMapping(path = "/{employeeID}")
    public Employee findByEmployeeID(@PathVariable int employeeID){
        return employees.stream().filter(employee -> employee.getId().equals(employeeID) ).findFirst().orElse( null );
    }

    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam("page") Integer page,
                                              @RequestParam("pageSize") Integer pageSize) {
        int skipCount = (page - 1) * pageSize;
        return employees
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam("gender") String gender) {

        return employees
                .stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    @PostMapping
    public void addNewEmployee(@RequestBody Employee employee){
        int lastEmployeeID = employees.stream().max(Comparator.comparingInt(Employee::getId)).get().getId();
        Employee newEmployee = new Employee( lastEmployeeID +1,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary()
        );

        employees.add(newEmployee);
    }

    @PutMapping(path = "/{employeeID}")
    public Employee updateEmployee(@PathVariable Integer employeeID,
                                   @RequestBody Employee employeeToBeUpdate) {
        Employee employeeToBeUpdated = employees.stream()
                .filter(employee -> employee.getId().equals(employeeID) )
                .findFirst()
                .map(employee -> updateEmployeeInfo(employee,employeeToBeUpdate))
                .get();

        return employeeToBeUpdated;
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

    @DeleteMapping(path="/{employeeID}")
    public void deleteEmployeeByID(@PathVariable int employeeID) {
        Employee employeeToDelete = employees.stream().filter(employee -> employee.getId().equals(employeeID)).findFirst().orElse(null);
        employees.remove(employeeToDelete);
    }

}
