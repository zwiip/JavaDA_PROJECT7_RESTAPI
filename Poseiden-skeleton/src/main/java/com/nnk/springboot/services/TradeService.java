package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TradeService {
    /* VARIABLES */
    private final TradeRepository tradeRepository;

    private final Logger logger = Logger.getLogger(TradeService.class.getName());

    /* CONSTRUCTOR */
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    /* METHODS */
    public List<Trade> getTrades() { return tradeRepository.findAll(); }

    public Optional<Trade> getTrade(Integer id) {
        return tradeRepository.findById(id);
    }

    public void saveNewTrade(Trade trade) { tradeRepository.save(trade); }

    public void updateTrade(Trade trade, Integer id) {
        Optional<Trade> tradeToUpdate = getTrade(id);

        if (tradeToUpdate.isPresent()) {
            // TODO: faut-il faire redescendre seulement les champs utilisés dans les templates ou tous ?
        }
    }

    public void deleteTrade(Integer id) { tradeRepository.deleteById(id); }
}
