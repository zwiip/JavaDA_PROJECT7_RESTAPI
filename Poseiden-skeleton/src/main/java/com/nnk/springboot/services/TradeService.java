package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Service layer for the Trade Entities.
 * Provides methods for the CRUD operations (create, read, update, delete) on the Trade repository.
 */
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

    /**
     * Retrieves a list of all Trade entities.
     * @return a list of Trade objects
     */
    public List<Trade> getTrades() {
        logger.fine("Fetching all trades.");
        List<Trade> trades = tradeRepository.findAll();
        logger.info("Found " + trades.size() + " trades.");
        return trades;
    }

    /**
     * Retrieve a Trade entity by its ID.
     * @param id the ID of the Trade to retrieve.
     * @return an Optional containing the matching Trade or an empty Optional object.
     */
    public Optional<Trade> getTrade(Integer id) {
        logger.fine("Fetching for the id: " + id);
        Optional<Trade> trade = tradeRepository.findById(id);
        if (trade.isPresent()) {
            logger.info("Found a trade for the given id: " + trade.get().getAccount());
        } else {
            logger.warning("No Trade found for the id: " + id);
        }
        return trade;
    }

    /**
     * Saves a Trade entity to the repository.
     * @param trade the Trade to save.
     */
    public void saveTrade(Trade trade) {
        logger.fine("Saving the Trade for the account: " + trade.getAccount());
        tradeRepository.save(trade);
        logger.info("Trade saved for the account: " + trade.getAccount());
    }

    /**
     * Deletes a Trade entity matching the given ID.
     * @param id the ID of the Trade to delete.
     */
    public void deleteTrade(Integer id) {
        logger.fine("Deleting Trade for the id: " + id);
        tradeRepository.deleteById(id);
        logger.info("Trade deleted.");
    }
}
