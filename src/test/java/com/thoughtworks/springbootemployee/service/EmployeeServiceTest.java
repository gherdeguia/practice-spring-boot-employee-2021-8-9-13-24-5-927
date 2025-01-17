package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import com.thoughtworks.springbootemployee.repository.Old_EmployeesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private Old_EmployeesRepository old_employeesRepository;
    @Mock
    private EmployeesRepository employeesRepository;


    @Test
    public void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        //given
        List<Employee> employees = employeesDataFactory();
        given(employeesRepository.findAll()).willReturn(employees);
        //when
        List<Employee> actualEmployee = employeeService.getAllEmployeesService();
        //then
        assertIterableEquals(employees, actualEmployee);
    }

    @Test
    public void should_return_employee_when_find_employee_by_id_given_employee_id() {
        //given
        Employee employee = new Employee(1, "tom", 20, "female", 1000);
        //when
        given(employeesRepository.findById(anyInt())).willReturn(java.util.Optional.of(employee));
        Employee actualEmployee = employeeService.findByEmployeeIDService(33);

        //then
        assertEquals(employee.getName(), actualEmployee.getName());

    }

//    @Test
//    public void should_return_employee_list_with_pagination_when_getEmployeesByPage_given_page_and_page_size() {
//        //given
//        List<Employee> employees = new ArrayList<>();
//        employees.add(new Employee(1, "tom", 20, "female", 1000));
//        employees.add(new Employee(2, "jerry", 21, "male", 900));
//        employees.add(new Employee(3, "Gerarld", 23, "male", 1100));
//        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
//        employees.add(new Employee(5, "Falcon", 21, "male", 2000));
//        employees.add(new Employee(6, "tomB", 20, "female", 1000));
//        employees.add(new Employee(7, "jerryB", 21, "male", 900));
//        employees.add(new Employee(8, "GerarldB", 23, "male", 1100));
//        employees.add(new Employee(9, "LorenzB", 21, "male", 800));
//        employees.add(new Employee(10, "FalconB", 21, "male", 2000));
//        given(employeesRepository.findAll(PageRequest.of(2,3))).willReturn((Page<Employee>) employees);
//        //when
//        List<Employee> actualEmployees = employeeService.getEmployeesByPageService(2,3);
//
//        //then
////        assertEquals(3, actualEmployees.size());
//        assertEquals(4, actualEmployees.get(0).getId());
//        assertEquals(5, actualEmployees.get(1).getId());
//        assertEquals(6, actualEmployees.get(2).getId());
//
//    }

    @Test
    public void should_return_employees_when_find_employee_by_gender_given_employee_gender() {
        //given
        List<Employee> employees = employeesDataFactory();
        given(employeesRepository.findAllByGender("female")).willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.getEmployeeByGenderService("female");

        //then
        assertEquals("female", actualEmployees.get(0).getGender());

    }

    @Test
    public void should_add_employee_when_post_given_employee_details() {
        //given
        Employee savedEmployee = new Employee("Edward", 20, "male", 14567);
        //when
        given(employeesRepository.save(savedEmployee)).willReturn(savedEmployee);
        Employee actualEmployee = employeeService.addNewEmployeeService(savedEmployee);
        //then
        assertEquals("Edward", actualEmployee.getName());
        assertEquals(20, actualEmployee.getAge());


    }

//    @Test
//    public void should_update_employee_when_put_given_employee_details() {
//        //given
//        List<Employee> employees = new ArrayList<>();
//
//        employees.add(new Employee(8, "Winry", 23, "female", 1100));
//        employees.add(new Employee(9, "Edward", 23, "male", 1599));
//        employees.add(new Employee(10, "Alfonse", 21, "male", 2000));
//        given(employeesRepository.findAll()).willReturn(employees);
//        //when
//        Employee employeeToBeUpdated = new Employee(9, "Edward Elric", 20, "male", 99999);
//        employeeService.updateEmployeeService(9,employeeToBeUpdated);
//        //then
//        assertEquals(employeeService.findByEmployeeIDService(9).getSalary(), employeeToBeUpdated.getSalary());
//        assertEquals(employeeService.findByEmployeeIDService(9).getAge(), employeeToBeUpdated.getAge());
//        assertEquals(employeeService.findByEmployeeIDService(9).getName(), employeeToBeUpdated.getName());
//
//    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(8, "Wrath", 55, "male", 1100));
        employees.add(new Employee(9, "Edward Elric", 23, "male", 1599));
        employees.add(new Employee(10, "Alfonse Elric", 21, "male", 2000));
        //given(employeesRepository.findAll()).willReturn(employees);
        //when
        employeeService.deleteEmployeeService(8);
        //then
        assertNull(employeeService.findByEmployeeIDService(8));

    }

    private List<Employee> employeesDataFactory() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Lan Fan", 24, "female", 99));
        employees.add(new Employee("Eric", 22, "male", 99));
        employees.add(new Employee("Spongebob", 24, "male", 99));
        employees.add(new Employee("Patrick", 22, "male", 99));
        employees.add(new Employee("Gary", 24, "male", 99));
        employees.add(new Employee("Squidward", 22, "male", 99));
        employees.add(new Employee( "Sandy", 22, "female", 99));
        employees.add(new Employee( "Winry", 22, "female", 99));
        return employees;
    }

}