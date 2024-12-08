package com.team4.appraisalApp.models;

import java.util.List;

/**
 * The Band class represents the band details for an employee's appraisal.
 * It includes the band ID and the multiplier associated with the band.
 */
public class Band {
    // POJO data members
    private String bandId;
    private double bandMul;

    // Getters and Setters

    /**
     * Gets the band ID.
     * @return the band ID.
     */
    public String getBandId() {
        return bandId;
    }

    /**
     * Sets the band ID.
     * @param bandId the band ID.
     */
    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    /**
     * Gets the band multiplier.
     * @return the band multiplier.
     */
    public double getBandMul() {
        return bandMul;
    }

    /**
     * Sets the band multiplier.
     * @param bandMul the band multiplier.
     */
    public void setBandMul(double bandMul) {
        this.bandMul = bandMul;
    }

    // Constructors

    /**
     * Default constructor.
     */
    public Band() {
    }

    /**
     * Constructs a Band object with the specified details.
     * @param bandId the band ID.
     * @param bandMul the band multiplier.
     */
    public Band(String bandId, double bandMul) {
        this.bandId = bandId;
        this.bandMul = bandMul;
    }

    // toString

    /**
     * Returns a string representation of the Band object.
     * @return a string representation of the Band object.
     */
    @Override
    public String toString() {
        return "Band{" +
                "bandId='" + bandId + '\'' +
                ", bandMul=" + bandMul +
                '}';
    }
}
