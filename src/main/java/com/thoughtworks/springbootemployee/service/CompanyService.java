package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.Old_CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    private Old_CompanyRepository oldCompanyRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public List<Company> getAllCompaniesService() {
        return companyRepository.findAll();
    }

    public Company findCompanyByIDService(int companyID) {
        return oldCompanyRepository.getAllCompanies()
                .stream()
                .filter(company -> company.getId().equals(companyID))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> getEmployeesByCompanyID(int companyID) {
        return Objects.requireNonNull(getAllCompaniesService()
                .stream()
                .filter(company -> company.getId().equals(companyID))
                .findFirst()
                .orElse(null)).getEmployees();
    }

    public List<Company> getCompaniesByPageService(int pageNumber, int pageSize) {
        int skipCount = (pageNumber - 1) * pageSize;
        return getAllCompaniesService().stream().skip(skipCount).limit(pageSize).collect(Collectors.toList());
    }

    public void addNewCompanyService(Company company) {
        int lastCompanyID = Objects.requireNonNull(oldCompanyRepository.getAllCompanies()
                .stream()
                .max(Comparator.comparingInt(Company::getId))
                .orElse(null))
                .getId();
        Company newCompany = new Company( lastCompanyID +1,
                company.getCompanyName(),
                company.getEmployees()
        );
        oldCompanyRepository.getAllCompanies().add(newCompany);
    }

    public Company updateCompanyService(int companyID, Company companyToBeUpdated) {
        return oldCompanyRepository.getAllCompanies().stream()
                .filter(company -> company.getId().equals(companyID) )
                .findFirst()
                .map(company -> updateCompanyInfo(company,companyToBeUpdated))
                .orElse(null);
    }

    private Company updateCompanyInfo(Company company, Company companyToBeUpdated) {
        if (!Objects.isNull(companyToBeUpdated.getCompanyName())) {
            company.setCompanyName(companyToBeUpdated.getCompanyName());
        }
        if (!Objects.isNull(companyToBeUpdated.getEmployees())) {
            company.setEmployees(companyToBeUpdated.getEmployees());
        }
        return company;
    }

    public void deleteCompanyByIDService(int companyID) {
        oldCompanyRepository.getAllCompanies().remove(findCompanyByIDService(companyID));
    }
}
