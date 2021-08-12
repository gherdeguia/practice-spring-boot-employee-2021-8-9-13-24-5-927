package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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

    @Test
    void should_return_employee_when_get_given_employee_id() throws Exception {
        //given
        final Employee employee = new Employee(1,"GG",20,"male",2021);
        final Employee savedEmployee = employeesRepository.save(employee);
        int id = savedEmployee.getId();
        //when
        //then

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GG"))
        ;
    }

    @Test
    void should_add_employee_when_post_given_employee_details() throws Exception {
        final Employee employee = new Employee(1,"GG",20,"male",2021);
        employeesRepository.save(employee);
        String employeeToAdd = "{\n" +
                "        \"name\": \"GGWP\",\n" +
                "        \"age\": 123,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 1234\n" +
                "    }";

        mockMvc.perform(MockMvcRequestBuilders.post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeToAdd)
                )
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].name").value("GGWP"));

//                        .andExpect(jsonPath("$.name").value("GG"))
//                .andExpect(jsonPath("$.age").value(123))
//                .andExpect(jsonPath("$.gender").value("male"))
//                .andExpect(jsonPath("$.salary").value(1234))
//                .andExpect(jsonPath("$.companyId").value(1))
    }

    @Test
    void should_update_employee_when_call_update_employee_api() throws Exception {
        //given
        final Employee employee = new Employee("GG",20,"male",2021);
        final Employee savedEmployee = employeesRepository.save(employee);
        String employeeToUpdate = "{\n" +
                "        \"name\": \"GGWP\",\n" +
                "        \"age\": 123,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 2022\n" +
                "    }";
        //when
        //then
        int id = savedEmployee.getId();
        mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeToUpdate)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GGWP"))
        ;
    }
}
