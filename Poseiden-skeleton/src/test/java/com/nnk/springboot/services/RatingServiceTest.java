package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RatingServiceTest {

    private RatingService ratingService;
    private Rating rating;

    @Mock
    private RatingRepository ratingRepository;

    @Captor
    ArgumentCaptor<Rating> ratingCaptor;

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

        List<Rating> actualListOfRatings;
        actualListOfRatings = ratingService.getRatings();

        assertEquals(2, actualListOfRatings.size());
    }

}
