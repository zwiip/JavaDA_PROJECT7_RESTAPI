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
public class RatingServiceTests {

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
    }

    @Test
    public void givenTwoRatings_whenGetRatings_thenReturnTheListWithTwoRatings() {
        Rating firstRating = new Rating("mood", "sand", "fitch", 1);
        Rating secondRating = new Rating("mood2", "sand2", "fitch2", 2);
        List<Rating> listOfTwoRatings = new ArrayList<>();
        listOfTwoRatings.add(firstRating);
        listOfTwoRatings.add(secondRating);
        when(ratingRepository.findAll()).thenReturn(listOfTwoRatings);

        List<Rating> actualListOfRatings = new ArrayList<>();
        actualListOfRatings = ratingService.getRatings();

        assertEquals(2, actualListOfRatings.size());

    }

}
