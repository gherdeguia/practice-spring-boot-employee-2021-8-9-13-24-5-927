package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping()
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployeesService();
    }


//
//    @GetMapping(path = "/{employeeID}")
//    public Employee findByEmployeeID(@PathVariable int employeeID){
//        return employees.stream().filter(employee -> employee.getId().equals(employeeID) ).findFirst().orElse( null );
//    }
//
    @GetMapping(params = {"page","pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam("page") Integer page,
                                              @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getEmployeesByPageService(page,pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam("gender") String gender) {

        return employeeService.getEmployeeByGenderService(gender);
    }

    @PostMapping
    public void addNewEmployee(@RequestBody Employee employee){
        employeeService.addNewEmployeeService(employee);
    }

//    @PutMapping(path = "/{employeeID}")
//    public Employee updateEmployee(@PathVariable Integer employeeID,
//                                   @RequestBody Employee employeeToBeUpdate) {
//        Employee employeeToBeUpdated = employees.stream()
//                .filter(employee -> employee.getId().equals(employeeID) )
//                .findFirst()
//                .map(employee -> updateEmployeeInfo(employee,employeeToBeUpdate))
//                .get();
//
//        return employeeToBeUpdated;
//    }
//
//    private Employee updateEmployeeInfo(Employee employee, Employee employeeToBeUpdate) {
//        if (!Objects.isNull(employeeToBeUpdate.getName())) {
//            employee.setName(employeeToBeUpdate.getName());
//        }
//        if (!Objects.isNull(employeeToBeUpdate.getAge())) {
//            employee.setAge(employeeToBeUpdate.getAge());
//        }
//        if (!Objects.isNull(employeeToBeUpdate.getGender())) {
//            employee.setGender(employeeToBeUpdate.getGender());
//        }
//        if (!Objects.isNull(employeeToBeUpdate.getSalary())) {
//            employee.setSalary(employeeToBeUpdate.getSalary());
//        }
//        return employee;
//    }
//
//    @DeleteMapping(path="/{employeeID}")
//    public void deleteEmployeeByID(@PathVariable int employeeID) {
//        Employee employeeToDelete = employees.stream().filter(employee -> employee.getId().equals(employeeID)).findFirst().orElse(null);
//        employees.remove(employeeToDelete);
//    }
}
