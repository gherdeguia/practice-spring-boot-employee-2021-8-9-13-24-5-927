package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Directory;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
    private List<Employee> getAllEmployees(){
        return employees;
    }

    @GetMapping(path = "/{employeeID}")
    private Employee findByEmployeeID(@PathVariable int employeeID){
        return employees.stream().filter(employee -> employee.getId().equals(employeeID) ).findFirst().orElse( null );
    }

    @GetMapping(params = {"pageNumber","pageSize"})
    private List<Employee> getEmployeesByPage(@RequestParam("pageNumber") Integer pageNumber,
                                              @RequestParam("pageSize") Integer pageSize) {
        int skipCount = (pageNumber - 1) * pageSize;
        return employees
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
