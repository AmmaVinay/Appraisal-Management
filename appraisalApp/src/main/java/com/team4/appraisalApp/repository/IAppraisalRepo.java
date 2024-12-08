package com.team4.appraisalApp.repository;

import com.team4.appraisalApp.models.Appraisal;
import com.team4.appraisalApp.models.Employee;

import java.util.List;
import java.util.Optional;

/**
 * The IAppraisalRepo interface defines the methods for interacting with appraisals.
 */
public interface IAppraisalRepo {

    /**
     * Retrieves an appraisal by employee ID.
     * @param empId the employee ID.
     * @return an Optional containing the appraisal if found, otherwise an empty Optional.
     */
    public Optional<Appraisal> getAppraisal(int empId);

    /**
     * Retrieves all appraisals.
     * @return a list of all appraisals.
     */
    public List<Appraisal> getAllAppraisals();

    /**
     * Creates a new appraisal for an employee.
     * @param employee the employee for whom the appraisal is being created.
     * @return the number of rows affected by the insert operation.
     */
    public int createAppraisal(Employee employee);

    /**
     * Updates existing appraisal for an employee
     * @param employee the employee whose data got changed
     * @return the number of rows affected by the update operation
     */
    public int updateAppraisal(Employee employee);

    /**
     * Deletes existing appraisal for an employee
     * @param empId the id of the employee whose data got deleted
     * @return
     */
    public int deleteAppraisal(int empId);
}
