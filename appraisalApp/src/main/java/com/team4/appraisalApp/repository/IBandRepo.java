package com.team4.appraisalApp.repository;

import com.team4.appraisalApp.models.Band;

import java.util.List;
import java.util.Optional;

/**
 * The IBandRepo interface defines the methods for interacting with bands.
 */
public interface IBandRepo {

    /**
     * Retrieves a band by band ID.
     * @param bandId the band ID.
     * @return an Optional containing the band if found, otherwise an empty Optional.
     */
    public Optional<Band> getBand(String bandId);

    /**
     * Retrieves all bands.
     * @return a list of all bands.
     */
    public List<Band> getAllBands();
}
