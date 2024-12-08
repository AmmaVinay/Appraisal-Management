package com.team4.appraisalApp.models;

/**
 * The Appraisal class represents the appraisal details of an employee.
 * It includes employee ID, name, review, band, current salary, appraisal percentage, and appraised salary.
 */
public class Appraisal {
    private int empId;
    private String empName;
    private int empReview;
    private String empBand;
    private double currentSalary;
    private double appraisalPercentage;
    private double appraisedSalary;

    // Getter and setter methods

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
    public int getEmpReview() {
        return empReview;
    }

    /**
     * Sets the employee review.
     * @param empReview the employee review.
     */
    public void setEmpReview(int empReview) {
        this.empReview = empReview;
    }

    /**
     * Gets the employee band.
     * @return the employee band.
     */
    public String getEmpBand() {
        return empBand;
    }

    /**
     * Sets the employee band.
     * @param empBand the employee band.
     */
    public void setEmpBand(String empBand) {
        this.empBand = empBand;
    }

    /**
     * Gets the current salary of the employee.
     * @return the current salary of the employee.
     */
    public double getCurrentSalary() {
        return currentSalary;
    }

    /**
     * Sets the current salary of the employee.
     * @param currentSalary the current salary of the employee.
     */
    public void setCurrentSalary(double currentSalary) {
        this.currentSalary = currentSalary;
    }

    /**
     * Gets the appraisal percentage.
     * @return the appraisal percentage.
     */
    public double getAppraisalPercentage() {
        return appraisalPercentage;
    }

    /**
     * Sets the appraisal percentage.
     * @param appraisalPercentage the appraisal percentage.
     */
    public void setAppraisalPercentage(double appraisalPercentage) {
        this.appraisalPercentage = appraisalPercentage;
    }

    /**
     * Gets the appraised salary of the employee.
     * @return the appraised salary of the employee.
     */
    public double getAppraisedSalary() {
        return appraisedSalary;
    }

    /**
     * Sets the appraised salary of the employee.
     * @param appraisedSalary the appraised salary of the employee.
     */
    public void setAppraisedSalary(double appraisedSalary) {
        this.appraisedSalary = appraisedSalary;
    }

    // Constructors

    /**
     * Constructs an Appraisal object with the specified details.
     * @param empId the employee ID.
     * @param empName the employee name.
     * @param empReview the employee review.
     * @param empBand the employee band.
     * @param currentSalary the current salary of the employee.
     * @param appraisalPercentage the appraisal percentage.
     * @param appraisedSalary the appraised salary of the employee.
     */
    public Appraisal(int empId, String empName, int empReview, String empBand, double currentSalary, double appraisalPercentage, double appraisedSalary) {
        this.empId = empId;
        this.empName = empName;
        this.empReview = empReview;
        this.empBand = empBand;
        this.currentSalary = currentSalary;
        this.appraisalPercentage = appraisalPercentage;
        this.appraisedSalary = appraisedSalary;
    }

    /**
     * Default constructor.
     */
    public Appraisal() {}

    @Override
    public String toString() {
        return "Appraisal{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empReview=" + empReview +
                ", empBand='" + empBand + '\'' +
                ", currentSalary=" + currentSalary +
                ", appraisalPercentage=" + appraisalPercentage +
                ", appraisedSalary=" + appraisedSalary +
                '}';
    }
}
