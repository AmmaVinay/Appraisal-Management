package com.team4.appraisalApp.controller;

import com.team4.appraisalApp.models.Appraisal;
import com.team4.appraisalApp.models.Band;
import com.team4.appraisalApp.models.Employee;
import com.team4.appraisalApp.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The EventController class handles HTTP requests related to appraisals, employees, bands, and reviews.
 */
@RestController
@RequestMapping("/team4")
public class EventController {
    private final IAppraisalRepo appraisalRepo;
    private final IEmployeeRepo employeeRepo;
    private final IBandRepo bandRepo;
    private final IReviewRepo reviewRepo;

    /**
     * Constructs an EventController with the specified repositories.
     *
     * @param appraisalRepo the appraisal repository.
     * @param employeeRepo  the employee repository.
     * @param bandRepo      the band repository.
     * @param reviewRepo    the review repository.
     */
    public EventController(IAppraisalRepo appraisalRepo, IEmployeeRepo employeeRepo, IBandRepo bandRepo, IReviewRepo reviewRepo) {
        this.appraisalRepo = appraisalRepo;
        this.employeeRepo = employeeRepo;
        this.bandRepo = bandRepo;
        this.reviewRepo = reviewRepo;
    }

    /**
     * Validates the provided band ID and review ID to ensure they exist in the system.
     * This method checks if the provided band ID exists within the list of available bands and if the provided review ID
     * exists within the list of available reviews. If either the band ID or review ID is invalid, an error message is returned
     * within a ResponseEntity. If both IDs are valid, it returns null.
     *
     * @param bandId   the band ID to validate. This should be a non-null string representing the band.
     * @param reviewId the review ID to validate. This should be an integer representing the review.
     * @return a ResponseEntity containing:
     * <ul>
     *     <li>HTTP 400 (BAD REQUEST) with an error message if either the band ID or review ID is invalid.</li>
     *     <li>null if both the band ID and review ID are valid.</li>
     * </ul>
     */
    private ResponseEntity<String> validateBandAndReview(String bandId, int reviewId) {
        // Validate Band ID
        boolean validBand = bandRepo.getAllBands().stream()
                .anyMatch(band -> band.getBandId().equals(bandId));
        if (!validBand) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid band ID");
        }

        // Validate Review ID
        boolean validReview = reviewRepo.getAllReviews().stream()
                .anyMatch(review -> review.getRevId() == reviewId);
        if (!validReview) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid review ID");
        }

        return null;
    }


