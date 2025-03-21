package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TradeRepositoryTest {

	@Autowired
	private TradeRepository tradeRepository;

	private Trade trade;

	@BeforeEach
	public void setup() {
		trade = new Trade("Trade Account", "Type");
	}

	@Test
	public void saveTradeTest() {
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getId());
		assertEquals("Trade Account", trade.getAccount());
	}

	@Test
	public void updateTradeTest() {
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
		assertEquals("Trade Account Update", trade.getAccount());
	}

	@Test
	public void findAllTradeTest() {
		trade = tradeRepository.save(trade);
		List<Trade> listResult = tradeRepository.findAll();
		assertEquals(1, listResult.size());
	}

	@Test
	public void deleteTradeTest() {
		trade = tradeRepository.save(trade);
		Integer id = trade.getId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
