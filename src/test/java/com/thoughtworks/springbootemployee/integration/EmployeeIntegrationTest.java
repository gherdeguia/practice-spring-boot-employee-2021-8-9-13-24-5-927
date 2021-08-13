package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
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

    @AfterEach
    void tearDown(){
        employeesRepository.deleteAll();
    }

    //todo @BeforeEach

    @Test
    void should_return_all_employee_when_get_given_employees() throws Exception {
        //given
        final Employee employee = new Employee(1,"GG",20,"male",2021);
        final Employee employee1 = new Employee(2,"GGWP",22,"male",2221);
        employeesRepository.save(employee);
        employeesRepository.save(employee1); //todo saveAll
        //when
        //then
            mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                    .andExpect(status().isOk())

                    .andExpect(jsonPath("$[0].name").value("GG"))
                    .andExpect(jsonPath("$[0].age").value(20))
                    .andExpect(jsonPath("$[0].gender").value("male"))
                    .andExpect(jsonPath("$[0].salary").value(2021))

                    .andExpect(jsonPath("$[1].name").value("GGWP"))
                    .andExpect(jsonPath("$[1].age").value(22))
                    .andExpect(jsonPath("$[1].gender").value("male"))
                    .andExpect(jsonPath("$[1].salary").value(2221))
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
                .andExpect(jsonPath("$.age").value(20))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(2021))
        ;
    }

    @Test
    void should_add_employee_when_post_given_employee_details() throws Exception {
        final Employee employee = new Employee("GG",20,"male",2021);
        Employee savedEmployee = employeesRepository.save(employee);
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

        int id = savedEmployee.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GGWP"))
                .andExpect(jsonPath("$.age").value(123))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1234))
                ;
    }

    @Test
    void should_update_employee_when_call_update_employee_api() throws Exception {
        //given
        final Employee employee = new Employee("GG",20,"male",2021);
        final Employee employee2 = new Employee("GGGG",20,"male",123456);
        final Employee savedEmployee = employeesRepository.save(employee);
        final Employee savedEmployee2 = employeesRepository.save(employee2);
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
                .andExpect(jsonPath("$.age").value(123))
                .andExpect(jsonPath("$.salary").value(2022))
        ;
    }

    @Test
    void should_return_employees_list_by_gender_when_get_employee_given_employees_and_gender() throws Exception {
        //given
        Employee employee1 = new Employee("GG",20,"male",2021);
        Employee employee2 = new Employee("Edward",19,"male",1223);
        Employee employee3 = new Employee("Winry",18,"female",1123);
        Employee employee4 = new Employee("Lan Fan",18,"female",2123);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        employeesRepository.save(employee4);
        //when
        String gender = "female";
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees/?gender={gender}",gender))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gender").value(gender))
                .andExpect(jsonPath("$[1].gender").value(gender))
        //todo add more details for checking
        ;
    }

    @Test
    void should_return_pagination_when_get_employee_given_page_and_pageSize() throws Exception {
        Employee employee1 = new Employee("GG",20,"male",2021);
        Employee employee2 = new Employee("Edward",19,"male",1223);
        Employee employee3 = new Employee("Winry",18,"female",9999);
        Employee employee4 = new Employee("Extra1",99,"male",9999);
        Employee employee5 = new Employee("Extra2",99,"male",9999);
        Employee employee6 = new Employee("Extra3",99,"female",9999);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        employeesRepository.save(employee4);
        employeesRepository.save(employee5);
        employeesRepository.save(employee6);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees")
                    .param("page", String.valueOf(2))
                    .param("pageSize", String.valueOf(3))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Extra1"))
                .andExpect(jsonPath("$[1].name").value("Extra2"))
                .andExpect(jsonPath("$[2].name").value("Extra3"))
        ;
    }

    @Test
    void should_delete_employee_when_call_delete_given_employee_id() throws Exception {
        //given
        Employee employee1 = new Employee("GG",20,"male",2021);
        Employee employee2 = new Employee("Edward",19,"male",1223);
        Employee employee3 = new Employee("Winry",18,"female",9999);
        employeesRepository.save(employee1);
        employeesRepository.save(employee2);
        employeesRepository.save(employee3);
        //when
        //then
        Integer id = employee2.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",id))
                .andExpect(status().isOk())
        ;

    }
}
