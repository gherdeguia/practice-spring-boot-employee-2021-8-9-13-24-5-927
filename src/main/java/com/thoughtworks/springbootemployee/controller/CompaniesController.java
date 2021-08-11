package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(params = {"page","pageSize"})
    public List<Company> getEmployeesByPage(@RequestParam("page") Integer page,
                                             @RequestParam("pageSize") Integer pageSize) {
        return companyService.getCompaniesByPageService(page,pageSize);
    }

    @PostMapping
    public void addNewCompany(@RequestBody Company company){
        companyService.addNewCompanyService(company);
    }

    @PutMapping(path = "/{companyID}")
    public Company updateCompanyByID(@PathVariable Integer companyID,
                                   @RequestBody Company companyToBeUpdated) {
        return companyService.updateCompanyService(companyID, companyToBeUpdated);
    }

    @DeleteMapping(path="/{companyID}")
    public void deleteCompanyByID(@PathVariable int companyID) {
        companyService.deleteCompanyByIDService(companyID);
    }
}
