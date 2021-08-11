package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;
    private EmployeeService employeeService;

    @Mock
    private CompanyRepository companyRepository;
    private EmployeesRepository employeesRepository;

    @Test
    public void should_return_all_companies_when_get_given_all_companies() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        employees_list2.add(new Employee(1, "Ling Yao", 20, "male", 1000));
        employees_list2.add(new Employee(2, "Lan Fan", 18, "female", 850));
        employees_list2.add(new Employee(3, "Mei Chang", 16, "female", 900));


        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list2));
        given(companyRepository.getAllCompanies()).willReturn(companies);
        //when
        List<Company> actualCompanies = companyService.getAllCompaniesService();

        //then
        assertIterableEquals(companies, actualCompanies);
    }

    @Test
    public void should_return_company_when_get_given_company_id() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        employees_list2.add(new Employee(1, "Ling Yao", 20, "male", 1000));
        employees_list2.add(new Employee(2, "Lan Fan", 18, "female", 850));
        employees_list2.add(new Employee(3, "Mei Chang", 16, "female", 900));


        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list2));
        given(companyRepository.getAllCompanies()).willReturn(companies);
        //when
        Company outputCompany = companyService.findCompanyByIDService(1);

        //then

        assertEquals(companies.get(0),outputCompany);
        assertEquals(companies.get(0).getId(),outputCompany.getId());
        assertEquals(companies.get(0).getName(),outputCompany.getName());
    }
    @Test
    public void should_return_employees_when_get_given_company_id() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        employees_list2.add(new Employee(1, "Ling Yao", 20, "male", 1000));
        employees_list2.add(new Employee(2, "Lan Fan", 18, "female", 850));
        employees_list2.add(new Employee(3, "Mei Chang", 16, "female", 900));


        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list2));
        given(companyRepository.getAllCompanies()).willReturn(companies);
        //when
        List<Employee> outputCompanyEmployees = companyService.getEmployeesByCompanyID(3);

        //then
        assertIterableEquals(employees_list2,outputCompanyEmployees);
        assertEquals(employees_list2,outputCompanyEmployees);
    }

    @Test
    public void should_return_companies_per_page_when_get_give_companies_and_page_size_and_page_number(){
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list1));
        companies.add(new Company(4, "Marley", employees_list1));
        companies.add(new Company(5, "CERN", employees_list1));
        companies.add(new Company(6, "DLSI", employees_list1));
        companies.add(new Company(7, "Prime", employees_list1));
        companies.add(new Company(8, "Big Pegs", employees_list1));
        given(companyRepository.getAllCompanies()).willReturn(companies);

        //when
        List<Company> outputCompaniesByPages = companyService.getCompaniesByPageService(1,5);

        //then
        assertEquals(5, outputCompaniesByPages.size());
        assertEquals(1, outputCompaniesByPages.get(0).getId());
        assertEquals(2, outputCompaniesByPages.get(1).getId());
        assertEquals(3, outputCompaniesByPages.get(2).getId());
        assertEquals(4, outputCompaniesByPages.get(3).getId());
        assertEquals(5, outputCompaniesByPages.get(4).getId());
    }

    @Test
    public void should_add_company_when_post_given_company_details() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list1));
        companies.add(new Company(4, "PRIME", employees_list1));

        given(companyRepository.getAllCompanies()).willReturn(companies);

        //when
        Company company = new Company("CERN",employees_list1);
        companyService.addNewCompanyService(company);


        //then
        assertEquals(5,companies.size());
        assertEquals(4, companies.get(3).getId());

    }

    @Test
    public void should_update_company_when_put_given_company_details() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Employee> employees_list2 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        employees_list2.add(new Employee(1, "Ling Yao", 20, "male", 1000));
        employees_list2.add(new Employee(2, "Lan Fan", 18, "female", 850));
        employees_list2.add(new Employee(3, "Mei Chang", 16, "female", 900));

        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list1));

        given(companyRepository.getAllCompanies()).willReturn(companies);

        //when
        Company companyToBeUpdated = new Company(2, "Xerxes II", employees_list2);
        companyService.updateCompanyService(2, companyToBeUpdated);


        //then
        assertEquals(companyService.findCompanyByIDService(2).getName(), companyToBeUpdated.getName());
        assertEquals(companyService.findCompanyByIDService(2).getEmployees(), companyToBeUpdated.getEmployees());

    }

    @Test
    public void should_delete_company_when_post_given_company_id() {
        //given
        List<Employee> employees_list1 = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        employees_list1.add(new Employee(1, "Edward Elric", 20, "male", 1000));
        employees_list1.add(new Employee(2, "Alfonse Elric", 18, "male", 600));
        employees_list1.add(new Employee(3, "Winry Rockbell", 20, "female", 900));

        companies.add(new Company(1, "Amestris", employees_list1));
        companies.add(new Company(2, "Xerxes", employees_list1));
        companies.add(new Company(3, "Xing", employees_list1));

        given(companyRepository.getAllCompanies()).willReturn(companies);

        //when
        companyService.deleteCompanyByIDService(2);

        //then
        assertEquals(companyService.getAllCompaniesService().size(), companies.size());
        assertNull(companyService.findCompanyByIDService(2));
    }
}
