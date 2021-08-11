package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeesRepository;
import org.junit.jupiter.api.Assertions;
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
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeesRepository employeesRepository;

    @Test
    public void should_return_all_employees_when_getAllEmployees_given_all_employees() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "tom", 20, "female", 1000));
        employees.add(new Employee(2, "jerry", 21, "male", 900));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        List<Employee> actualEmployee = employeeService.getAllEmployeesService();

        //then
        assertIterableEquals(employees, actualEmployee);

    }

    @Test
    public void should_return_employee_when_find_employee_by_id_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "tom", 20, "female", 1000));
        employees.add(new Employee(2, "jerry", 21, "male", 900));
        employees.add(new Employee(3, "hayley", 20, "female", 12345));
        employees.add(new Employee(4, "josh", 21, "male", 3215));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        Employee actualEmployee = employeeService.findByEmployeeIDService(3);

        //then
        assertEquals(employees.get(2), actualEmployee);

    }

    @Test
    public void should_return_employee_list_with_pagination_when_getEmployeesByPage_given_page_and_page_size() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "tom", 20, "female", 1000));
        employees.add(new Employee(2, "jerry", 21, "male", 900));
        employees.add(new Employee(3, "Gerarld", 23, "male", 1100));
        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
        employees.add(new Employee(5, "Falcon", 21, "male", 2000));
        employees.add(new Employee(6, "tomB", 20, "female", 1000));
        employees.add(new Employee(7, "jerryB", 21, "male", 900));
        employees.add(new Employee(8, "GerarldB", 23, "male", 1100));
        employees.add(new Employee(9, "LorenzB", 21, "male", 800));
        employees.add(new Employee(10, "FalconB", 21, "male", 2000));
        given(employeesRepository.getEmployees()).willReturn(employees);


        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(4, "Lorenz", 21, "male", 800));
        employeeList.add(new Employee(5, "Falcon", 21, "male", 2000));
        employeeList.add(new Employee(6, "tomB", 20, "female", 1000));
        //when
        List<Employee> actualEmployees = employeeService.getEmployeesByPageService(2,3);

        //then
        assertEquals(3, actualEmployees.size());
        assertEquals(4, actualEmployees.get(0).getId());
        assertEquals(5, actualEmployees.get(1).getId());
        assertEquals(6, actualEmployees.get(2).getId());

    }

    @Test
    public void should_return_employees_when_find_employee_by_gender_given_employee_gender() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
        employees.add(new Employee(5, "Falcon", 21, "male", 2000));
        employees.add(new Employee(6, "tomB", 20, "female", 1000));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        List<Employee> actualEmployees = employeeService.getEmployeeByGenderService("female");

        //then
        assertEquals(employees.get(2).getGender(), actualEmployees.get(0).getGender());

    }

    @Test
    public void should_add_employee_when_post_given_employee_details() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(4, "Lorenz", 21, "male", 800));
        employees.add(new Employee(5, "Falcon", 21, "male", 2000));
        employees.add(new Employee(6, "tomB", 20, "female", 1000));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        Employee employee = new Employee(0, "Edward", 20, "male", 14567);
        employeeService.addNewEmployeeService(employee);
        //then
        assertEquals(employees.size(), 4);
        assertEquals(employees.get(3).getId(), 7);

    }

    @Test
    public void should_update_employee_when_put_given_employee_details() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(8, "Winry", 23, "female", 1100));
        employees.add(new Employee(9, "Edward", 23, "male", 1599));
        employees.add(new Employee(10, "Alfonse", 21, "male", 2000));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        Employee employeeToBeUpdated = new Employee(9, "Edward Elric", 20, "male", 99999);
        employeeService.updateEmployeeService(9,employeeToBeUpdated);
        //then
        assertEquals(employeeService.findByEmployeeIDService(9).getSalary(), employeeToBeUpdated.getSalary());
        assertEquals(employeeService.findByEmployeeIDService(9).getAge(), employeeToBeUpdated.getAge());
        assertEquals(employeeService.findByEmployeeIDService(9).getName(), employeeToBeUpdated.getName());

    }

    @Test
    public void should_delete_employee_when_delete_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(8, "Wrath", 55, "male", 1100));
        employees.add(new Employee(9, "Edward Elric", 23, "male", 1599));
        employees.add(new Employee(10, "Alfonse Elric", 21, "male", 2000));
        given(employeesRepository.getEmployees()).willReturn(employees);
        //when
        employeeService.deleteEmployeeService(8);
        //then
        assertNull(employeeService.findByEmployeeIDService(8));

    }

}