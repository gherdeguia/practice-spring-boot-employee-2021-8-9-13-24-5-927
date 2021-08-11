package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    @Autowired
    CompanyService companyService;

    @GetMapping()
    public List<Company> getAllCompanies() {
        return companyService.getAllCompaniesService();
    }

    @GetMapping(path = "/{companyID}")
    public Company findByEmployeeID(@PathVariable int companyID){
        return companyService.findCompanyByIDService(companyID);
    }

    @GetMapping(path = "/{companyID}/employees")
    public List<Employee> getEmployeesByCompanyID(@PathVariable int companyID){
        return companyService.getEmployeesByCompanyID(companyID);
    }
}
