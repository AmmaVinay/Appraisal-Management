package com.team4.appraisalApp.repository;

import com.team4.appraisalApp.models.Review;

import java.util.List;
import java.util.Optional;

/**
 * The IReviewRepo interface defines the methods for interacting with reviews.
 */
public interface IReviewRepo {

    /**
     * Retrieves a review by review ID.
     * @param revId the review ID.
     * @return an Optional containing the review if found, otherwise an empty Optional.
     */
    public Optional<Review> getReview(int revId);

    /**
     * Retrieves all reviews.
     * @return a list of all reviews.
     */
    public List<Review> getAllReviews();
}
