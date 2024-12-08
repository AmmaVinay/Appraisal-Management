package com.team4.appraisalApp.repository.impl;

import com.team4.appraisalApp.models.Employee;
import com.team4.appraisalApp.repository.IEmployeeRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The EmployeeRepoImpl class implements the IEmployeeRepo interface.
 * It provides the data access methods for employees, including retrieving, adding, updating, and deleting employees.
 */
@Repository
public class EmployeeRepoImpl implements IEmployeeRepo {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs an EmployeeRepoImpl with the specified JdbcTemplate.
     * @param jdbcTemplate the JdbcTemplate for database access.
     */
    public EmployeeRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a RowMapper for mapping database rows to Employee objects.
     * @return a RowMapper for Employee objects.
     */
    private RowMapper<Employee> getEmployeeRowMapper() {
        try {
            return (rs, row) -> {
                return new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("emp_name"),
                        rs.getInt("emp_review"),
                        rs.getString("emp_band"),
                        rs.getDouble("emp_salary")
                );
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an employee by employee ID.
     * @param empId the employee ID.
     * @return an Optional containing the employee if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Employee> getEmployeeById(int empId) {
        String sql = "SELECT * FROM public.employee WHERE emp_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getEmployeeRowMapper(), empId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all employees.
     * @return a list of all employees.
     */
    @Override
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM public.employee";
        try {
            return jdbcTemplate.query(sql, getEmployeeRowMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Deletes an employee by employee ID.
     * @param empId the employee ID.
     * @return the number of rows affected by the delete operation.
     */
    @Override
    public int deleteEmployeeById(int empId) {
        String sql = "DELETE FROM public.employee WHERE emp_id = ?";
        try {
            return jdbcTemplate.update(sql, empId);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates an existing employee in the database.
     * This method updates the details of an employee, such as name, review, band,
     * and salary, based on the provided employee object. The update is performed
     * using the employee's ID as the identifier.
     *
     * @param employee the {@link Employee} object containing the updated employee details.
     * @return the number of rows affected by the update operation.
     *         Typically, 1 if the update is successful and 0 if no matching employee was found.
     * @throws RuntimeException if a database access error occurs.
     */
    @Override
    public int updateEmployee(Employee employee) {
        int empId = employee.getEmpId();
        String sql = "UPDATE public.employee SET emp_id=?, emp_name=?, emp_review=?, emp_band=?, emp_salary=? WHERE emp_id=?";
        try {
            return jdbcTemplate.update(sql, employee.getEmpId(), employee.getEmpName(), employee.getReview(), employee.getBand(), employee.getSalary(), empId);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Adds a new employee.
     * @param employee the employee to be added.
     * @return the number of rows affected by the insert operation.
     */
    public int addEmployee(Employee employee) {
        String sql = "INSERT INTO public.employee(emp_id, emp_name, emp_review, emp_band, emp_salary) VALUES (?, ?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, employee.getEmpId(), employee.getEmpName(), employee.getReview(), employee.getBand(), employee.getSalary());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
