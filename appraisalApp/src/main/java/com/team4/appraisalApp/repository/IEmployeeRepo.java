package com.team4.appraisalApp.repository;

import com.team4.appraisalApp.models.Employee;

import java.util.List;
import java.util.Optional;

/**
 * The IEmployeeRepo interface defines the methods for interacting with employees.
 */
public interface IEmployeeRepo {

    /**
     * Retrieves an employee by employee ID.
     * @param empId the employee ID.
     * @return an Optional containing the employee if found, otherwise an empty Optional.
     */
    public Optional<Employee> getEmployeeById(int empId);

    /**
     * Retrieves all employees.
     * @return a list of all employees.
     */
    public List<Employee> getAllEmployees();

    /**
     * Deletes an employee by employee ID.
     * @param empId the employee ID.
     * @return the number of rows affected by the delete operation.
     */
    public int deleteEmployeeById(int empId);

    /**
     * Updates an existing employee.
     * @param employee the employee to be updated.
     * @return the number of rows affected by the update operation.
     */
    public int updateEmployee(Employee employee);

    /**
     * Adds a new employee.
     * @param employee the employee to be added.
     * @return the number of rows affected by the insert operation.
     */
    public int addEmployee(Employee employee);

}
