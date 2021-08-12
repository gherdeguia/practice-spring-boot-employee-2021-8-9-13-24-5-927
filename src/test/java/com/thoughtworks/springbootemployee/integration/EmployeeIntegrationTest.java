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

import java.util.ArrayList;
import java.util.List;

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
        final Employee employee1 = new Employee(2,"GGWP",22,"male",2221);
        employeesRepository.save(employee);
        employeesRepository.save(employee1);
        //when
        //then
            mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[1].name").value("GGWP"))
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

    @Test
    void should_return_employees_list_by_gender_when_get_employee_given_employees_and_gender() throws Exception {
        //given
        Employee employee1 = new Employee("GG",20,"male",2021);
        Employee employee2 = new Employee("Edward",19,"male",1223);
        Employee employee3 = new Employee("Winry",18,"female",1123);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        //when
        String gender = employee3.getGender();
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/?gender={gender}",gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gender").value(gender))
        ;
    }

    @Test
    void should_return_pagination_when_get_employee_given_page_and_pageSize() throws Exception {
        Employee employee1 = new Employee("GG",20,"male",2021);
        Employee employee2 = new Employee("Edward",19,"male",1223);
        Employee employee3 = new Employee("Winry",18,"female",9999);
        Employee employee4 = new Employee("Greed",99,"male",9999);
        Employee employee5 = new Employee("Sloth",99,"male",9999);
        Employee employee6 = new Employee("Lust",99,"female",9999);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        employeesRepository.save(employee4);
        employeesRepository.save(employee5);
        employeesRepository.save(employee6);
        //when
        String gender = employee3.getGender();
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                    .param("page", String.valueOf(2))
                    .param("pageSize", String.valueOf(3))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Greed"))
                .andExpect(jsonPath("$[1].name").value("Sloth"))
        ;

    }
}
