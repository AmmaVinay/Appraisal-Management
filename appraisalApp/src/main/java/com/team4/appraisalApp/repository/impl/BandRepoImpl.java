package com.team4.appraisalApp.repository.impl;

import com.team4.appraisalApp.models.Band;
import com.team4.appraisalApp.repository.IBandRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The BandRepoImpl class implements the IBandRepo interface.
 * It provides the data access methods for bands, including retrieving and saving bands.
 */
@Repository
public class BandRepoImpl implements IBandRepo {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a BandRepoImpl with the specified JdbcTemplate.
     * @param jdbcTemplate the JdbcTemplate for database access.
     */
    public BandRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Creates a RowMapper for mapping database rows to Band objects.
     * @return a RowMapper for Band objects.
     */
    private RowMapper<Band> getBandRowMapper() {
        return (rs, row) -> {
            return new Band(
                    rs.getString("band_id"),
                    rs.getDouble("band_mul")
            );
        };
    }

    /**
     * Retrieves a band by band ID.
     * @param bandId the band ID.
     * @return an Optional containing the band if found, otherwise an empty Optional.
     */
    @Override
    public Optional<Band> getBand(String bandId) {
        String sql = "SELECT * FROM public.band WHERE band_id=?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getBandRowMapper(), bandId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all bands.
     * @return a list of all bands.
     */
    @Override
    public List<Band> getAllBands() {
        String sql = "SELECT * FROM public.band";
        try {
            return jdbcTemplate.query(sql, getBandRowMapper());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
