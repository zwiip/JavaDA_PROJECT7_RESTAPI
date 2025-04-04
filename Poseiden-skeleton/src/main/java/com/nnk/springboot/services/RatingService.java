package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the Rating Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the Rating repository.
 */
@Service
public class RatingService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(RatingService.class);
    /* VARIABLES */
    private final RatingRepository ratingRepository;
    private final Logger logger = Logger.getLogger(RatingService.class.getName());

    /* CONSTRUCTOR */
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /* METHODS */
    /**
     * Retrieves a list of all Rating entities.
     * @return a list of Rating objects
     */
    public List<Rating> getRatings() {
        logger.fine("Fetching all the Ratings.");
        List<Rating> ratings = ratingRepository.findAll();
        logger.info("Found " + ratings.size() + " ratings.");
        return ratings;
    }

    /**
     * Retrieve a Rating by its ID.
     * @param id the ID of the Rating to retrieve.
     * @return an Optional containing the matching Rating or an empty Optional object.
     */
    public Optional<Rating> getRating(Integer id) {
        logger.fine("Fetching a Rating for the id: " + id);
        Optional<Rating> rating = ratingRepository.findById(id);
        if (rating.isPresent()) {
            logger.info("Found a Rating for the id: " + id);
        } else {
            logger.warning("No Rating found for the id: " + id);
        }
        return rating;
    }

    /**
     * Saves a Rating entity to the repository.
     * @param rating the Rating to saves.
     */
    public void saveRating(Rating rating) {
        logger.fine("Saving the Rating");
        ratingRepository.save(rating);
        logger.info("Rating saved.");
    }

    /**
     * Deletes a Rating entity matching the given ID.
     * @param id the ID of the Rating to delete.
     */
    public void deleteRating(Integer id) {
        logger.fine("Deleting a Rating for the id: " + id);
        ratingRepository.deleteById(id);
        logger.info("Rating deleted.");
    }
}
