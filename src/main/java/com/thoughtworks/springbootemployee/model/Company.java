package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private Integer id;
    private String name;
    private List<Employee> employees;

    public Company(){

    }

    public Company(int companyID, String companyName, List<Employee> employees) {
        this.id = companyID;
        this.name = companyName;
        this.employees = employees;
    }

    public Company(String companyName, List<Employee> employees) {
        this.id = null;
        this.name = companyName;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
