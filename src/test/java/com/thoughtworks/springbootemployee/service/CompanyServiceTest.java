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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
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

        assertEquals(outputCompany, companies.get(0));
        assertEquals(outputCompany.getId(), companies.get(0).getId());
        assertEquals(outputCompany.getName(), companies.get(0).getName());
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
        List<Employee> outputCompanyEmployees = companyService.getCompanyEmployeesListByID(1);

        //then

        assertEquals(outputCompanyEmployees, employees_list2);
    }
}
