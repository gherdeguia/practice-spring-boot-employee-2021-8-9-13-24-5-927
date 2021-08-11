package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeController(){
        employees.add(new Employee(1,"tom",20,"female",1000));
        employees.add(new Employee(2,"jerry",21,"male",900));
    }

    @GetMapping()
    private List<Employee> getAllEmployees(){
        return employees;
    }

}
