package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BidListRepositoryTest {

	@Autowired
	private BidListRepository bidListRepository;

	private BidList bid;

	@BeforeEach
	public void setup() {
		bid = new BidList("Account Test", "Type Test", 10d);
	}

	@Test
	public void saveBidListTest() {
		bid = bidListRepository.save(bid);
		assertNotNull(bid.getBidListId());
		assertEquals(bid.getBidQuantity(), 10d);
	}

	@Test
	public void updateBidListTest() {
		bid.setBidQuantity(20d);
		bid = bidListRepository.save(bid);
		assertEquals(bid.getBidQuantity(), 20d);
	}


	@Test
	public void findAllBidListTest() {
		bid = bidListRepository.save(bid);
		List<BidList> listResult = bidListRepository.findAll();
		assertEquals(1, listResult.size());
	}

	@Test
	public void deleteBidListTest() {
		bid = bidListRepository.save(bid);
		Integer id = bid.getBidListId();
		bidListRepository.delete(bid);
		Optional<BidList> bidList = bidListRepository.findById(id);
		assertFalse(bidList.isPresent());
	}
}
