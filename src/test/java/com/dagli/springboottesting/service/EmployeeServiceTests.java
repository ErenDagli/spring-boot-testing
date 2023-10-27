package com.dagli.springboottesting.service;

import com.dagli.springboottesting.entity.Employee;
import com.dagli.springboottesting.exception.ResourceNotFoundException;
import com.dagli.springboottesting.repository.EmployeeRepository;
import com.dagli.springboottesting.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        //employeeRepository = Mockito.mock(EmployeeRepository.class);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .firstName("eren")
                .lastName("dagli")
                .email("eren.dagli@gmail.com")
                .build();
    }


    // JUnit test for saveEmployee method
    @Test
    @DisplayName("JUnit test for saveEmployee method")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saveEmployee method
    @Test
    @DisplayName("JUnit test for saveEmployee method whitch throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going test
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }


    // JUnit test for getAllEmployees method
    @Test
    @DisplayName("JUnit test for getAllEmployees method")
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("ahmet")
                .lastName("dagli")
                .email("ahmet.dagli@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for getAllEmployees method
    @Test
    @DisplayName("JUnit test for getAllEmployees method (negative scenario)")
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }


    // JUnit test for getEmployeeById method
    @Test
    @DisplayName("JUnit test for getEmployeeById method")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going test
        Optional<Employee> savedEmployee = employeeService.getEmployeeById(employee.getId());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for update Employee method
    @Test
    @DisplayName("JUnit test for update Employee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("ahmet");

        // when - action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(employee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("ahmet");
    }


    // JUnit test for deleteEmployee method
    @Test
    @DisplayName("JUnit test for deleteEmployee method")
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        // given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when - action or the behaviour that we are going test
        employeeService.deleteEmployee(employee.getId());

        // then - verify the output
        verify(employeeRepository,times(1)).deleteById(employee.getId());
    }
}
