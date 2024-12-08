package com.team4.appraisalApp.repository.impl;

import com.team4.appraisalApp.models.Review;
import com.team4.appraisalApp.repository.IReviewRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * The ReviewRepoImpl class implements the IReviewRepo interface.
 * It provides the data access methods for reviews, including retrieving and saving reviews.
 */
@Repository
public class ReviewRepoImpl implements IReviewRepo {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a ReviewRepoImpl with the specified JdbcTemplate.
     * @param jdbcTemplate the JdbcTemplate for database access.
     */
    public ReviewRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a RowMapper for mapping database rows to Review objects.
     * @return a RowMapper for Review objects.
     */
    private RowMapper<Review> getReviewRowMapper() {
        return (rs, rowNum) -> {
            Review review = new Review();
            review.setRevId(rs.getInt("rev_id"));
            review.setRevMul(rs.getDouble("rev_mul"));
            return review;
        };
    }

    /**
     * Retrieves a review by review ID.
     * @param revId the review ID.
     * @return an Optional containing the review if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Review> getReview(int revId) {
        String sql = "SELECT * FROM public.review WHERE rev_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getReviewRowMapper(), revId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all reviews.
     * @return a list of all reviews.
     */
    @Override
    public List<Review> getAllReviews() {
        String sql = "SELECT * FROM public.review";
        try {
            return jdbcTemplate.query(sql, getReviewRowMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
