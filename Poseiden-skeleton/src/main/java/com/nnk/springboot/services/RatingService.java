package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RatingService {
    /* VARIABLES */
    private final RatingRepository ratingRepository;

    private final Logger logger = Logger.getLogger(RatingService.class.getName());

    /* CONSTRUCTOR */
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /* METHODS */
    public List<Rating> getRatings() { return ratingRepository.findAll(); }

    public Optional<Rating> getRating(Integer id) { return ratingRepository.findById(id); }

    public void saveRating(Rating rating) { ratingRepository.save(rating); }

    public void deleteRating(Integer id) { ratingRepository.deleteById(id); }
}
