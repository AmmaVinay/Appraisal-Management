package com.team4.appraisalApp.repository.impl;

import com.team4.appraisalApp.models.Appraisal;
import com.team4.appraisalApp.models.Band;
import com.team4.appraisalApp.models.Employee;
import com.team4.appraisalApp.models.Review;
import com.team4.appraisalApp.repository.IAppraisalRepo;
import com.team4.appraisalApp.repository.IBandRepo;
import com.team4.appraisalApp.repository.IReviewRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The AppraisalRepoImpl class implements the IAppraisalRepo interface.
 * It provides the data access methods for appraisals, including retrieving and saving appraisals.
 */
@Repository
public class AppraisalRepoImpl implements IAppraisalRepo {

    private final JdbcTemplate jdbcTemplate;
    private final IBandRepo bandRepo;
    private final IReviewRepo reviewRepo;

    /**
     * Constructs an AppraisalRepoImpl with the specified JdbcTemplate, band repository, and review repository.
     *
     * @param jdbcTemplate the JdbcTemplate for database access.
     * @param bandRepo     the band repository.
     * @param reviewRepo   the review repository.
     */
    public AppraisalRepoImpl(JdbcTemplate jdbcTemplate, IBandRepo bandRepo, IReviewRepo reviewRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.bandRepo = bandRepo;
        this.reviewRepo = reviewRepo;
    }

    /**
     * Creates a RowMapper for mapping database rows to Appraisal objects.
     *
     * @return a RowMapper for Appraisal objects.
     */
    private RowMapper<Appraisal> getAppraisalRowMapper() {
        return (rs, rowNum) -> new Appraisal(
                rs.getInt("emp_id"),
                rs.getString("emp_name"),
                rs.getInt("emp_review"),
                rs.getString("emp_band"),
                rs.getDouble("current_salary"),
                rs.getDouble("appraisal_percentage"),
                rs.getDouble("appraised_salary")
        );
    }

    /**
     * Retrieves an appraisal by employee ID.
     *
     * @param empId the employee ID.
     * @return an Optional containing the appraisal if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Appraisal> getAppraisal(int empId) {
        String sql = "SELECT * FROM public.appraisal WHERE emp_id = ?";
        try {
            Appraisal appraisal = jdbcTemplate.queryForObject(sql, getAppraisalRowMapper(), empId);
            return Optional.ofNullable(appraisal);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while fetching appraisal", e);
        }
    }

    /**
     * Retrieves all appraisals.
     *
     * @return a list of all appraisals.
     */
    @Override
    public List<Appraisal> getAllAppraisals() {
        String sql = "SELECT * FROM public.appraisal";
        try {
            return jdbcTemplate.query(sql, getAppraisalRowMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while fetching all appraisals", e);
        }
    }

    /**
     * Creates a new appraisal for an employee.
     *
     * @param employee the employee for whom the appraisal is being created.
     * @return the number of rows affected by the insert operation.
     */
    @Override
    public int createAppraisal(Employee employee) {
        double salary = employee.getSalary();
        int reviewId = employee.getReview();
        String bandId = employee.getBand();

        try {
            double bandMultiplier = bandRepo.getBand(bandId)
                    .map(Band::getBandMul)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Band ID: " + bandId));

            double reviewMultiplier = reviewRepo.getReview(reviewId)
                    .map(Review::getRevMul)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Review ID: " + reviewId));

            double appraisalPercentage = reviewMultiplier * bandMultiplier;
            double appraisedSalary = salary + (salary * appraisalPercentage);
            String sql = "INSERT INTO public.appraisal(emp_id, emp_name, emp_review, emp_band, current_salary, appraisal_percentage, appraised_salary) VALUES (?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, employee.getEmpId(), employee.getEmpName(), reviewId, bandId, salary, appraisalPercentage, appraisedSalary);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Validation error: " + e.getMessage(), e);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while creating appraisal", e);
        }
    }

    /**
     * Updates an employee's appraisal information in the database.
     * This method calculates the appraisal percentage and appraised salary for an employee
     * based on their salary, review multiplier, and band multiplier. It then updates the
     * appraisal details in the database for the specified employee.
     *
     * @param employee the {@link Employee} object containing the employee's details,
     *                 including ID, name, review ID, band ID, and current salary.
     * @return the number of rows affected by the update operation.
     * Typically, 1 if the update is successful and 0 if no matching employee was found.
     * @throws IllegalArgumentException if the provided band ID or review ID is invalid.
     * @throws RuntimeException         if a database access error occurs while performing the update.
     */
    @Override
    public int updateAppraisal(Employee employee) {
        double salary = employee.getSalary();
        int reviewId = employee.getReview();
        String bandId = employee.getBand();
        try {
            double bandMultiplier = bandRepo.getBand(bandId)
                    .map(Band::getBandMul)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Band ID: " + bandId));

            double reviewMultiplier = reviewRepo.getReview(reviewId)
                    .map(Review::getRevMul)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Review ID: " + reviewId));

            double appraisalPercentage = reviewMultiplier * bandMultiplier;
            double appraisedSalary = salary + (salary * appraisalPercentage);
            String sql = "UPDATE public.appraisal SET emp_id=?, emp_name=?, emp_review=?, emp_band=?, current_salary=?, appraisal_percentage=?, appraised_salary=? WHERE emp_id=?";
            int id = employee.getEmpId();
            Appraisal appraisal = new Appraisal(
                    employee.getEmpId(),
                    employee.getEmpName(),
                    employee.getReview(),
                    employee.getBand(),
                    employee.getSalary(),
                    appraisalPercentage,
                    appraisedSalary
            );
            return jdbcTemplate.update(sql,
                    appraisal.getEmpId(),
                    appraisal.getEmpName(),
                    appraisal.getEmpReview(),
                    appraisal.getEmpBand(),
                    appraisal.getCurrentSalary(),
                    appraisal.getAppraisalPercentage(),
                    appraisal.getAppraisedSalary(),
                    id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while updating appraisal", e);
        }
    }

    /**
     * Deletes an appraisal for the specified employee ID from the database.
     * <p>
     * This method attempts to delete an appraisal record from the `appraisal` table for the given employee ID.
     * If the deletion is successful, it returns the number of rows affected. If a database error occurs,
     * it throws a `RuntimeException` with a relevant error message.
     *
     * @param empId The ID of the employee whose appraisal is to be deleted.
     * @return The number of rows affected by the delete operation. A return value of 1 indicates success.
     * @throws RuntimeException If a database error occurs while deleting the appraisal.
     */
    @Override
    public int deleteAppraisal(int empId) {
        try {
            String sql = "DELETE FROM public.appraisal WHERE emp_id=?";
            return jdbcTemplate.update(sql, empId);
        } catch (DataAccessException e) {
            throw new RuntimeException("Database error while deleting appraisal", e);
        }
    }


}
