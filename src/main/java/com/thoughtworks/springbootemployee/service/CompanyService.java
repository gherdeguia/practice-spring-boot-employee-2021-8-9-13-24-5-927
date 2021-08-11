package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public List<Employee> getEmployeesByCompanyID(int companyID) {
        return Objects.requireNonNull(companyRepository.getAllCompanies()
                .stream()
                .filter(company -> company.getId().equals(companyID))
                .findFirst()
                .orElse(null))
                .getEmployees();
    }

    public List<Company> getCompaniesByPageService(int pageNumber, int pageSize) {
        int skipCount = (pageNumber - 1) * pageSize;
        return companyRepository.getAllCompanies()
                .stream()
                .skip(skipCount)
                .limit(pageSize)
                .collect(Collectors.toList());
    }


    public void addNewCompanyService(Company company) {
        int lastCompanyID = companyRepository.getAllCompanies()
                .stream()
                .max(Comparator.comparingInt(Company::getId))
                .get()
                .getId();
        Company newCompany = new Company( lastCompanyID +1,
                company.getName(),
                company.getEmployees()
        );
        companyRepository.getAllCompanies().add(newCompany);
    }

    public Company updateCompanyService(int companyID, Company companyToBeUpdated) {
        return companyRepository.getAllCompanies().stream()
                .filter(company -> company.getId().equals(companyID) )
                .findFirst()
                .map(company -> updateCompanyInfo(company,companyToBeUpdated))
                .get();
    }

    private Company updateCompanyInfo(Company company, Company companyToBeUpdated) {
        if (!Objects.isNull(companyToBeUpdated.getName())) {
            company.setName(companyToBeUpdated.getName());
        }
        if (!Objects.isNull(companyToBeUpdated.getEmployees())) {
            company.setEmployees(companyToBeUpdated.getEmployees());
        }
        return company;
    }
}