// *********************************************************************************
// **************************  Review table APIs **********************************
// *********************************************************************************

    /**
     * Retrieves all reviews from the system.
     * This method fetches the list of all reviews from the review repository. If the list is not empty,
     * it returns the list of reviews with an HTTP 200 (OK) status. If the list is empty, it returns an HTTP 204
     * (NO CONTENT) status along with a message indicating the list is empty.
     *
     * @return a ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the list of reviews if reviews are available.</li>
     *     <li>HTTP 204 (NO CONTENT) with a message "Empty review list" if there are no reviews.</li>
     * </ul>
     */
    @GetMapping("/review")
    public ResponseEntity<?> getAllReviews() {
        List<?> reviewList = reviewRepo.getAllReviews();
        if (!reviewList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(reviewList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty review list");
    }

// *********************************************************************************
// **************************  Employee table APIs ********************************
// *********************************************************************************

    /**
     * Retrieves an employee by their unique ID.
     * This method attempts to fetch an employee from the system by their ID. If the employee is found,
     * it returns the employee data with an HTTP 200 (OK) status. If the employee is not found,
     * it returns an HTTP 404 (NOT FOUND) status with a message indicating the employee is not found.
     *
     * @param id the unique ID of the employee to retrieve.
     * @return a ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the employee data if the employee is found.</li>
     *     <li>HTTP 404 (NOT FOUND) with a message "Employee not found" if the employee does not exist.</li>
     * </ul>
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id) {
        Optional<Employee> employee = employeeRepo.getEmployeeById(id);
        if (employee.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    }


    /**
     * Retrieves all employees from the system.
     * This method fetches the list of all employees in the system. If there are employees, it returns
     * the list with an HTTP 200 (OK) status. If the list is empty, it returns an HTTP 204 (NO CONTENT) status
     * with a message indicating that the employee list is empty.
     *
     * @return a ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the list of employees if the list is not empty.</li>
     *     <li>HTTP 204 (NO CONTENT) with a message "Empty employee list" if no employees are found.</li>
     * </ul>
     */
    @GetMapping("/employee")
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeRepo.getAllEmployees();
        if (!employeeList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(employeeList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty employee list");
    }


    /**
     * Updates an existing employee by their ID.
     * <p>
     * This method allows for updating an existing employee's details. The employee is identified by their ID
     * passed in the URL path. If the employee is found, the updated information is saved. If the update fails,
     * a message indicating the failure is returned. If the employee does not exist, a message indicating the
     * employee was not found is returned.
     *
     * @param id       The ID of the employee to update.
     * @param employee The employee object containing the updated information.
     * @return a ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the updated employee if the update is successful.</li>
     *     <li>HTTP 404 (NOT FOUND) if the employee with the given ID does not exist.</li>
     *     <li>HTTP 304 (NOT MODIFIED) if the update operation fails.</li>
     * </ul>
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
        Optional<Employee> employeeOptional = employeeRepo.getEmployeeById(id);
        if (employeeOptional.isPresent()) {
            int update = employeeRepo.updateEmployee(employee);
            if (update == 0) return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Update failed");
            return ResponseEntity.status(HttpStatus.OK).body(employee);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    }


    /**
     * Deletes an employee by ID from the system.
     * <p>
     * This method checks if the employee exists using the provided employee ID. If the employee is found,
     * it attempts to delete the employee. If the delete operation is successful, it returns the deleted
     * employee's information along with HTTP status 200 (OK). If the deletion fails (e.g., no rows were affected),
     * it returns an HTTP status 304 (Not Modified) with an error message. If the employee is not found,
     * it returns HTTP status 404 (Not Found) with an appropriate message.
     * <p>
     * Additionally, if the employee has an associated appraisal, it will attempt to delete the appraisal.
     * If the appraisal deletion is successful, the employee is deleted, otherwise, it returns a failure response.
     *
     * @param id The ID of the employee to delete.
     * @return A ResponseEntity containing:
     * <ul>
     *     <li>HTTP status 200 (OK) with the deleted employee if the operation is successful.</li>
     *     <li>HTTP status 304 (Not Modified) with a message if the delete operation fails.</li>
     *     <li>HTTP status 404 (Not Found) with a message if the employee with the given ID does not exist.</li>
     * </ul>
     * @throws IllegalArgumentException If the employee ID is invalid or null (if applicable).
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
        Optional<Employee> employeeOptional = employeeRepo.getEmployeeById(id);
        if (employeeOptional.isPresent()) {
            int delete = employeeRepo.deleteEmployeeById(id);
            if (delete == 0) return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Delete failed");
            else {
                Optional<Appraisal> appraisalOptional = appraisalRepo.getAppraisal(id);
                if (appraisalOptional.isPresent()) {
                    int deleteAppraisal = appraisalRepo.deleteAppraisal(id);
                    if (deleteAppraisal == 0)
                        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Delete failed");
                }
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "Employee deleted successfully", employeeOptional.get()
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
    }


    /**
     * Adds a new employee to the system.
     * This method checks if the employee already exists in the system. If the employee exists,
     * it returns a response with HTTP status 409 (Conflict) and an appropriate message.
     * If the employee does not exist, the method performs validation on the employee's band and review.
     * If validation fails, an error response is returned. If all checks pass, the employee is added to the system.
     * The method then returns a response indicating whether the employee was successfully created.
     *
     * @param employee The employee to be added.
     * @return ResponseEntity containing:
     * <ul>
     *     <li>HTTP 409 (Conflict) with a message if the employee already exists.</li>
     *     <li>HTTP 400 (Bad Request) with validation error message if validation fails.</li>
     *     <li>HTTP 201 (Created) with the newly created employee if successful.</li>
     *     <li>HTTP 304 (Not Modified) with a message if employee creation fails.</li>
     * </ul>
     */
    @PostMapping("/employee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        // Check if the employee already exists
        if (employeeRepo.getEmployeeById(employee.getEmpId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Employee already exists");
        }
        // Validation
        ResponseEntity<String> validationResponse = validateBandAndReview(employee.getBand(), employee.getReview());
        if (validationResponse != null) {
            return validationResponse;
        }
        // Add the employee if all validations pass
        int addEmp = employeeRepo.addEmployee(employee);
        if (addEmp == 0) return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Employee creation failed");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Employee successfully added", employee));
    }


// *********************************************************************************
// **************************  Band table APIss ************************************
// *********************************************************************************

    /**
     * Retrieves all bands from the database.
     * This method fetches the list of all bands from the repository. If the list is non-empty,
     * it returns the list with a status of HTTP 200 (OK). If the list is empty, it returns a status of
     * HTTP 204 (No Content) with an appropriate message indicating that the list is empty.
     *
     * @return ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the list of bands if the list is non-empty.</li>
     *     <li>HTTP 204 (No Content) with a message "Empty band list" if the list is empty.</li>
     * </ul>
     */
    @GetMapping("/band")
    public ResponseEntity<?> getAllBands() {
        List<Band> bandList = bandRepo.getAllBands();
        if (!bandList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(bandList);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty band list");
    }


// **********************************************************************************
// **************************  Appraisal table APIs ********************************
// **********************************************************************************


    /**
     * Retrieves an appraisal by its ID.
     * <p>
     * This method fetches an appraisal from the database using the provided appraisal ID. If the appraisal is found,
     * it returns the appraisal with a status of HTTP 200 (OK). If the appraisal is not found, it returns a status of
     * HTTP 404 (Not Found) with a message indicating that the appraisal was not found.
     *
     * @param id The ID of the appraisal to retrieve.
     * @return ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the appraisal if found.</li>
     *     <li>HTTP 404 (Not Found) with a message "Appraisal not found" if the appraisal does not exist.</li>
     * </ul>
     */
    @GetMapping("/appraisal/{id}")
    public ResponseEntity<?> getAppraisalById(@PathVariable int id) {
        Optional<Appraisal> appraisalOptional = appraisalRepo.getAppraisal(id);
        if (appraisalOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(appraisalOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appraisal not found");
    }

    /**
     * Retrieves all appraisals from the database.
     * This method fetches the list of all appraisals from the repository. If the list is non-empty,
     * it returns the list with a status of HTTP 200 (OK). If the list is empty, it returns a status of
     * HTTP 204 (No Content) with an appropriate message.
     *
     * @return ResponseEntity containing:
     * <ul>
     *     <li>HTTP 200 (OK) with the list of appraisals if the list is non-empty.</li>
     *     <li>HTTP 204 (No Content) with a message "Empty appraisal list" if the list is empty.</li>
     * </ul>
     */
    @GetMapping("/appraisal")
    public ResponseEntity<?> getAllAppraisals() {
        List<Appraisal> appraisalList = appraisalRepo.getAllAppraisals();
        if (!appraisalList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(appraisalList);

        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Empty appraisal list");

    }

    /**
     * Adds a new appraisal for an employee.
     * This endpoint checks if an appraisal already exists for the employee, validates the employee's band and review,
     * and then creates the appraisal if all validations pass.
     *
     * @param employee The {@link Employee} object containing the details of the employee for whom the appraisal is to be added.
     * @return a {@link ResponseEntity} representing the outcome of the add operation:
     * <ul>
     *     <li>HTTP 409 (CONFLICT): If an appraisal already exists for the given employee ID.</li>
     *     <li>HTTP 400 (BAD REQUEST): If validation of band or review fails.</li>
     *     <li>HTTP 304 (NOT MODIFIED): If the appraisal creation fails.</li>
     *     <li>HTTP 201 (CREATED): If the appraisal is successfully created.</li>
     * </ul>
     */
    @PostMapping("/appraisal")
    public ResponseEntity<?> addAppraisal(@RequestBody Employee employee) {
        if (appraisalRepo.getAppraisal(employee.getEmpId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Appraisal already exists");
        }
        ResponseEntity<String> validationResponse = validateBandAndReview(employee.getBand(), employee.getReview());
        if (validationResponse != null) {
            return validationResponse;
        }
        int addApp = appraisalRepo.createAppraisal(employee);
        if (addApp == 0) return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Appraisal creation failed");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Appraisal successfully created for ", employee));
    }

    /**
     * Updates an appraisal record for a given employee.
     * <p>
     * This endpoint validates the existence of the appraisal and the employee's band and review details
     * before updating the appraisal information in the database. If any validation fails, or if the update
     * operation is unsuccessful, appropriate HTTP status codes and error messages are returned.
     *
     * @param employee the {@link Employee} object containing the details of the employee to update.
     *                 It includes the employee's ID, band, review, and other related information.
     * @return a {@link ResponseEntity} representing the outcome of the update operation:
     * <ul>
     *     <li>HTTP 404 (NOT FOUND): If the appraisal does not exist for the given employee ID.</li>
     *     <li>HTTP 400 (BAD REQUEST): If validation of band or review fails.</li>
     *     <li>HTTP 304 (NOT MODIFIED): If the update operation does not affect any records.</li>
     *     <li>HTTP 200 (OK): If the appraisal is successfully updated.</li>
     *     <li>HTTP 500 (INTERNAL SERVER ERROR): If an unexpected error occurs.</li>
     * </ul>
     */
    @PutMapping("/appraisal")
    public ResponseEntity<?> updateAppraisal(@RequestBody Employee employee) {
        if (!appraisalRepo.getAppraisal(employee.getEmpId()).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appraisal does not exists");
        }
        ResponseEntity<String> validationResponse = validateBandAndReview(employee.getBand(), employee.getReview());
        if (validationResponse != null) {
            return validationResponse;
        }

        int update = appraisalRepo.updateAppraisal(employee);
        if (update == 0) return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Update failed");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Appraisal update successful for ", employee));
    }


}
