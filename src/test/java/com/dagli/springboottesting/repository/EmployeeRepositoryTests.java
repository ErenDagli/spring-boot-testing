package com.dagli.springboottesting.repository;

import com.dagli.springboottesting.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("eren")
                .lastName("dagli")
                .email("eren.dagli@gmail.com")
                .build();
    }

    // JUnit test for save employee operation
    @Test
    @DisplayName("JUnit test for save employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit test for get all employees operation
    @DisplayName("Junit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {

        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("ahmet")
                .lastName("dagli")
                .email("ahmet.dagli@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    // Junit test for get employee by id operation
    @DisplayName("Junit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        System.out.println(employeeDB.getId());
        System.out.println(employeeDB.getFirstName());
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for get employee by email operation
    @DisplayName("Junit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        System.out.println(employeeDB.getId());
        System.out.println(employeeDB.getFirstName());
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for update employee operation
    @DisplayName("Junit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setFirstName("ahmet");
        employeeRepository.save(employeeDB);
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        System.out.println(updatedEmployee.getId());
        System.out.println(updatedEmployee.getFirstName());
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFirstName()).isEqualTo("ahmet");
    }

    // Junit test for delete employee operation
    @DisplayName("Junit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }


    // Junit test for  custom query using JPQL with index
    @Test
    @DisplayName("Junit test for  custom query using JPQL with index")
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("eren.dagli@gmail.com");
    }

    // Junit test for  custom query using JPQL with named params
    @Test
    @DisplayName("Junit test for  custom query using JPQL with named params")
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("eren.dagli@gmail.com");
    }


    // Junit test for  custom query using Native SQL with index
    @Test
    @DisplayName("Junit test for  custom query using Native SQL with index")
    public void givenFirstNameAndLastName_whenFindByNativeSql_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSql(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("eren.dagli@gmail.com");
    }

    // Junit test for  custom query using Native SQL with named params
    @Test
    @DisplayName("Junit test for  custom query using Native SQL with named params")
    public void givenFirstNameAndLastName_whenFindByNativeSqlNamedParams_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSqlNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("eren.dagli@gmail.com");
    }

    // Junit test for custom query using method name
    @Test
    @DisplayName("Junit test for  custom query using Native SQL with named params")
    public void givenFirstNameAndLastName_whenFindByFirstNameAndLastName_thenReturnEmployeeObject() {

        // given - precondition or setup
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByFirstNameAndLastName(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("eren.dagli@gmail.com");
    }
}
