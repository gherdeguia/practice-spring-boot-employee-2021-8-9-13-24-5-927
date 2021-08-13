package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeesRepository employeesRepository;
    @Autowired
    EmployeesRepository employeesRepository2;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    void should_return_employees_when_get_given_company_id() throws Exception {
        //given
        Company company1 = new Company(1, "Spring",null);
        Company company2 = new Company(2, "Boot",null);
        companyRepository.save(company1);
        companyRepository.save(company2);
        employeesRepository.save(new Employee( "Winry", 20, "female", 1000,2));
        employeesRepository.save(new Employee( "Lan Fan", 21, "female", 900, 2));
        employeesRepository2.save(new Employee( "Alfonse", 21, "male", 800,1));
        employeesRepository2.save(new Employee( "Edward", 21, "male", 2000,1));

        //when
        //then
        int companyID = company1.getId();
        List<Employee> employeeList = company1.getEmployees();
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}",companyID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyName").value("Spring"))
        ;
    }
}
