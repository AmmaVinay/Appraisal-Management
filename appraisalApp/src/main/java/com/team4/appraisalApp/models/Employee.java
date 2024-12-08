package com.team4.appraisalApp.models;

/**
 * The Employee class represents the details of an employee.
 * It includes the employee ID, name, review, band, and salary.
 */
public class Employee {
    // POJO data members
    private int empId;
    private String empName;
    private int review;
    private String band;
    private double salary;

    // Getters and Setters

    /**
     * Gets the employee ID.
     * @return the employee ID.
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * Sets the employee ID.
     * @param empId the employee ID.
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * Gets the employee name.
     * @return the employee name.
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * Sets the employee name.
     * @param empName the employee name.
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * Gets the employee review.
     * @return the employee review.
     */
    public int getReview() {
        return review;
    }

    /**
     * Sets the employee review.
     * @param review the employee review.
     */
    public void setReview(int review) {
        this.review = review;
    }

    /**
     * Gets the employee band.
     * @return the employee band.
     */
    public String getBand() {
        return band;
    }

    /**
     * Sets the employee band.
     * @param band the employee band.
     */
    public void setBand(String band) {
        this.band = band;
    }

    /**
     * Gets the employee salary.
     * @return the employee salary.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the employee salary.
     * @param salary the employee salary.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Constructors

    /**
     * Constructs an Employee object with the specified details.
     * @param empId the employee ID.
     * @param empName the employee name.
     * @param review the employee review.
     * @param band the employee band.
     * @param salary the employee salary.
     */
    public Employee(int empId, String empName, int review, String band, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.review = review;
        this.band = band;
        this.salary = salary;
    }

    /**
     * Default constructor.
     */
    public Employee() {}

    // toString

    /**
     * Returns a string representation of the Employee object.
     * @return a string representation of the Employee object.
     */
    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", review=" + review +
                ", band='" + band + '\'' +
                ", salary=" + salary +
                '}';
    }
}
