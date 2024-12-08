package com.team4.appraisalApp.models;

/**
 * The Review class represents the review details for an employee's appraisal.
 * It includes the review ID and the multiplier associated with the review.
 */
public class Review {
    private int revId;
    private double revMul;

    // Getters and Setters

    /**
     * Gets the review ID.
     * @return the review ID.
     */
    public int getRevId() {
        return revId;
    }

    /**
     * Sets the review ID.
     * @param revId the review ID.
     */
    public void setRevId(int revId) {
        this.revId = revId;
    }

    /**
     * Gets the review multiplier.
     * @return the review multiplier.
     */
    public double getRevMul() {
        return revMul;
    }

    /**
     * Sets the review multiplier.
     * @param revMul the review multiplier.
     */
    public void setRevMul(double revMul) {
        this.revMul = revMul;
    }

    // Constructors

    /**
     * Constructs a Review object with the specified details.
     * @param revId the review ID.
     * @param revMul the review multiplier.
     */
    public Review(int revId, double revMul) {
        this.revId = revId;
        this.revMul = revMul;
    }

    /**
     * Default constructor.
     */
    public Review() {}

    // toString

    /**
     * Returns a string representation of the Review object.
     * @return a string representation of the Review object.
     */
    @Override
    public String toString() {
        return "Review{" +
                "revId=" + revId +
                ", revMul=" + revMul +
                '}';
    }
}
