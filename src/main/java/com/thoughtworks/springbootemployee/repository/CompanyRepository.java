package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private List<Company> companyRepository = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();

    public CompanyRepository() {
        populateEmployees();
        companyRepository.add(new Company(1, "Spring", employees));
        companyRepository.add(new Company(2, "BigPegs", employees));
        companyRepository.add(new Company(3, "Prime", employees));
        companyRepository.add(new Company(4, "DLSI", employees));
    }

    private void populateEmployees() {
        employees.add(new Employee(1, "Winry", 20, "female", 1000));
        employees.add(new Employee(2, "Linlin", 21, "female", 900));
        employees.add(new Employee(3, "GG", 23, "male", 1100));
        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
        employees.add(new Employee(5, "Ameer", 21, "male", 2000));
        employees.add(new Employee(6, "Eric", 20, "male", 1000));
        employees.add(new Employee(7, "Aryann", 21, "male", 900));
        employees.add(new Employee(8, "Francis", 23, "male", 1100));
        employees.add(new Employee(9, "Alfonse", 21, "male", 800));
        employees.add(new Employee(10, "Edward", 21, "male", 2000));
    }

    public List<Company> getAllCompanies() {
        return companyRepository;
    }
}
