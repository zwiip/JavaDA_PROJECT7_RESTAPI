package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class RatingServiceTest {

    private RatingService ratingService;
    private Rating rating;

    @Mock
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUp() {
        ratingRepository = mock(RatingRepository.class);
        ratingService = new RatingService(ratingRepository);

        rating = new Rating("mood", "sand", "fitch", 1);
    }

    @Test
    public void givenTwoRatings_whenGetRatings_thenReturnTheListWithTwoRatings() {
        Rating secondRating = new Rating("mood2", "sand2", "fitch2", 2);
        List<Rating> listOfTwoRatings = new ArrayList<>();
        listOfTwoRatings.add(rating);
        listOfTwoRatings.add(secondRating);
        when(ratingRepository.findAll()).thenReturn(listOfTwoRatings);

        List<Rating> actualListOfRatings = ratingService.getRatings();

        assertEquals(2, actualListOfRatings.size());
        assertEquals("mood", actualListOfRatings.getFirst().getMoodysRating());
        assertEquals("sand2", actualListOfRatings.getLast().getSandPRating());
    }

    @Test
    public void givenCorrectRatingId_whenGetRatingById_thenReturnMatchingRating() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(rating));

        Optional<Rating> actualRating = ratingService.getRating(1);

        assertTrue(actualRating.isPresent());
        assertEquals("mood", actualRating.get().getMoodysRating());
    }

    @Test
    public void givenCorrectRating_whenSaveRating_thenRatingIsSaved() {
        ratingService.saveRating(rating);

        verify(ratingRepository).save(rating);
    }

    @Test
    public void givenCorrectId_whenDeleteRating_thenRatingIsDeleted() {
        ratingService.deleteRating(1);

        verify(ratingRepository).deleteById(1);
    }
}
