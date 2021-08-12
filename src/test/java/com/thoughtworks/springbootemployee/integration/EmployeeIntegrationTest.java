package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EmployeesRepository employeesRepository;

    @Test
    void should_return_all_employee_when_get_given_employees() throws Exception {
        //given
        final Employee employee = new Employee(1,"GG",20,"male",2021);
        employeesRepository.save(employee);
        //when
        //then
            mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("GG"))
            ;
    }
}
