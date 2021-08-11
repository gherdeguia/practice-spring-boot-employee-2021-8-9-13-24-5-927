package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompaniesService() {
        return companyRepository.getAllCompanies();
    }

    public Company findCompanyByIDService(int companyID) {
        return companyRepository.getAllCompanies()
                .stream()
                .filter(company -> company.getId().equals(companyID))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getCompanyEmployeesListByID(int companyID) {
        return null;
    }
}
