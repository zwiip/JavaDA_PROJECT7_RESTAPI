package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class TradeServiceTest {

    private TradeService tradeService;
    private Trade trade;

    @Mock
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUp() {
        tradeRepository = mock(TradeRepository.class);
        tradeService = new TradeService(tradeRepository);

        trade = new Trade("Trade Account", "Type");
    }

    @Test
    public void givenTwoTrades_whenGetTrades_thenReturnTheListWithTwoTrades() {
        Trade secondTrade = new Trade("Second Trade Account", "Type 2");
        List<Trade> listOfTrades = new ArrayList<>();
        listOfTrades.add(trade);
        listOfTrades.add(secondTrade);
        when(tradeRepository.findAll()).thenReturn(listOfTrades);

        List<Trade> actualTrades = tradeService.getTrades();

        assertEquals(2, actualTrades.size());
        assertEquals("Trade Account", actualTrades.getFirst().getAccount());
        assertEquals("Type 2", actualTrades.getLast().getType());
    }

    @Test
    public void givenCorrectId_whenGetTrade_thenReturnMatchingTrade() {
        when(tradeRepository.findById(1)).thenReturn(Optional.of(trade));

        Optional<Trade> actualTrade = tradeService.getTrade(1);

        assertTrue(actualTrade.isPresent());
        assertEquals("Trade Account", actualTrade.get().getAccount());
    }

    @Test
    public void givenCorrectTrade_whenSaveTrade_thenSaveTrade() {
        tradeService.saveTrade(trade);

        verify(tradeRepository).save(trade);
    }

    @Test
    public void givenCorrectId_whenDeleteTrade_thenTradeIsDeleted() {
        tradeService.deleteTrade(1);

        verify(tradeRepository).deleteById(1);
    }
}
