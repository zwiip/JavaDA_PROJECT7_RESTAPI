package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RatingRepositoryTest {

	@Autowired
	private RatingRepository ratingRepository;

	private Rating rating;

	@BeforeEach
	public void setup() {
		rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
	}

	@Test
	public void saveRatingTest() {
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
        assertEquals(10, rating.getOrderNumber());
	}

	@Test
	public void updateRatingTest() {
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
        assertEquals(20, rating.getOrderNumber());
	}

	@Test
	public void findAllRatingTest() {
		rating = ratingRepository.save(rating);
		List<Rating> listResult = ratingRepository.findAll();
		assertEquals(1, listResult.size());
	}

	@Test
	public void deleteRatingTest() {
		rating = ratingRepository.save(rating);
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}
}
